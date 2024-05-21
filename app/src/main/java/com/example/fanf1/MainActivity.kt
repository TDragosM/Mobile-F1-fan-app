@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.fanf1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fanf1.ui.theme.FanF1Theme
import controller.CircuitController
import controller.DriverController
import controller.RaceController
import controller.TeamController
import controller.UserController
import model.DatabaseHelper
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DatabaseHelper(this)
        val userController = UserController(this)
        val circuitController = CircuitController(this)
        val driverController = DriverController(this)
        val teamController = TeamController(this)
        val raceController = RaceController(this)
        /*circuitController.addCircuit(5.412F,57,2004, 308.238F,"Bahrain", "1.31.447")
        circuitController.addCircuit(6.174F,50,2021, 308.450F,"Saudi Arabia", "1.30.734")
        circuitController.addCircuit(5.278F,58,1996, 306.124F,"Australia", "1.19.813")
        circuitController.addCircuit(5.807F,53,1987, 307.471F,"Japan", "1.30.983")
        circuitController.addCircuit(5.451F,56,2004, 305.006F,"China", "1.32.238")
        circuitController.addCircuit(5.412F,57,2022, 308.326F,"U.S.A. Miami", "1.29.708")
        circuitController.addCircuit(4.909F,63,1980, 309.049F,"Italy Imola", "1.15.484")
        circuitController.addCircuit(3.337F,78,1950, 260.286F,"Monaco", "1.12.909")
        circuitController.addCircuit(4.361F,70,1978, 305.270F,"Canada", "1.13.078")
        circuitController.addCircuit(4.657F,66,1991, 307.236F,"Spain", "1.16.330")
        circuitController.addCircuit(4.318F,71,1970, 306.452F,"Austria", "1.05.619")
        circuitController.addCircuit(5.891F,52,1950, 306.198F,"Great Britain", "1.27.097")
        circuitController.addCircuit(4.381F,70,1986, 306.630F,"Hungary", "1.16.627")
        circuitController.addCircuit(7.004F,44,1950, 308.052F,"Belgium", "1.46.286")
        circuitController.addCircuit(4.259F,72,1952, 306.587F,"Netherlands", "1.11.097")
        circuitController.addCircuit(5.793F,53,1950, 306.720F,"Italy Monza", "1.21.046")
        circuitController.addCircuit(6.003F,51,2016, 306.049F,"Azerbaijan", "1.42.009")
        circuitController.addCircuit(4.940F,62,2008, 306.143F,"Singapore", "1.35.867")
        circuitController.addCircuit(5.513F,56,2012, 308.405F,"U.S.A. Texas", "1.36.169")
        circuitController.addCircuit(4.304F,71,1963, 305.354F,"Mexico", "1.17.774")
        circuitController.addCircuit(4.309F,71,1973, 305.879F,"Brazil", "1.10.540")
        circuitController.addCircuit(6.201F,50,2023, 309.958F,"U.S.A. Las Vegas", "1.35.490")
        circuitController.addCircuit(5.419F,57,2021, 308.611F,"Qatar", "1.24.319")
        circuitController.addCircuit(5.281F,58,2009, 306.183F,"Abu Dhabi", "1.26.103")
        val cursor = circuitController.getAllCircuits()
        cursor?.use {
            if(cursor.moveToFirst()){
                do{
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_NAME))
                    val first = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_FIRST))
                    val length = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_LENGTH))
                    val laps = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_LAPS))
                    val distance = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_DISTANCE))
                    val record = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_RECORD))
                    println("Circuit ID: $id, Name: $name, First Grand Prix: $first, Circuit Length: $length km, No. of laps: $laps, Race Distance: $distance km, Lap record: $record")
                } while (cursor.moveToNext())
            }
        }
        raceController.addRace(1,"02 Mar 2024", "Bahrain")
        raceController.addRace(2,"09 Mar 2024", "Saudi Arabia")
        raceController.addRace(3,"24 Mar 2024", "Australia")
        raceController.addRace(4, "07 Apr 2024", "Japan")
        raceController.addRace(5, "21 Apr 2024", "China")
        raceController.addRace(6, "05 May 2024", " U.S.A. Miami")
        raceController.addRace(7, "19 May 2024", "Italy Imola")
        raceController.addRace(8, "26 May 2024", "Monaco")
        raceController.addRace(9, "09 Jun 2024", "Canada")
        raceController.addRace(10, "23 Jun 2024", "Spain")
        raceController.addRace(11, "30 Jun 2024", "Austria")
        raceController.addRace(12, "07 Jul 2024", "Great Britain")
        raceController.addRace(13, "21 Jul 2024", "Hungary")
        raceController.addRace(14, "28 Jul 2024", "Belgium")
        raceController.addRace(15, "25 Aug 2024", "Netherlands")
        raceController.addRace(16, "01 Sep 2024", "Italy Monza")
        raceController.addRace(17, "15 Sep 2024","Azerbaijan")
        raceController.addRace(18,"22 Sep 2024", "Singapore")
        raceController.addRace(19, "20 Oct 2024", "U.S.A. Texas")
        raceController.addRace(20, "27 Oct 2024", "Mexico")
        raceController.addRace(21,"03 Nov 2024", "Brazil")
        raceController.addRace(22, "23 Nov 2024", "U.S.A. Las Vegas")
        raceController.addRace(23, "01 Dec 2024", "Qatar")
        raceController.addRace(24,"08 Dec 2024", "Abu Dhabi")
        val cursor2 = raceController.getAllRaces()
        cursor2?.use { cursor2 ->
            if (cursor2.moveToFirst()){
                do {
                    val id = cursor2.getInt(cursor2.getColumnIndexOrThrow(DatabaseHelper.RACE_ID))
                    val round = cursor2.getInt(cursor2.getColumnIndexOrThrow(DatabaseHelper.RACE_ROUND))
                    val date = cursor2.getString(cursor2.getColumnIndexOrThrow(DatabaseHelper.RACE_DATE))
                    val name = cursor2.getString(cursor2.getColumnIndexOrThrow(DatabaseHelper.RACE_NAME))
                    println("Race ID: $id, Round: $round, Race Name: $name, Race Date: $date")
                } while (cursor2.moveToNext())
            }
        }

        driverController.addDriver("Max Verstappen",77,101,3,32,2,189,4,"Red Bull Racing",
            "Ned", "30/09/1997", "Hasselt, BELGIUM", 3)
        driverController.addDriver("Sergio Perez", 54, 38,3,11,0,262,4,"Red Bull Racing",
            "Mex","26/01/1990","Guadalajara, MEXICO", 0)
        driverController.addDriver("Charles Leclerc",59,32,2,9,2,129,4,"Ferrari",
            "Mon","16/10/1997","Monte Carlo, MONACO", 0)
        driverController.addDriver("Carlos Sainz", 55,21,3,3,0,188,3,"Ferrari",
            "ESP","01/09/1994","Madrid, SPAIN", 0)
        driverController.addDriver("Lando Norris",37,14,1,6,0,108,4,"McLaren",
            "GBR", "13/11/1999", "Bristol, ENGLAND", 0)
        driverController.addDriver("Oscar Piastri", 32,2,0,2,0,26,4,"McLaren",
            "AUS", "06/04/2001", "Melbourne, VICTORIA", 0)
        driverController.addDriver("George Russell", 24,11,0,6,0,108,4,"Mercedes",
            "GBR", "15/02/1998", "King's Lynn, ENGLAND", 0)
        driverController.addDriver("Fernando Alonso",24,106,0,24,0,384,4,"Aston Martin",
            "ESP", "29/07/1981", "Oviedo, SPAIN", 2)
        driverController.addDriver("Lewis Hamilton",10,197,0,65,0,336,4,"Mercedes",
            "GBR", "07/01/1985", "Stevenage, ENGLAND", 7)
        driverController.addDriver("Lance Stroll",9,3,0,0,0,147,4,"Aston Martin",
            "CAN","29/10/1998", "Montreal, CANADA", 0)
        driverController.addDriver("Yuki Tsunoda", 7,0,0,1,0,70,4,"RB",
            "JPN","11/05/2000", "Sagamihara, JAPAN", 0)
        driverController.addDriver("Oliver Bearman",6,0,0,0,0,1,1, "Ferrari",
            "GBR","08/05/2005", "Chelmsford, ENGLAND", 0)
        driverController.addDriver("Nico Hulkenberg", 3,0,0,2,0,210,4,"Haas F1 Team",
            "GER", "19/08/1987", "Emmerich am Rhein, GERMANY", 0)
        driverController.addDriver("Kevin Magnussen",1,1,0,2,0,168,4,"Haas F1 Team",
            "DEN", "05/10/1992", "Roskilde, DENMARK", 0)
        driverController.addDriver("Alexander Albon",0,2,0,0,0,85,4,"Williams",
            "THA", "23/03/1996", "London, ENGLAND", 0)
        driverController.addDriver("Guanyu Zhou",0,0,0,2,0,48,4,"Kick Sauber",
            "CHN", "30/05/1999", "Shanghai, CHINA",0)
        driverController.addDriver("Daniel Ricciardo",0,32,0,16,0,243,4,"RB",
            "AUS","01/07/1989","Perth, AUSTRALIA",0)
        driverController.addDriver("Esteban Ocon",0,3,0,0,0,137,4,"Alpine",
            "FRA", "17/09/1996", "Evreux, NORMANDY",0)
        driverController.addDriver("Pierre Gasly",0,4,0,3,0,134,4,"Alpine",
            "FRA", "07/02/1996", "Rouen, FRANCE", 0 )
        driverController.addDriver("Valtteri Bottas",0,67,0,19,0,226,4,"Kick Sauber",
            "FIN","28/08/1989", "Nastola, FINLAND", 0)
        driverController.addDriver("Logan Sargeant", 0,0,0,0,0,25,4,"Williams",
            "USA", "31/12/2000", "Fort Lauderdale, FLORIDA", 0)
        val cursor3 = driverController.getAllDrivers()
        cursor3?.use {cursor3 ->
            if (cursor3.moveToFirst()){
                do {
                    val id = cursor3.getInt(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_ID))
                    val name = cursor3.getString(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_NAME))
                    val points = cursor3.getInt(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_POINTS))
                    val podiumsStart= cursor3.getInt(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_PODIUMS_START))
                    val podiumsSeason = cursor3.getInt(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_PODIUMS_SEASON))
                    val fastLapStart = cursor3.getInt(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_FASTEST_LAP_START))
                    val fastLapSeason = cursor3.getInt(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_FASTEST_LAP_SEASON))
                    val gpsStart = cursor3.getInt(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_GPS_START))
                    val gpsSeason = cursor3.getInt(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_GPS_SEASON))
                    val teamName = cursor3.getString(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_TEAM))
                    val country = cursor3.getString(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_COUNTRY))
                    val bday = cursor3.getString(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_BIRTH))
                    val place = cursor3.getString(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_FROM))
                    val champs = cursor3.getInt(cursor3.getColumnIndexOrThrow(DatabaseHelper.DRIVER_CHAMPIONSHIPS))
                    println("Driver ID: $id, Name: $name, Points: $points, Podiums: $podiumsSeason this season, $podiumsStart all seasons, Fastest laps:" +
                            " $fastLapSeason this season, $fastLapStart all seasons, Grand Prix Starts: $gpsSeason this season, $gpsStart all seasons, " +
                            "Team: $teamName, Championships: $champs, Country: $country, Place of Birth: $place, Date of Birth: $bday")
                } while (cursor3.moveToNext())
            }
        }
        teamController.addTeam("Red Bull Racing", 141, "Max Verstappen", "Sergio Perez", 1,1,99,3,
            97,2,6,"Milton Keynes", "Christian Horner", "RB20", "Honda RBPT")
        teamController.addTeam("Ferrari", 120,"Charles Leclerc", "Carlos Sainz", 1,1,249,0,
            261,2,16,"Maranello", "Frederic Vasseur", "SF-24", "Ferrari")
        teamController.addTeam("McLaren", 69,"Oscar Piastri", "Lando Norris", 1,3,156,0,
            165,0,8,"Woking","Andrea Stella","MCL38", "Mercedes")
        teamController.addTeam("Mercedes",34,"Lewis Hamilton", "George Russell", 1,5,129,0,
            96,0,8,"Brackley", "Toto Wolff", "W15", "Mercedes")
        teamController.addTeam("Aston Martin", 33, "Lance Stroll", "Fernando Alonso", 1,5,1,0,
            1,0,0,"Silverstone", "Mike Krack", "AMR24","Mercedes")
        teamController.addTeam("RB",7,"Daniel Ricciardo", "Yuki Tsunoda", 1,7,1,0,
            3,0,0,"Faenza", "Laurent Mekies", "VCARB 01", "Honda RBPT")
        teamController.addTeam("Haas F1 Team", 4, "Kevin Magnussen", "Nico Hunkenberg", 4,9,1,0,
            2,0,0,"Kannapolis", "Ayao Komatsu", "VF-24", "Ferrari")
        teamController.addTeam("Williams", 0, "Alexander Albon", "Logan Sargeant", 1, 11,128,0,
            133,0,9,"Grove","James Vowles", "FW46", "Mercedes")
        teamController.addTeam("Kick Sauber", 0,"Guanyu Zhou", "Valtteri Bottas", 1,11,1,0,
            7,0,0,"Hinwil","Alessandro Bravi", "C44", "Ferrari")
        teamController.addTeam("Alpine", 0, "Esteban Ocon", "Pierre Gasly", 1,13,20,0,
            15,0,2,"Enstone","Bruno Famin", "A524", "Renault")

        val cursor4 = teamController.getAllTeams()
        cursor4?.use { cursor4->
            if (cursor4.moveToFirst()){
                do{
                    val id = cursor4.getInt(cursor4.getColumnIndexOrThrow(DatabaseHelper.TEAM_ID))
                    val name = cursor4.getString(cursor4.getColumnIndexOrThrow(DatabaseHelper.TEAM_NAME))
                    val points = cursor4.getInt(cursor4.getColumnIndexOrThrow(DatabaseHelper.TEAM_POINTS))
                    val driver1 = cursor4.getString(cursor4.getColumnIndexOrThrow(DatabaseHelper.TEAM_DRIVER1))
                    val driver2 = cursor4.getString(cursor4.getColumnIndexOrThrow(DatabaseHelper.TEAM_DRIVER2))
                    val highAll = cursor4.getInt(cursor4.getColumnIndexOrThrow(DatabaseHelper.HIGHEST_FINISH_ALL))
                    val highNow = cursor4.getInt(cursor4.getColumnIndexOrThrow(DatabaseHelper.HIGHEST_FINISH_NOW))
                    val polesAll = cursor4.getInt(cursor4.getColumnIndexOrThrow(DatabaseHelper.POLES_ALL))
                    val polesNow = cursor4.getInt(cursor4.getColumnIndexOrThrow(DatabaseHelper.POLES_NOW))
                    val fLapAll = cursor4.getInt(cursor4.getColumnIndexOrThrow(DatabaseHelper.FAST_LAP_ALL))
                    val fLapNow = cursor4.getInt(cursor4.getColumnIndexOrThrow(DatabaseHelper.FAST_LAP_NOW))
                    val champs = cursor4.getInt(cursor4.getColumnIndexOrThrow(DatabaseHelper.CHAMPIONSHIP))
                    val base = cursor4.getString(cursor4.getColumnIndexOrThrow(DatabaseHelper.BASE))
                    val chief = cursor4.getString(cursor4.getColumnIndexOrThrow(DatabaseHelper.CHIEF))
                    val chassis = cursor4.getString(cursor4.getColumnIndexOrThrow(DatabaseHelper.CHASSIS))
                    val pu = cursor4.getString(cursor4.getColumnIndexOrThrow(DatabaseHelper.POWER_UNIT))
                    println("Team ID: $id, Team Name: $name, Points: $points, Drivers: $driver1, $driver2, Highest Finishing Position:$highNow now, $highAll all time," +
                            " No. of poles: $polesNow now, $polesAll all time, Fastest Laps: $fLapNow now, $fLapAll all time, No. of championships: $champs " +
                            ", Base: $base, Team chief: $chief, Chassis: $chassis, Power Unit: $pu")
                }while (cursor4.moveToNext())
            }
        }*/

        //userController.deleteUserByUsername("Dragos")
        /*

        Sa vad daca merge aia de users

        val cursor = userController.getAllUsers() // Retrieve cursor from getAllUsers()

        cursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_ID))
                    val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_USERNAME))
                    val password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_PASSWORD))

                    println("User ID: $id, Username: $username, Password: $password")
                } while (cursor.moveToNext())
            }
        }

        //for future use
         val intent = Intent(this, PageName::class.java)
                startActivity(intent)
                finish()
         */
        //userController.addUser("TDM", "dana", 1)
        setContent {
            FanF1Theme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(context= this ,userController)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    context: Context,
    userController: UserController
)
{
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { /* Handle login */ }), //Go to main page, when created
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        // Validate login credentials
                        if (isValidCredentials(username, password, userController)) {
                            Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                            val intent = Intent(context, RacingActivity::class.java)
                            intent.putExtra("Username", username)
                            context.startActivity(intent)
                            println("Login successful")
                        } else {
                            errorMessage = "Invalid username or password"
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Log In")
                }


                Spacer(modifier = Modifier.width(16.dp))
                val intent = Intent(context, CreateAccountActivity::class.java)
                Button(
                    onClick = {
                        context.startActivity(intent)
                    }
                )
                {
                    Text("Create Account")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }

        }
    }

}


fun isValidCredentials(username: String, password: String,userController: UserController): Boolean {
    return if (username.isNotBlank() && password.isNotBlank())
        userController.checkLoginCredentials(username,password)
    else false
}

