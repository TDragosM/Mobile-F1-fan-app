package com.example.fanf1

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.squareup.picasso.Picasso
import controller.DriverController
import controller.UserController

import model.DatabaseHelper
class DriverActivity : ComponentActivity() {
    private var originalDriverName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val driverController = DriverController(this)

        val driverName = intent.getStringExtra("driverName")
        val team = driverController.getTeamByDriverName(driverName.toString())
        showToast(team.toString())
        //driverController.updateDriverTeam("Max Verstappen", "Red Bull Racing")
        originalDriverName= driverName
        val driverPosition = intent.getIntExtra("driverPosition", -1)
        val driverPoints = intent.getIntExtra("driverPoints", -1)
        val username = intent.getStringExtra("Username")
        showToast(username.toString())
        val userController = UserController(this)
        val isAdmin = userController.isAdmin(username.toString())
        if (isAdmin) {
            setContentView(R.layout.admin_layout)
            // Load data for admin layout
            loadAdminData()
        } else {
            setContentView(R.layout.activity_driver)
            // Load data for non-admin layout
            loadUserData()
        }

    }

    private fun loadAdminData() {
        val driverController = DriverController(this)
        val driverName = intent.getStringExtra("driverName")
        val driverPosition = intent.getIntExtra("driverPosition", -1)
        val driverPoints = intent.getIntExtra("driverPoints", -1)
        val cursor = driverController.getDriverByName(driverName.toString())
        cursor?.apply {
            if (moveToFirst()) {
                var adminTeam = getString(getColumnIndexOrThrow(DatabaseHelper.DRIVER_TEAM))
                val adminNationality =
                    getString(getColumnIndexOrThrow(DatabaseHelper.DRIVER_COUNTRY))
                val adminFast =
                    getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_FASTEST_LAP_SEASON))
                val adminFastAll =
                    getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_FASTEST_LAP_START))
                val adminPodiums =
                    getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_PODIUMS_SEASON))
                val adminPodiumsAll =
                    getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_PODIUMS_START))
                val adminStarts = getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_GPS_SEASON))
                val adminStartsAll = getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_GPS_START))
                val adminChamp = getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_CHAMPIONSHIPS))
                val adminBday = getString(getColumnIndexOrThrow(DatabaseHelper.DRIVER_BIRTH))
                val adminPlace = getString(getColumnIndexOrThrow(DatabaseHelper.DRIVER_FROM))

                findViewById<EditText>(R.id.editTeam).apply {
                    setText(adminTeam)
                    addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                            // No need to implement anything here
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            // Update adminTeam variable when text changes
                            adminTeam = s.toString()
                        }

                        override fun afterTextChanged(s: Editable?) {
                            // No need to implement anything here
                        }
                    })
                }
                findViewById<EditText>(R.id.worldChampionships).apply {
                    setText("$adminChamp")
                    textSize = 40f // Set text size to 20sp
                }

// Update UI with fetched admin details
                findViewById<EditText>(R.id.driverName).apply {
                    setText(driverName)
                }

                findViewById<EditText>(R.id.fastestLaps).apply {
                    setText("$adminFast")
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<EditText>(R.id.allTimeFastestLaps).apply {
                    setText("$adminFastAll")
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<EditText>(R.id.podiums).apply {
                    setText("$adminPodiums")
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<EditText>(R.id.allTimePodiums).apply {
                    setText("$adminPodiumsAll")
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<EditText>(R.id.gpsEntered).apply {
                    setText("$adminStarts")
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<EditText>(R.id.allTimeGpsEntered).apply {
                    setText("$adminStartsAll")
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<EditText>(R.id.championshipStanding).apply {
                    setText("$driverPosition")
                    textSize = 40f // Set text size to 20sp
                }
                findViewById<EditText>(R.id.championshipPoints).apply {
                    setText("$driverPoints")
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<EditText>(R.id.dateOfBirth).apply {
                    setText("$adminBday")
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<EditText>(R.id.country).apply {
                    setText("$adminNationality")
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<EditText>(R.id.placeOfBirth).apply {
                    setText("$adminPlace")
                    textSize = 20f // Set text size to 20sp
                }

                // Load admin image if available (similar to loading driver image)
                val adminURLS = mapOf(
                    "Max Verstappen" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/M/MAXVER01_Max_Verstappen/maxver01.png",
                    "Sergio Perez" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/S/SERPER01_Sergio_Perez/serper01.png",
                    "Charles Leclerc" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/C/CHALEC01_Charles_Leclerc/chalec01.png",
                    "Lando Norris" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LANNOR01_Lando_Norris/lannor01.png",
                    "Carlos Sainz" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/C/CARSAI01_Carlos_Sainz/carsai01.png",
                    "Oscar Piastri" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/O/OSCPIA01_Oscar_Piastri/oscpia01.png",
                    "George Russell" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/G/GEORUS01_George_Russell/georus01.png",
                    "Fernando Alonso" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/F/FERALO01_Fernando_Alonso/feralo01.png",
                    "Lewis Hamilton" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LEWHAM01_Lewis_Hamilton/lewham01.png",
                    "Yuki Tsunoda" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/Y/YUKTSU01_Yuki_Tsunoda/yuktsu01.png",
                    "Lance Stroll" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LANSTR01_Lance_Stroll/lanstr01.png",
                    "Oliver Bearman" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/O/OLIBEA01_Oliver_Bearman/olibea01.png",
                    "Nico Hulkenberg" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/N/NICHUL01_Nico_Hulkenberg/nichul01.png",
                    "Daniel Ricciardo" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/D/DANRIC01_Daniel_Ricciardo/danric01.png",
                    "Esteban Ocon" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/E/ESTOCO01_Esteban_Ocon/estoco01.png",
                    "Kevin Magnussen" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/K/KEVMAG01_Kevin_Magnussen/kevmag01.png",
                    "Alexander Albon" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/A/ALEALB01_Alexander_Albon/alealb01.png",
                    "Guanyu Zhou" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/G/GUAZHO01_Guanyu_Zhou/guazho01.png",
                    "Pierre Gasly" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/P/PIEGAS01_Pierre_Gasly/piegas01.png",
                    "Valtteri Bottas" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/V/VALBOT01_Valtteri_Bottas/valbot01.png",
                    "Logan Sargeant" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LOGSAR01_Logan_Sargeant/logsar01.png"
                )

                val adminURL = adminURLS[driverName]

                if (adminURL != null) {
                    val adminIMG = findViewById<ImageView>(R.id.driverPicture)
                    Picasso.get().load(adminURL).into(adminIMG)
                } else {
                        // Handle case where admin image URL is not found
                }
            }
                // Close the cursor after use
            close()
        }
    }



    fun saveChanges(view: View) {
        // Get the updated values from EditText fields
        val updatedDriverName = findViewById<EditText>(R.id.driverName).text.toString()
        val updatedTeam = findViewById<EditText>(R.id.editTeam).text.toString()

        val updatedFast = findViewById<EditText>(R.id.fastestLaps).text.toString().toInt()
        val updatedFastAll = findViewById<EditText>(R.id.allTimeFastestLaps).text.toString().toInt()
        val updatedPodiums = findViewById<EditText>(R.id.podiums).text.toString().toInt()
        val updatedPodiumsAll = findViewById<EditText>(R.id.allTimePodiums).text.toString().toInt()
        val updatedStarts = findViewById<EditText>(R.id.gpsEntered).text.toString().toInt()
        val updatedStartsAll = findViewById<EditText>(R.id.allTimeGpsEntered).text.toString().toInt()
        val updatedChamp = findViewById<EditText>(R.id.worldChampionships).text.toString().toInt()
        val updatedBday = findViewById<EditText>(R.id.dateOfBirth).text.toString()
        val updatedNationality = findViewById<EditText>(R.id.country).text.toString()
        val updatedPlace = findViewById<EditText>(R.id.placeOfBirth).text.toString()
        val updatedPoints = findViewById<EditText>(R.id.championshipPoints).text.toString().toInt()
        val updatedPosition = findViewById<EditText>(R.id.championshipStanding).text.toString().toInt()

        // Update the database with the changes
        val driverController = DriverController(this)
        val driverID = driverController.getDriverIdByName(originalDriverName.toString())
        val success = driverController.updateDriver(
            driverID,
            originalDriverName.toString(),
            updatedPoints,
            updatedPodiumsAll,
            updatedPodiums,
            updatedFastAll,
            updatedFast,
            updatedStartsAll,
            updatedStarts,
            updatedTeam,
            updatedBday,
            updatedNationality,
            updatedPlace,
            updatedChamp
        )

    }


    private fun loadUserData() {
        val driverController = DriverController(this)
        val driverName = intent.getStringExtra("driverName")
        val driverPosition = intent.getIntExtra("driverPosition", -1)
        val driverPoints = intent.getIntExtra("driverPoints", -1)
        val username = intent.getStringExtra("Username")
        val userController = UserController(this)
        val cursor = driverController.getDriverByName(driverName.toString())
        // Check if cursor is not null and move to the first row
        cursor?.apply {
            if (moveToFirst()) {
                val driverTeam = getString(getColumnIndexOrThrow(DatabaseHelper.DRIVER_TEAM))
                val driverNationality = getString(getColumnIndexOrThrow(DatabaseHelper.DRIVER_COUNTRY))
                val driverFast = getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_FASTEST_LAP_SEASON))
                val driverFastAll = getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_FASTEST_LAP_START))
                val driverPodiums = getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_PODIUMS_SEASON))
                val driverPodiumsAll = getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_PODIUMS_START))
                val driverStarts = getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_GPS_SEASON))
                val driverStartsAll = getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_GPS_START))
                val driverChamp = getInt(getColumnIndexOrThrow(DatabaseHelper.DRIVER_CHAMPIONSHIPS))
                val driverBday = getString(getColumnIndexOrThrow(DatabaseHelper.DRIVER_BIRTH))
                val driverPlace = getString(getColumnIndexOrThrow(DatabaseHelper.DRIVER_FROM))

                findViewById<TextView>(R.id.team).apply {
                    text = "$driverTeam\n"
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<TextView>(R.id.worldChampionships).apply {
                    text = "$driverChamp\n"
                    textSize = 40f // Set text size to 20sp
                }
                // Update UI with fetched driver details
                findViewById<TextView>(R.id.driverName).apply {
                    text = "$driverName\n"
                }
                //findViewById<TextView>(R.id.driverTeam).text = driverTeam
                findViewById<TextView>(R.id.fastestLaps).apply {
                    text = "$driverFast\n"
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<TextView>(R.id.allTimeFastestLaps).apply {
                    text = "$driverFastAll\n"
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<TextView>(R.id.podiums).apply {
                    text = "$driverPodiums\n"
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<TextView>(R.id.allTimePodiums).apply {
                    text = "$driverPodiumsAll\n"
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<TextView>(R.id.gpsEntered).apply {
                    text = "$driverStarts\n"
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<TextView>(R.id.allTimeGpsEntered).apply {
                    text = "$driverStartsAll\n"
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<TextView>(R.id.championshipStanding).apply {
                    text = "$driverPosition\n"
                    textSize = 40f // Set text size to 20sp
                }
                findViewById<TextView>(R.id.championshipPoints).apply {
                    text = "$driverPoints\n"
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<TextView>(R.id.dateOfBirth).apply {
                    text = "$driverBday\n"
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<TextView>(R.id.country).apply {
                    text = "$driverNationality\n"
                    textSize = 20f // Set text size to 20sp
                }
                findViewById<TextView>(R.id.placeOfBirth).apply {
                    text = "$driverPlace\n"
                    textSize = 20f // Set text size to 20sp
                }
                val driverURLS = mapOf(
                    "Max Verstappen" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/M/MAXVER01_Max_Verstappen/maxver01.png",
                    "Sergio Perez" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/S/SERPER01_Sergio_Perez/serper01.png",
                    "Charles Leclerc" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/C/CHALEC01_Charles_Leclerc/chalec01.png",
                    "Lando Norris" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LANNOR01_Lando_Norris/lannor01.png",
                    "Carlos Sainz" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/C/CARSAI01_Carlos_Sainz/carsai01.png",
                    "Oscar Piastri" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/O/OSCPIA01_Oscar_Piastri/oscpia01.png",
                    "George Russell" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/G/GEORUS01_George_Russell/georus01.png",
                    "Fernando Alonso" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/F/FERALO01_Fernando_Alonso/feralo01.png",
                    "Lewis Hamilton" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LEWHAM01_Lewis_Hamilton/lewham01.png",
                    "Yuki Tsunoda" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/Y/YUKTSU01_Yuki_Tsunoda/yuktsu01.png",
                    "Lance Stroll" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LANSTR01_Lance_Stroll/lanstr01.png",
                    "Oliver Bearman" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/O/OLIBEA01_Oliver_Bearman/olibea01.png",
                    "Nico Hulkenberg" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/N/NICHUL01_Nico_Hulkenberg/nichul01.png",
                    "Daniel Ricciardo" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/D/DANRIC01_Daniel_Ricciardo/danric01.png",
                    "Esteban Ocon" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/E/ESTOCO01_Esteban_Ocon/estoco01.png",
                    "Kevin Magnussen" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/K/KEVMAG01_Kevin_Magnussen/kevmag01.png",
                    "Alexander Albon" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/A/ALEALB01_Alexander_Albon/alealb01.png",
                    "Guanyu Zhou" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/G/GUAZHO01_Guanyu_Zhou/guazho01.png",
                    "Pierre Gasly" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/P/PIEGAS01_Pierre_Gasly/piegas01.png",
                    "Valtteri Bottas" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/V/VALBOT01_Valtteri_Bottas/valbot01.png",
                    "Logan Sargeant" to "https://media.formula1.com/d_driver_fallback_image.png/content/dam/fom-website/drivers/L/LOGSAR01_Logan_Sargeant/logsar01.png"
                )

                val driverURL = driverURLS[driverName]

                if (driverURL != null) {
                    val driverIMG = findViewById<ImageView>(R.id.driverPicture)
                    Picasso.get().load(driverURL).into(driverIMG)
                } else {
                    // Handle case where track name is not found
                }

            }
            // Close the cursor after use
            close()
        }
    }
        private fun showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
}
