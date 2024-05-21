package com.example.fanf1

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import controller.TeamController
import model.DatabaseHelper
import com.squareup.picasso.Picasso
import controller.UserController

class TeamActivity : ComponentActivity() {
    private var teamN = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get team name from intent
        val teamName = intent.getStringExtra("teamName")
        val username = intent.getStringExtra("Username")
        if (teamName != null) {
            teamN = teamName
        }
        // Fetch team details from database
        val dbHelper = DatabaseHelper(this)
        val teamController = TeamController(this)
        val teamCursor = teamController.getTeamByName(teamName.toString())
        val userController = UserController(this)
        // Check if cursor is not null and move to the first row
        teamCursor?.apply {
            if (moveToFirst()) {
                // Extract team details from cursor
                val teamPoints = getInt(getColumnIndexOrThrow(DatabaseHelper.TEAM_POINTS))
                val teamDriver1 = getString(getColumnIndexOrThrow(DatabaseHelper.TEAM_DRIVER1))
                val teamDriver2 = getString(getColumnIndexOrThrow(DatabaseHelper.TEAM_DRIVER2))
                val highestFinishAll =
                    getInt(getColumnIndexOrThrow(DatabaseHelper.HIGHEST_FINISH_ALL))
                val highestFinishNow =
                    getInt(getColumnIndexOrThrow(DatabaseHelper.HIGHEST_FINISH_NOW))
                val polesNow = getInt(getColumnIndexOrThrow(DatabaseHelper.POLES_NOW))
                val polesAll = getInt(getColumnIndexOrThrow(DatabaseHelper.POLES_ALL))
                val fastLapNow = getInt(getColumnIndexOrThrow(DatabaseHelper.FAST_LAP_NOW))
                val fastLapAll = getInt(getColumnIndexOrThrow(DatabaseHelper.FAST_LAP_ALL))
                val championships = getInt(getColumnIndexOrThrow(DatabaseHelper.CHAMPIONSHIP))
                val base = getString(getColumnIndexOrThrow(DatabaseHelper.BASE))
                val chief = getString(getColumnIndexOrThrow(DatabaseHelper.CHIEF))
                val chassis = getString(getColumnIndexOrThrow(DatabaseHelper.CHASSIS))
                val powerUnit = getString(getColumnIndexOrThrow(DatabaseHelper.POWER_UNIT))
                val isAdmin = userController.isAdmin(username.toString())

                if (isAdmin) {
                    setContentView(R.layout.admin_layout_team)
                    // Load data for admin layout
                    loadAdminData(
                        teamName.toString(),
                        teamPoints,
                        teamDriver1,
                        teamDriver2,
                        highestFinishAll,
                        highestFinishNow,
                        polesNow,
                        polesAll,
                        fastLapNow,
                        fastLapAll,
                        championships,
                        base,
                        chief,
                        chassis,
                        powerUnit
                    )
                } else {
                    setContentView(R.layout.activity_team)
                    // Load data for non-admin layout
                    loadNonAdminData(
                        teamName.toString(),
                        teamPoints,
                        teamDriver1,
                        teamDriver2,
                        highestFinishAll,
                        highestFinishNow,
                        polesNow,
                        polesAll,
                        fastLapNow,
                        fastLapAll,
                        championships,
                        base,
                        chief,
                        chassis,
                        powerUnit
                    )
                }
            }
            // Close the cursor after use
            close()
        }
    }

    private fun loadAdminData(
        teamName: String,
        teamPoints: Int,
        teamDriver1: String,
        teamDriver2: String,
        highestFinishAll: Int,
        highestFinishNow: Int,
        polesNow: Int,
        polesAll: Int,
        fastLapNow: Int,
        fastLapAll: Int,
        championships: Int,
        base: String,
        chief: String,
        chassis: String,
        powerUnit: String
    ) {
        // Update UI with fetched team details for admin layout
        findViewById<EditText>(R.id.teamName).apply {
            setText(teamName)
            textSize = 30f // Set text size to 30sp
        }

        findViewById<EditText>(R.id.teamPoints).apply {
            setText("$teamPoints")
        }

        findViewById<EditText>(R.id.teamDriver1).apply {
            setText("$teamDriver1")
        }

        findViewById<EditText>(R.id.teamDriver2).apply {
            setText("$teamDriver2")
        }

        findViewById<EditText>(R.id.highestFinishAll).apply {
            setText("$highestFinishAll")
        }

        findViewById<EditText>(R.id.highestFinishNow).apply {
            setText("$highestFinishNow")
        }

        findViewById<EditText>(R.id.polesNow).apply {
            setText("$polesNow")
        }

        findViewById<EditText>(R.id.polesAll).apply {
            setText("$polesAll")
        }

        findViewById<EditText>(R.id.fastLapNow).apply {
            setText("$fastLapNow")
        }

        findViewById<EditText>(R.id.fastLapAll).apply {
            setText("$fastLapAll")
        }

        findViewById<EditText>(R.id.championships).apply {
            setText("$championships")
        }

        findViewById<EditText>(R.id.base).apply {
            setText("$base")
        }

        findViewById<EditText>(R.id.chief).apply {
            setText("$chief")
        }

        findViewById<EditText>(R.id.chassis).apply {
            setText("$chassis")
        }

        findViewById<EditText>(R.id.powerUnit).apply {
            setText("$powerUnit")
        }


        val teamURLS = mapOf(
            "Red Bull Racing" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/red-bull-racing.png",
            "Ferrari" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/ferrari.png",
            "McLaren" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/mclaren.png",
            "Mercedes" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/mercedes.png",
            "Aston Martin" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/aston-martin.png",
            "RB" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/rb.png",
            "Haas F1 Team" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/haas.png",
            "Alpine" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/alpine.png",
            "Williams" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/williams.png",
            "Kick Sauber" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/kick-sauber.png"
        )

        val teamURL = teamURLS[teamName]

        if (teamURL != null) {
            val teamIMG = findViewById<ImageView>(R.id.teamLogo)
            Picasso.get().load(teamURL).into(teamIMG)
        } else {
            // Handle case where track name is not found
        }
    }
    fun saveChanges2(view: View) {
        // Get the updated values from EditText fields
        val updatedTeamPoints = findViewById<EditText>(R.id.teamPoints).text.toString().replace("[^0-9]".toRegex(), "").toInt()
        val updatedTeamDriver1 = findViewById<EditText>(R.id.teamDriver1).text.toString()
        val updatedTeamDriver2 = findViewById<EditText>(R.id.teamDriver2).text.toString()
        val updatedHighestFinishAll = findViewById<EditText>(R.id.highestFinishAll).text.toString().replace("[^0-9]".toRegex(), "").toInt()
        val updatedHighestFinishNow = findViewById<EditText>(R.id.highestFinishNow).text.toString().replace("[^0-9]".toRegex(), "").toInt()
        val updatedPolesNow = findViewById<EditText>(R.id.polesNow).text.toString().replace("[^0-9]".toRegex(), "").toInt()
        val updatedPolesAll = findViewById<EditText>(R.id.polesAll).text.toString().replace("[^0-9]".toRegex(), "").toInt()
        val updatedFastLapNow = findViewById<EditText>(R.id.fastLapNow).text.toString().replace("[^0-9]".toRegex(), "").toInt()
        val updatedFastLapAll = findViewById<EditText>(R.id.fastLapAll).text.toString().replace("[^0-9]".toRegex(), "").toInt()
        val updatedChampionships = findViewById<EditText>(R.id.championships).text.toString().replace("[^0-9]".toRegex(), "").toInt()
        val updatedBase = findViewById<EditText>(R.id.base).text.toString()
        val updatedChief = findViewById<EditText>(R.id.chief).text.toString()
        val updatedChassis = findViewById<EditText>(R.id.chassis).text.toString()
        val updatedPowerUnit = findViewById<EditText>(R.id.powerUnit).text.toString()

        val teamController = TeamController(this)

        // Update the team details in the database
        teamController.updateTeam(
            teamN,
            updatedTeamPoints,
            updatedTeamDriver1,
            updatedTeamDriver2,
            updatedHighestFinishAll,
            updatedHighestFinishNow,
            updatedPolesNow,
            updatedPolesAll,
            updatedFastLapNow,
            updatedFastLapAll,
            updatedChampionships,
            updatedBase,
            updatedChief,
            updatedChassis,
            updatedPowerUnit
        )
    }



    private fun loadNonAdminData(
        teamName: String,
        teamPoints: Int,
        teamDriver1: String,
        teamDriver2: String,
        highestFinishAll: Int,
        highestFinishNow: Int,
        polesNow: Int,
        polesAll: Int,
        fastLapNow: Int,
        fastLapAll: Int,
        championships: Int,
        base: String,
        chief: String,
        chassis: String,
        powerUnit: String
    ) {
        // Update UI with fetched team details for non-admin layout
        findViewById<TextView>(R.id.teamName).apply {
            text = "$teamName\n"
            textSize = 30f // Set text size to 30sp
        }
        findViewById<TextView>(R.id.teamPoints).apply {
            text = "Team Points: $teamPoints\n pts"
        }
        findViewById<TextView>(R.id.teamDriver1).apply {
            text = "Driver Pairing\n\n$teamDriver1"
        }
        findViewById<TextView>(R.id.teamDriver2).apply {
            text = "$teamDriver2\n"
        }
        findViewById<TextView>(R.id.highestFinishAll).apply {
            text = "Highest Finish (All Time): $highestFinishAll\n"
        }
        findViewById<TextView>(R.id.highestFinishNow).apply {
            text = "Highest Finish (Current season): $highestFinishNow\n"
        }
        findViewById<TextView>(R.id.polesNow).apply {
            text = "Poles (Current season): $polesNow\n"
        }
        findViewById<TextView>(R.id.polesAll).apply {
            text = "Poles (All Time): $polesAll\n"
        }
        findViewById<TextView>(R.id.fastLapNow).apply {
            text = "Fastest Laps (Current season): $fastLapNow\n"
        }
        findViewById<TextView>(R.id.fastLapAll).apply {
            text = "Fastest Laps (All Time): $fastLapAll\n"
        }
        findViewById<TextView>(R.id.championships).apply {
            text = "Championships: $championships\n"
        }
        findViewById<TextView>(R.id.base).apply {
            text = "Base: $base\n"
        }
        findViewById<TextView>(R.id.chief).apply {
            text = "Chief: $chief\n"
        }
        findViewById<TextView>(R.id.chassis).apply {
            text = "Chassis: $chassis\n"
        }
        findViewById<TextView>(R.id.powerUnit).apply {
            text = "Power Unit: $powerUnit\n"
        }
        val teamURLS = mapOf(
            "Red Bull Racing" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/red-bull-racing.png",
            "Ferrari" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/ferrari.png",
            "McLaren" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/mclaren.png",
            "Mercedes" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/mercedes.png",
            "Aston Martin" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/aston-martin.png",
            "RB" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/rb.png",
            "Haas F1 Team" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/haas.png",
            "Alpine" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/alpine.png",
            "Williams" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/williams.png",
            "Kick Sauber" to "https://media.formula1.com/d_team_car_fallback_image.png/content/dam/fom-website/teams/2024/kick-sauber.png"
        )

        val teamURL = teamURLS[teamName]

        if (teamURL != null) {
            val teamIMG = findViewById<ImageView>(R.id.teamLogo)
            Picasso.get().load(teamURL).into(teamIMG)
        } else {
            // Handle case where track name is not found
        }
    }

}

