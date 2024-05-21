package com.example.fanf1

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.squareup.picasso.Picasso
import controller.CircuitController
import controller.RaceController
import controller.UserController
import model.DatabaseHelper

class CircuitActivity : ComponentActivity() {
    private var circuitN = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        val circuitController = CircuitController(this)
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_circuit)
        val username = intent.getStringExtra("Username")
        // Retrieve circuit data from the intent extras
        val circuitName = intent.getStringExtra("name")
        circuitN = circuitName.toString()
        val userController = UserController(this)
        // Get cursor for the circuit by name
        val cursor = circuitController.getCircuitByName(intent.getStringExtra("name").toString())

        // Check if cursor is not null and move to the first row
        cursor?.apply {
            if (moveToFirst()) {
                // Extract circuit details from the cursor
                val circuitLength = getString(getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_LENGTH))
                val circuitLaps = getInt(getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_LAPS))
                val firstGP = getString(getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_FIRST))
                val raceDistance = getString(getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_DISTANCE))
                val lapRecord = getString(getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_RECORD))
                val isAdmin = userController.isAdmin(username.toString())

                if (isAdmin){
                    setContentView(R.layout.admin_layout_circuit)
                    findViewById<EditText>(R.id.circuitName).apply {
                        setText(circuitName)
                        textSize = 28f // Changed text size to 28sp
                    }

                    findViewById<EditText>(R.id.circuitLength).apply {
                        setText("$circuitLength")
                        textSize = 24f // Changed text size to 24sp
                    }

                    findViewById<EditText>(R.id.circuitLaps).apply {
                        setText("$circuitLaps")
                        textSize = 24f // Changed text size to 24sp
                    }

                    findViewById<EditText>(R.id.firstGP).apply {
                        setText("$firstGP")
                        textSize = 24f // Changed text size to 24sp
                    }

                    findViewById<EditText>(R.id.raceDistance).apply {
                        setText("$raceDistance")
                        textSize = 24f // Changed text size to 24sp
                    }

                    findViewById<EditText>(R.id.lapRecord).apply {
                        setText("$lapRecord")
                        textSize = 24f // Changed text size to 24sp
                    }

                }
                else{
                    setContentView(R.layout.activity_circuit)
                    findViewById<TextView>(R.id.circuitName).apply {
                        text = circuitName
                        textSize = 28f // Changed text size to 28sp
                    }
                    findViewById<TextView>(R.id.circuitLength).apply {
                        text = "$circuitLength km"
                        textSize = 24f // Changed text size to 24sp
                    }
                    findViewById<TextView>(R.id.circuitLaps).apply {
                        text = "$circuitLaps"
                        textSize = 24f // Changed text size to 24sp
                    }
                    findViewById<TextView>(R.id.firstGP).apply {
                        text = "$firstGP"
                        textSize = 24f // Changed text size to 24sp
                    }
                    findViewById<TextView>(R.id.raceDistance).apply {
                        text = "$raceDistance km"
                        textSize = 24f // Changed text size to 24sp
                    }
                    findViewById<TextView>(R.id.lapRecord).apply {
                        text = "$lapRecord"
                        textSize = 24f // Changed text size to 24sp
                    }
                }
                // Set circuit details to corresponding TextViews

                // Map track names to their corresponding image URLs
                val trackImageURLs = mapOf(
                    "Bahrain" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244985/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Bahrain_Circuit.png.transform/5col/image.png",
                    "Saudi Arabia" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244985/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Saudi_Arabia_Circuit.png.transform/5col/image.png",
                    "Australia" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244985/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Australia_Circuit.png.transform/5col/image.png",
                    "Japan" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677250050/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Japan_Circuit.png.transform/5col/image.png",
                    "China" to "https://media.formula1.com/image/upload/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/China_Circuit.png.transform/5col/image.png",
                    "U.S.A. Miami" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244985/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Miami_Circuit.png.transform/5col/image.png",
                    "Italy Imola" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244984/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Emilia_Romagna_Circuit.png.transform/5col/image.png",
                    "Monaco" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244984/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Monoco_Circuit.png.transform/5col/image.png",
                    "Canada" to "https://media.formula1.com/image/upload/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Canada_Circuit.png.transform/5col/image.png",
                    "Spain" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244986/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Spain_Circuit.png.transform/5col/image.png",
                    "Austria" to "https://media.formula1.com/image/upload/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Austria_Circuit.png.transform/5col/image.png",
                    "Great Britain" to "https://media.formula1.com/image/upload/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Great_Britain_Circuit.png.transform/5col/image.png",
                    "Hungary" to "https://media.formula1.com/image/upload/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Hungary_Circuit.png.transform/5col/image.png",
                    "Belgium" to "https://media.formula1.com/image/upload/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Belgium_Circuit.png.transform/5col/image.png",
                    "Netherlands" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244984/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Netherlands_Circuit.png.transform/5col/image.png",
                    "Italy Monza" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244987/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Italy_Circuit.png.transform/5col/image.png",
                    "Azerbaijan" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244987/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Baku_Circuit.png.transform/5col/image.png",
                    "Singapore" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1683633963/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Singapore_Circuit.png.transform/5col/image.png",
                    "U.S.A. Texas" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244984/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/USA_Circuit.png.transform/5col/image.png",
                    "Mexico" to "https://media.formula1.com/image/upload/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Mexico_Circuit.png.transform/5col/image.png",
                    "Brazil" to "https://media.formula1.com/image/upload/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Brazil_Circuit.png.transform/5col/image.png",
                    "U.S.A. Las Vegas" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677249930/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Las_Vegas_Circuit.png.transform/5col/image.png",
                    "Qatar" to "https://media.formula1.com/image/upload/f_auto/q_auto/v1677244985/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Qatar_Circuit.png.transform/5col/image.png",
                    "Abu Dhabi" to "https://media.formula1.com/image/upload/content/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/Abu_Dhabi_Circuit.png.transform/5col/image.png"
                    )

                val trackImageURL = trackImageURLs[circuitName]

                if (trackImageURL != null) {
                    val trackImageView = findViewById<ImageView>(R.id.trackImage)
                    Picasso.get().load(trackImageURL).into(trackImageView)
                } else {
                    // Handle case where track name is not found
                }

            }
            // Close the cursor after use
            close()
        }
    }

    fun confirmChanges(view: View) {
        val raceController = RaceController(this)
        val circuitNameEditText = findViewById<EditText>(R.id.circuitName)
        val circuitLengthEditText = findViewById<EditText>(R.id.circuitLength)
        val circuitLapsEditText = findViewById<EditText>(R.id.circuitLaps)
        val firstGPEditText = findViewById<EditText>(R.id.firstGP)
        val raceDistanceEditText = findViewById<EditText>(R.id.raceDistance)
        val lapRecordEditText = findViewById<EditText>(R.id.lapRecord)

        val circuitName = circuitNameEditText.text.toString()
        val circuitLength = circuitLengthEditText.text.toString().toFloat()
        val circuitLaps = circuitLapsEditText.text.toString().toInt()
        val firstGP = firstGPEditText.text.toString().toInt()
        val raceDistance = raceDistanceEditText.text.toString().toFloat()
        val lapRecord = lapRecordEditText.text.toString()

        val raceId = raceController.getRaceIDByName(circuitName)
        val circuitController = CircuitController(this)
        val circuitID = circuitController.getCircuitIDByName(circuitName)
        if (raceId != null) {
            // Update race details if race ID is found
            if (circuitID != null) {
                circuitController.updateCircuit(circuitID,circuitLength,circuitLaps,firstGP,raceDistance,circuitName,lapRecord)
            }
        }
    }
}
