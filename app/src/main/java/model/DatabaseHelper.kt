package model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,2) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createUsersTable =
            "CREATE TABLE $USERS_TABLE (" +
                    "$USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$USER_USERNAME VARCHAR(50), "+
                    "$USER_PASSWORD VARCHAR(50), "+
                    "$USER_ISADMIN INTEGER)"
        val createCircuitTable =
            "CREATE TABLE $CIRCUIT_TABLE (" +
                    "$CIRCUIT_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$CIRCUIT_LENGTH REAL, " +
                    "$CIRCUIT_LAPS INTEGER, " +
                    "$CIRCUIT_FIRST INTEGER, " +
                    "$CIRCUIT_DISTANCE REAL, " +
                    "$CIRCUIT_NAME TEXT, " +
                    "$CIRCUIT_RECORD TEXT)"
        val createRaceTable =
            "CREATE TABLE $RACES_TABLE (" +
                    "$RACE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$RACE_ROUND INTEGER, " +
                    "$RACE_DATE TEXT, " +
                    "$RACE_NAME TEXT, " +
                    "FOREIGN KEY($RACE_NAME) REFERENCES $CIRCUIT_TABLE($CIRCUIT_NAME))"

        val createDriverTable =
            "CREATE TABLE $DRIVERS_TABLE (" +
                    "$DRIVER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$DRIVER_NAME TEXT, " +
                    "$DRIVER_POINTS INTEGER, " +
                    "$DRIVER_PODIUMS_START INTEGER, " +
                    "$DRIVER_PODIUMS_SEASON INTEGER, " +
                    "$DRIVER_FASTEST_LAP_START INTEGER, " +
                    "$DRIVER_FASTEST_LAP_SEASON INTEGER, " +
                    "$DRIVER_GPS_START INTEGER, " +
                    "$DRIVER_GPS_SEASON INTEGER, " +
                    "$DRIVER_TEAM TEXT, " +
                    "$DRIVER_COUNTRY TEXT, " +
                    "$DRIVER_BIRTH TEXT, " +
                    "$DRIVER_FROM TEXT, " +
                    "$DRIVER_CHAMPIONSHIPS INTEGER)"

        val createTeamTable =
            "CREATE TABLE $TEAMS_TABLE (" +
                    "$TEAM_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$TEAM_NAME TEXT, " +
                    "$TEAM_POINTS INTEGER, " +
                    "$TEAM_DRIVER1 TEXT, " +
                    "$TEAM_DRIVER2 TEXT, " +
                    "$HIGHEST_FINISH_ALL INTEGER, " +
                    "$HIGHEST_FINISH_NOW INTEGER, " +
                    "$POLES_ALL INTEGER, " +
                    "$POLES_NOW INTEGER, " +
                    "$FAST_LAP_ALL INTEGER, " +
                    "$FAST_LAP_NOW INTEGER, " +
                    "$CHAMPIONSHIP INTEGER, " +
                    "$BASE TEXT, " +
                    "$CHIEF TEXT, " +
                    "$CHASSIS TEXT, " +
                    "$POWER_UNIT TEXT, " +
                    "FOREIGN KEY($TEAM_DRIVER1) REFERENCES $DRIVERS_TABLE($DRIVER_NAME), " +
                    "FOREIGN KEY($TEAM_DRIVER2) REFERENCES $DRIVERS_TABLE($DRIVER_NAME))"

        db?.execSQL(createCircuitTable)
        db?.execSQL(createDriverTable)
        db?.execSQL(createRaceTable)
        db?.execSQL(createTeamTable)
        db?.execSQL(createUsersTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $USERS_TABLE")
        }
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $TEAMS_TABLE")
        }
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $DRIVERS_TABLE")
        }
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $CIRCUIT_TABLE")
        }
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $RACES_TABLE")
        }
        onCreate(db)

    }

    companion object {
        // Database version
        private const val DATABASE_VERSION = 1
        // Database name
        private const val DATABASE_NAME = "my_database"
        // Users table name and column names
        const val USERS_TABLE = "users"
        const val USER_ID = "user_id"
        const val USER_USERNAME = "username"
        const val USER_PASSWORD = "password"
        const val USER_ISADMIN = "is_admin"
        // Race
        const val RACES_TABLE = "races"
        const val RACE_ID = "race_id"
        const val RACE_ROUND = "race_round"
        const val RACE_DATE = "race_date"
        const val RACE_NAME = "race_name"
        // Circuit
        const val CIRCUIT_TABLE = "circuit"
        const val CIRCUIT_ID = "circuit_id"
        const val CIRCUIT_NAME = "circuit_name"
        const val CIRCUIT_LENGTH = "circuit_length"
        const val CIRCUIT_LAPS = "circuit_laps"
        const val CIRCUIT_FIRST = "circuit_first"
        const val CIRCUIT_DISTANCE = "circuit_distance"
        const val CIRCUIT_RECORD = "circuit_record"
        // Driver
        const val DRIVERS_TABLE = "drivers"
        const val DRIVER_ID = "driver_id"
        const val DRIVER_POINTS = "driver_points"
        const val DRIVER_PODIUMS_START = "driver_all_podiums"
        const val DRIVER_PODIUMS_SEASON = "driver_now_podiums"
        const val DRIVER_FASTEST_LAP_START = "driver_fast_all"
        const val DRIVER_FASTEST_LAP_SEASON = "driver_fast_now"
        const val DRIVER_GPS_START = "driver_gp_all"
        const val DRIVER_GPS_SEASON = "driver_gp_now"
        const val DRIVER_TEAM = "driver_team"
        const val DRIVER_COUNTRY = "driver_country"
        const val DRIVER_BIRTH = "driver_bday"
        const val DRIVER_FROM = "driver_place"
        const val DRIVER_CHAMPIONSHIPS = "driver_champ"
        const val DRIVER_NAME = "driver_name"
        // Team
        const val TEAMS_TABLE = "teams"
        const val TEAM_ID = "team_id"
        const val TEAM_POINTS = "team_points"
        const val TEAM_DRIVER1 = "team_d1"
        const val TEAM_DRIVER2 = "team_d2"
        const val HIGHEST_FINISH_ALL = "team_highest_pos_all"
        const val HIGHEST_FINISH_NOW = "team_highest_pos_now"
        const val POLES_NOW = "team_poles_now"
        const val POLES_ALL = "team_poles_all"
        const val FAST_LAP_NOW = "team_fastest_lap_now"
        const val FAST_LAP_ALL = "team_fastest_lap_all"
        const val CHAMPIONSHIP = "team_champs"
        const val BASE = "team_base"
        const val CHIEF = "team_chief"
        const val CHASSIS = "team_chassis"
        const val POWER_UNIT = "team_PU"
        const val TEAM_NAME = "team_name"


    }

}
