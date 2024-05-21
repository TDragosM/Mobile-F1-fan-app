package com.example.fanf1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import controller.RaceController
import model.DatabaseHelper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RacingActivity : ComponentActivity() {
    val raceController = RaceController(this)
    val linearLayout by lazy { findViewById<LinearLayout>(R.id.linearLayout) }
    var originalRaceList: MutableList<race> = mutableListOf() // Store the original race list
    var raceList: MutableList<race> = mutableListOf() // Declare as a mutable list
    var isShowingPastRaces = false // Flag to track if past races are being shown
    var isShowingUpcomingRaces = false // Flag to track if upcoming races are being shown
    var uname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_racing)
        val username = intent?.getStringExtra("Username")
        if (username != null) {
            // Use the username as needed
            println("Username received: $username")
            uname = username
            showToast(username)
        } else {
            // Handle the case where the username is null
            println("No username received in Intent")
            Toast.makeText(this, "Error: No username provided", Toast.LENGTH_LONG).show()
            // Optionally, you can finish the activity if the username is required
            finish()
        }
        val currentDate = getCurrentDate()

        val cursor2 = raceController.getAllRaces()
        val buttonDrivers = findViewById<Button>(R.id.buttonDrivers)
        val buttonConstructor = findViewById<Button>(R.id.buttonConstructors)
        // Set OnClickListener to handle button clicks
        buttonDrivers.setOnClickListener {
            // Create an Intent to navigate to the DriversActivity
            val intent = Intent(this, DriversActivity::class.java)
            intent.putExtra("Username", uname) // Add username to intent
            startActivity(intent)
        }

        buttonConstructor.setOnClickListener {
            // Create an Intent to navigate to the ConstructorsActivity
            val intent = Intent(this, ConstructorsActivity::class.java)
            intent.putExtra("Username", uname) // Add username to intent
            startActivity(intent)
        }

        cursor2?.use { cursor2 ->
            if (cursor2.moveToFirst()) {
                do {
                    val round = cursor2.getInt(cursor2.getColumnIndexOrThrow(DatabaseHelper.RACE_ROUND))
                    val dateStr = cursor2.getString(cursor2.getColumnIndexOrThrow(DatabaseHelper.RACE_DATE))
                    val name = cursor2.getString(cursor2.getColumnIndexOrThrow(DatabaseHelper.RACE_NAME))

                    // Parse the race date
                    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    val raceDate = dateFormat.parse(dateStr)

                    val raceInstance = race(round, dateStr, name)
                    originalRaceList.add(raceInstance) // Add to the original list
                    raceList.add(raceInstance) // Add to the current list

                } while (cursor2.moveToNext())
            }
        }

        displayRaces(raceList) // Display the initial list of races

        val buttonPast = findViewById<Button>(R.id.buttonPast)
        val buttonUpcoming = findViewById<Button>(R.id.buttonUpcoming)
        val buttonRacing = findViewById<Button>(R.id.buttonRacing)
        buttonRacing.setBackgroundColor(Color.RED)
        buttonPast.setOnClickListener {
            // Toggle between showing past and upcoming races
            isShowingPastRaces = !isShowingPastRaces
            isShowingUpcomingRaces = false

            if (isShowingPastRaces) {
                filterRaces(currentDate, false)
                // Highlight the button
                buttonPast.setBackgroundColor(Color.RED)
                buttonUpcoming.setBackgroundColor(Color.TRANSPARENT)
                animateTransition(buttonPast, buttonUpcoming, buttonRacing)
            } else {
                displayRaces(originalRaceList) // Show the original list
                // Unhighlight the button
                buttonPast.setBackgroundColor(Color.TRANSPARENT)
                // Automatically select the "Racing" button
                buttonRacing.performClick()
            }
        }

        buttonUpcoming.setOnClickListener {
            // Toggle between showing past and upcoming races
            isShowingUpcomingRaces = !isShowingUpcomingRaces
            isShowingPastRaces = false

            if (isShowingUpcomingRaces) {
                filterRaces(currentDate, true)
                // Highlight the button
                buttonUpcoming.setBackgroundColor(Color.RED)
                buttonPast.setBackgroundColor(Color.TRANSPARENT)
                animateTransition(buttonUpcoming, buttonPast, buttonRacing)
            } else {
                displayRaces(originalRaceList) // Show the original list
                // Unhighlight the button
                buttonUpcoming.setBackgroundColor(Color.TRANSPARENT)
                // Automatically select the "Racing" button
                buttonRacing.performClick()
            }
        }
        buttonRacing.setOnClickListener {
            // Show all races
            raceList = originalRaceList.toMutableList() // Reset raceList to original list
            displayRaces(raceList)
            // Highlight the button

            buttonPast.setBackgroundColor(Color.TRANSPARENT)
            buttonUpcoming.setBackgroundColor(Color.TRANSPARENT)
            animateTransition(buttonRacing, buttonPast, buttonUpcoming)
        }
    }

    private fun animateTransition(selectedButton: Button, unselectedButton1: Button, unselectedButton2: Button) {
        // Highlight the selected button
        selectedButton.setBackgroundColor(Color.RED)
        unselectedButton1.setBackgroundColor(Color.TRANSPARENT)
        unselectedButton2.setBackgroundColor(Color.TRANSPARENT)
        // Add animation here (e.g., slide animation)
        val slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left)
        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
        findViewById<LinearLayout>(R.id.linearLayout).startAnimation(slideOut)
        findViewById<LinearLayout>(R.id.linearLayout).startAnimation(slideIn)
    }

    private fun getCurrentDate(): Date {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val currentDateStr = dateFormat.format(currentDate)
        return dateFormat.parse(currentDateStr)!!
    }

    private fun filterRaces(currentDate: Date, upcoming: Boolean) {
        raceList = if (upcoming) {
            originalRaceList.filter { raceInstance ->
                val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val raceDate = dateFormat.parse(raceInstance.date)
                raceDate.after(currentDate)
            }.toMutableList() // Convert back to a mutable list
        } else {
            originalRaceList.filter { raceInstance ->
                val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val raceDate = dateFormat.parse(raceInstance.date)
                raceDate.before(currentDate)
            }.toMutableList() // Convert back to a mutable list
        }
        displayRaces(raceList)
    }

    private fun displayRaces(races: List<race>) {
        linearLayout.removeAllViews()
        for (raceInstance in races) {
            val dateTextView = TextView(this)
            dateTextView.text = raceInstance.date
            dateTextView.setTextColor(Color.WHITE)
            dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat()) // Set text size here

            val roundTextView = TextView(this)
            roundTextView.text = "   Round: ${raceInstance.round}   "
            roundTextView.setTextColor(Color.WHITE)
            roundTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat()) // Set text size here

            val nameTextView = TextView(this)
            nameTextView.text = "${raceInstance.name}\n\n"
            nameTextView.setTextColor(Color.WHITE)
            nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat()) // Set text size here

            // Create a horizontal LinearLayout to contain the elements
            val containerLayout = LinearLayout(this)
            containerLayout.orientation = LinearLayout.HORIZONTAL
            containerLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // Add TextViews to the container layout
            containerLayout.addView(dateTextView)
            containerLayout.addView(roundTextView)
            containerLayout.addView(nameTextView)

            // Add click listener to the container layout
            containerLayout.setOnClickListener {
                // Start CircuitActivity and pass the selected race's data
                val username = intent?.getStringExtra("Username")
                val intent = Intent(this, CircuitActivity::class.java)
                intent.putExtra("round", raceInstance.round)
                intent.putExtra("date", raceInstance.date)
                intent.putExtra("name", raceInstance.name)
                intent.putExtra("Username", uname)
                startActivity(intent)
            }

            // Add container layout to the main linear layout
            linearLayout.addView(containerLayout)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}



class race(round: Int, date: String, name: String){
    var round: Int = round
    var date: String = date
    var name: String = name
}

