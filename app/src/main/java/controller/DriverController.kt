package controller

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.DatabaseHelper
import java.security.MessageDigest

class DriverController(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    fun addDriver(name: String, points: Int, podiumsStart: Int, podiumsSeason: Int, fastestLapStart: Int, fastestLapSeason: Int,
                  gpsStart: Int, gpsSeason: Int, teamName: String, country: String, birth: String, from: String, championships: Int) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.DRIVER_NAME, name)
            put(DatabaseHelper.DRIVER_POINTS, points)
            put(DatabaseHelper.DRIVER_PODIUMS_START, podiumsStart)
            put(DatabaseHelper.DRIVER_PODIUMS_SEASON, podiumsSeason)
            put(DatabaseHelper.DRIVER_FASTEST_LAP_START, fastestLapStart)
            put(DatabaseHelper.DRIVER_FASTEST_LAP_SEASON, fastestLapSeason)
            put(DatabaseHelper.DRIVER_GPS_START, gpsStart)
            put(DatabaseHelper.DRIVER_GPS_SEASON, gpsSeason)
            put(DatabaseHelper.DRIVER_TEAM, teamName)
            put(DatabaseHelper.DRIVER_COUNTRY, country)
            put(DatabaseHelper.DRIVER_BIRTH, birth)
            put(DatabaseHelper.DRIVER_FROM, from)
            put(DatabaseHelper.DRIVER_CHAMPIONSHIPS, championships)
        }
        db.insert(DatabaseHelper.DRIVERS_TABLE, null, contentValues)
        db.close()
    }
    fun getAllDrivers(): Cursor? {
        val db = dbHelper.readableDatabase
        return db.query(DatabaseHelper.DRIVERS_TABLE, null, null, null, null, null, null)
    }

    fun getDriversByTeam(teamName: String): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.DRIVER_TEAM} = ?"
        val selectionArgs = arrayOf(teamName)
        return db.query(DatabaseHelper.DRIVERS_TABLE, null, selection, selectionArgs, null, null, null)
    }
    fun getDriversByCountry(country: String): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.DRIVER_COUNTRY} = ?"
        val selectionArgs = arrayOf(country)
        return db.query(DatabaseHelper.DRIVERS_TABLE, null, selection, selectionArgs, null, null, null)
    }
    fun getDriversWithMostChampionships(): Cursor? {
        val db = dbHelper.readableDatabase
        return db.query(DatabaseHelper.DRIVERS_TABLE, null, null, null, null, null, "${DatabaseHelper.DRIVER_CHAMPIONSHIPS} DESC")
    }
    fun getDriversWithMostGrandPrixStarts(): Cursor? {
        val db = dbHelper.readableDatabase
        return db.query(DatabaseHelper.DRIVERS_TABLE, null, null, null, null, null, "${DatabaseHelper.DRIVER_GPS_START} DESC")
    }
    fun getDriverById(driverId: Int): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.DRIVER_ID} = ?"
        val selectionArgs = arrayOf(driverId.toString())
        return db.query(DatabaseHelper.DRIVERS_TABLE, null, selection, selectionArgs, null, null, null)
    }
    fun updateDriverPoints(driverId: Int, newPoints: Int) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.DRIVER_POINTS, newPoints)
        }
        val selection = "${DatabaseHelper.DRIVER_ID} = ?"
        val selectionArgs = arrayOf(driverId.toString())
        db.update(DatabaseHelper.DRIVERS_TABLE, values, selection, selectionArgs)
        db.close()
    }
    fun getDriverPoints(driverId: Int): Int {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.DRIVER_ID} = ?"
        val selectionArgs = arrayOf(driverId.toString())
        val cursor = db.query(
            DatabaseHelper.DRIVERS_TABLE,
            arrayOf(DatabaseHelper.DRIVER_POINTS),
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        var points = 0
        cursor?.use { c ->
            if (c.moveToFirst()) {
                points = c.getInt(c.getColumnIndexOrThrow(DatabaseHelper.DRIVER_POINTS))
            }
        }
        return points
    }

    fun deleteDriverById(driverId: Int) {
        val db = dbHelper.writableDatabase
        val selection = "${DatabaseHelper.DRIVER_ID} = ?"
        val selectionArgs = arrayOf(driverId.toString())
        db.delete(DatabaseHelper.DRIVERS_TABLE, selection, selectionArgs)
        db.close()
    }
    fun getDriverByName(driverName: String): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.DRIVER_NAME} = ?"
        val selectionArgs = arrayOf(driverName)
        return db.query(DatabaseHelper.DRIVERS_TABLE, null, selection, selectionArgs, null, null, null)
    }
    fun addDriverGPS(driverId: Int, gpsToAdd: Int) {
        updateDriverAttribute(driverId, DatabaseHelper.DRIVER_GPS_START, gpsToAdd, "+")
    }

    fun addDriverPoints(driverId: Int, pointsToAdd: Int) {
        updateDriverAttribute(driverId, DatabaseHelper.DRIVER_POINTS, pointsToAdd, "+")
    }

    fun addDriverFastestLap(driverId: Int, fastestLapToAdd: Int) {
        updateDriverAttribute(driverId, DatabaseHelper.DRIVER_FASTEST_LAP_START, fastestLapToAdd, "+")
    }

    fun addDriverPodiums(driverId: Int, podiumsToAdd: Int) {
        updateDriverAttribute(driverId, DatabaseHelper.DRIVER_PODIUMS_START, podiumsToAdd, "+")
    }

    fun addDriverChampionship(driverId: Int, championshipsToAdd: Int) {
        updateDriverAttribute(driverId, DatabaseHelper.DRIVER_CHAMPIONSHIPS, championshipsToAdd, "+")
    }

    fun updateDriver(
        driverId: Int,
        name: String,
        points: Int,
        podiumsStart: Int,
        podiumsSeason: Int,
        fastestLapStart: Int,
        fastestLapSeason: Int,
        gpsStart: Int,
        gpsSeason: Int,
        teamName: String,
        country: String,
        birth: String,
        from: String,
        championships: Int
    ) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.DRIVER_NAME, name)
            put(DatabaseHelper.DRIVER_POINTS, points)
            put(DatabaseHelper.DRIVER_PODIUMS_START, podiumsStart)
            put(DatabaseHelper.DRIVER_PODIUMS_SEASON, podiumsSeason)
            put(DatabaseHelper.DRIVER_FASTEST_LAP_START, fastestLapStart)
            put(DatabaseHelper.DRIVER_FASTEST_LAP_SEASON, fastestLapSeason)
            put(DatabaseHelper.DRIVER_GPS_START, gpsStart)
            put(DatabaseHelper.DRIVER_GPS_SEASON, gpsSeason)
            put(DatabaseHelper.DRIVER_TEAM, teamName)
            put(DatabaseHelper.DRIVER_COUNTRY, country)
            put(DatabaseHelper.DRIVER_BIRTH, birth)
            put(DatabaseHelper.DRIVER_FROM, from)
            put(DatabaseHelper.DRIVER_CHAMPIONSHIPS, championships)
        }
        val selection = "${DatabaseHelper.DRIVER_ID} = ?"
        val selectionArgs = arrayOf(driverId.toString())
        db.update(DatabaseHelper.DRIVERS_TABLE, contentValues, selection, selectionArgs)
        db.close()
    }
    fun getTeamByDriverName(driverName: String): String? {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(DatabaseHelper.DRIVER_TEAM)
        val selection = "${DatabaseHelper.DRIVER_NAME} = ?"
        val selectionArgs = arrayOf(driverName)

        var teamName: String? = null

        db.query(
            DatabaseHelper.DRIVERS_TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                teamName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DRIVER_TEAM))
            }
        }

        return teamName
    }

    fun updateDriverTeam(driverName: String, newTeamName: String): Boolean {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.DRIVER_TEAM, newTeamName)
        }
        val selection = "${DatabaseHelper.DRIVER_NAME} = ?"
        val selectionArgs = arrayOf(driverName)
        val rowsAffected = db.update(DatabaseHelper.DRIVERS_TABLE, contentValues, selection, selectionArgs)
        db.close()
        return rowsAffected > 0
    }

    private fun updateDriverAttribute(driverId: Int, attribute: String, value: Int, operation: String) {
        val db = dbHelper.writableDatabase
        val query = "UPDATE ${DatabaseHelper.DRIVERS_TABLE} " +
                "SET $attribute = CASE " +
                "WHEN $operation = '+' THEN $attribute + $value " +
                "WHEN $operation = '-' THEN $attribute - $value " +
                "END " +
                "WHERE ${DatabaseHelper.DRIVER_ID} = ?"
        val selectionArgs = arrayOf(driverId.toString())
        db.execSQL(query, selectionArgs)
        db.close()
    }
    fun getDriverIdByName(driverName: String): Int {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(DatabaseHelper.DRIVER_ID)
        val selection = "${DatabaseHelper.DRIVER_NAME} = ?"
        val selectionArgs = arrayOf(driverName)
        val cursor = db.query(
            DatabaseHelper.DRIVERS_TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        var driverId = -1 // Default value if driver not found
        cursor.use {
            if (it.moveToFirst()) {
                driverId = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.DRIVER_ID))
            }
        }
        return driverId
    }

}