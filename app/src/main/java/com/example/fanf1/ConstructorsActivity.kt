package com.example.fanf1

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import controller.TeamController
import model.DatabaseHelper

class ConstructorsActivity : ComponentActivity() {
    private val constructorController = TeamController(this)
    private val linearLayout by lazy { findViewById<LinearLayout>(R.id.linearLayout) }
    private var constructorList: MutableList<Constructor> = mutableListOf()
    private var uname: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constructors)
        val username = intent?.getStringExtra("Username")
        if (username != null) {
            // Use the username as needed
            uname =  username
            println("Username received: $username")
        } else {
            // Handle the case where the username is null
            println("No username received in Intent")
            Toast.makeText(this, "Error: No username provided", Toast.LENGTH_LONG).show()
            // Optionally, you can finish the activity if the username is required
            finish()
        }
        val cursor = constructorController.getAllTeams()

        cursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    // Retrieve constructor details from the database
                    val teamName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TEAM_NAME))
                    val points = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.TEAM_POINTS))
                    val driver1 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TEAM_DRIVER1))
                    val driver2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TEAM_DRIVER2))
                    // Create Constructor object and add it to the list
                    val constructor = Constructor(teamName, points, driver1.toString(),
                        driver2.toString()
                    )
                    constructorList.add(constructor)
                } while (cursor.moveToNext())
            }
        }

        val buttonConstructors = findViewById<Button>(R.id.buttonConstructors)
        buttonConstructors.setBackgroundColor(Color.RED)

        // Sort the constructorList based on points in descending order
        constructorList.sortByDescending { it.points }

        // Display the sorted list of constructors
        displayConstructors(constructorList)

        val buttonRacing = findViewById<Button>(R.id.buttonRacing)
        val buttonDrivers = findViewById<Button>(R.id.buttonDrivers)

        // Set OnClickListener to handle button clicks
        buttonRacing.setOnClickListener {
            // Create an Intent to navigate to the RacingActivity
            val intent = Intent(this, RacingActivity::class.java)
            buttonRacing.setBackgroundColor(Color.RED)
            intent.putExtra("Username", uname)
            // Start the RacingActivity
            startActivity(intent)
        }

        // Set OnClickListener for the "Drivers" button
        buttonDrivers.setOnClickListener {
            // Create an Intent to navigate to the DriversActivity
            val intent = Intent(this, DriversActivity::class.java)
            intent.putExtra("Username", uname)
            // Start the DriversActivity
            startActivity(intent)
        }
    }

    private fun displayConstructors(constructors: List<Constructor>) {
        var position = 1 // Variable to track the position of each constructor

        for (constructor in constructors) {
            // Create a TextView to display constructor information
            val textView = TextView(this)
            textView.text = "$position. ${constructor.teamName} \n${constructor.driver1} & ${constructor.driver2} - ${constructor.points} points\n\n"
            textView.setTextColor(Color.WHITE)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat()) // Set text size here

            // Set OnClickListener for the TextView to open TeamActivity with the corresponding team name
            textView.setOnClickListener {
                val username = intent.getStringExtra("username")
                val intent = Intent(this, TeamActivity::class.java)
                intent.putExtra("teamName", constructor.teamName)
                intent.putExtra("Username", uname)
                startActivity(intent)
            }

            // Add TextView to the LinearLayout
            linearLayout.addView(textView)

            // Increment position for the next constructor
            position++
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

class Constructor(val teamName: String, val points: Int, val driver1: String, val driver2: String)
