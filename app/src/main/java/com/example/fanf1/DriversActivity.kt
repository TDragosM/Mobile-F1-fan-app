package com.example.fanf1


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import controller.DriverController
import model.DatabaseHelper

class DriversActivity : ComponentActivity() {
    private val driverController = DriverController(this)
    private val linearLayout by lazy { findViewById<LinearLayout>(R.id.linearLayout) }
    private var driverList: MutableList<Driver> = mutableListOf()
    private var uname: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drivers)
        val username = intent?.getStringExtra("Username")
        if (username != null) {
            // Use the username as needed
            println("Username received: $username")
            showToast(username.toString())
            uname= username
        } else {
            // Handle the case where the username is null
            println("No username received in Intent")
            Toast.makeText(this, "Error: No username provided", Toast.LENGTH_LONG).show()
            // Optionally, you can finish the activity if the username is required
            finish()
        }
        val cursor3 = driverController.getAllDrivers()

        cursor3?.use { cursor3 ->
            if (cursor3.moveToFirst()) {
                do {
                    // Retrieve driver details from the database
                    val name = cursor3.getString(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_NAME))
                    val points = cursor3.getInt(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_POINTS))
                    val teamName = cursor3.getString(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_TEAM))

                    // Create Driver object and add it to the list
                    val driver = Driver(name, points, teamName)
                    driverList.add(driver)
                } while (cursor3.moveToNext())
            }
        }
        val buttonDrivers = findViewById<Button>(R.id.buttonDrivers)
        val buttonRacingActivity = findViewById<Button>(R.id.buttonRacing)
        buttonDrivers.setBackgroundColor(Color.RED)
        // Sort the driverList based on points in descending order
        driverList.sortByDescending { it.points }

        // Display the sorted list of drivers
        displayDrivers(driverList)
        val buttonConstructors = findViewById<Button>(R.id.buttonConstructors)

        // Set OnClickListener to handle button clicks
        buttonConstructors.setOnClickListener {
            // Create an Intent to navigate to the ConstructorsActivity
            val intent = Intent(this, ConstructorsActivity::class.java)
            intent.putExtra("Username", uname)
            buttonConstructors.setBackgroundColor(Color.RED)
            // Start the ConstructorsActivity
            startActivity(intent)
        }
        buttonRacingActivity.setOnClickListener {
            // Create an Intent to navigate to the ConstructorsActivity
            val intent = Intent(this, RacingActivity::class.java)
            intent.putExtra("Username", uname)
            buttonRacingActivity.setBackgroundColor(Color.RED)
            // Start the ConstructorsActivity
            startActivity(intent)
        }
    }
    private fun displayDrivers(drivers: List<Driver>) {
        for ((index, driver) in drivers.withIndex()) {
            val position = index + 1 // Adjust the position to start from 1
            // Create a TextView to display driver information
            val textView = TextView(this)
            textView.text = "$position. ${driver.name} (${driver.teamName}) - ${driver.points} points\n\n"
            textView.setTextColor(Color.WHITE)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.toFloat()) // Set text size here


            // Set OnClickListener for the TextView
            textView.setOnClickListener {
                // Handle click event here, e.g., navigate to another activity or perform an action

                val intent2 = Intent(this, DriverActivity::class.java)
                intent2.putExtra("driverName", driver.name)
                intent2.putExtra("driverPoints", driver.points)
                intent2.putExtra("driverPosition", position)
                intent2.putExtra("Username", uname)
                startActivity(intent2)
                showToast("Clicked on: ${driver.name}")
            }

            // Add TextView to the LinearLayout
            linearLayout.addView(textView)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun saveChanges(view: View) {}
}

class Driver(val name: String, val points: Int, val teamName: String)
