package controller
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.DatabaseHelper
import java.security.MessageDigest
class TeamController (context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun addTeam(
        name: String,
        points: Int,
        driver1Name: String,
        driver2Name: String,
        highestFinishAll: Int,
        highestFinishNow: Int,
        polesAll: Int,
        polesNow: Int,
        fastLapAll: Int,
        fastLapNow: Int,
        championship: Int,
        base: String,
        chief: String,
        chassis: String,
        powerUnit: String
    ) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.TEAM_NAME, name)
            put(DatabaseHelper.TEAM_POINTS, points)
            put(DatabaseHelper.TEAM_DRIVER1, driver1Name)
            put(DatabaseHelper.TEAM_DRIVER2, driver2Name)
            put(DatabaseHelper.HIGHEST_FINISH_ALL, highestFinishAll)
            put(DatabaseHelper.HIGHEST_FINISH_NOW, highestFinishNow)
            put(DatabaseHelper.POLES_ALL, polesAll)
            put(DatabaseHelper.POLES_NOW, polesNow)
            put(DatabaseHelper.FAST_LAP_ALL, fastLapAll)
            put(DatabaseHelper.FAST_LAP_NOW, fastLapNow)
            put(DatabaseHelper.CHAMPIONSHIP, championship)
            put(DatabaseHelper.BASE, base)
            put(DatabaseHelper.CHIEF, chief)
            put(DatabaseHelper.CHASSIS, chassis)
            put(DatabaseHelper.POWER_UNIT, powerUnit)
        }
        db.insert(DatabaseHelper.TEAMS_TABLE, null, contentValues)
        db.close()
    }

    fun getAllTeams(): Cursor? {
        val db = dbHelper.readableDatabase
        return db.query(DatabaseHelper.TEAMS_TABLE, null, null, null, null, null, null)
    }

    fun getTeamById(teamId: Int): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.TEAM_ID} = ?"
        val selectionArgs = arrayOf(teamId.toString())
        return db.query(
            DatabaseHelper.TEAMS_TABLE,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
    }
    fun getTeamIdByName(teamName: String): Int {
        var teamId = -1
        val db = dbHelper.readableDatabase
        val projection = arrayOf(DatabaseHelper.TEAM_ID)
        val selection = "${DatabaseHelper.TEAM_NAME} = ?"
        val selectionArgs = arrayOf(teamName)

        db.query(
            DatabaseHelper.TEAMS_TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                teamId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.TEAM_ID))
            }
        }
        return teamId
    }

    fun updateTeam(
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
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.TEAM_POINTS, teamPoints)
            put(DatabaseHelper.TEAM_DRIVER1, teamDriver1)
            put(DatabaseHelper.TEAM_DRIVER2, teamDriver2)
            put(DatabaseHelper.HIGHEST_FINISH_ALL, highestFinishAll)
            put(DatabaseHelper.HIGHEST_FINISH_NOW, highestFinishNow)
            put(DatabaseHelper.POLES_NOW, polesNow)
            put(DatabaseHelper.POLES_ALL, polesAll)
            put(DatabaseHelper.FAST_LAP_NOW, fastLapNow)
            put(DatabaseHelper.FAST_LAP_ALL, fastLapAll)
            put(DatabaseHelper.CHAMPIONSHIP, championships)
            put(DatabaseHelper.BASE, base)
            put(DatabaseHelper.CHIEF, chief)
            put(DatabaseHelper.CHASSIS, chassis)
            put(DatabaseHelper.POWER_UNIT, powerUnit)
        }
        db.update(DatabaseHelper.TEAMS_TABLE, values, "${DatabaseHelper.TEAM_NAME} = ?", arrayOf(teamName))
    }

    fun deleteTeam(teamId: Int) {
        val db = dbHelper.writableDatabase
        val selection = "${DatabaseHelper.TEAM_ID} = ?"
        val selectionArgs = arrayOf(teamId.toString())
        db.delete(DatabaseHelper.TEAMS_TABLE, selection, selectionArgs)
        db.close()
    }

    fun getTeamsWithMostPoints(): Cursor? {
        val db = dbHelper.readableDatabase
        return db.query(DatabaseHelper.TEAMS_TABLE, null, null, null, null, null, "${DatabaseHelper.TEAM_POINTS} DESC")
    }
    fun updateTeamPoints(teamId: Int, newPoints: Int) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.TEAM_POINTS, newPoints)
        }
        val selection = "${DatabaseHelper.TEAM_ID} = ?"
        val selectionArgs = arrayOf(teamId.toString())
        db.update(DatabaseHelper.TEAMS_TABLE, values, selection, selectionArgs)
        db.close()
    }
    fun getTeamByName(name: String): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.TEAM_NAME} = ?"
        val selectionArgs = arrayOf(name)
        return db.query(
            DatabaseHelper.TEAMS_TABLE,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
    }

    fun getTeamsByDriver(driverName: String): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.TEAM_DRIVER1} = ? OR ${DatabaseHelper.TEAM_DRIVER2} = ?"
        val selectionArgs = arrayOf(driverName, driverName)
        return db.query(
            DatabaseHelper.TEAMS_TABLE,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
    }
    fun incrementTeamPoints(teamId: Int, pointsToAdd: Int) {
        val db = dbHelper.writableDatabase
        db.execSQL("UPDATE ${DatabaseHelper.TEAMS_TABLE} SET ${DatabaseHelper.TEAM_POINTS} = ${DatabaseHelper.TEAM_POINTS} + $pointsToAdd WHERE ${DatabaseHelper.TEAM_ID} = $teamId")
        db.close()
    }
    fun addPolesToTeam(teamId: Int, polesToAdd: Int) {
        val db = dbHelper.writableDatabase
        db.execSQL("UPDATE ${DatabaseHelper.TEAMS_TABLE} SET ${DatabaseHelper.POLES_ALL} = ${DatabaseHelper.POLES_ALL} + $polesToAdd WHERE ${DatabaseHelper.TEAM_ID} = $teamId")
        db.close()
    }

    fun updateHighestFinish(teamId: Int, newHighestFinish: Int) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.HIGHEST_FINISH_ALL, newHighestFinish)
        }
        val selection = "${DatabaseHelper.TEAM_ID} = ?"
        val selectionArgs = arrayOf(teamId.toString())
        db.update(DatabaseHelper.TEAMS_TABLE, contentValues, selection, selectionArgs)
        db.close()
    }

    fun addFastestLapsToTeam(teamId: Int, fastestLapsToAdd: Int) {
        val db = dbHelper.writableDatabase
        db.execSQL("UPDATE ${DatabaseHelper.TEAMS_TABLE} SET ${DatabaseHelper.FAST_LAP_ALL} = ${DatabaseHelper.FAST_LAP_ALL} + $fastestLapsToAdd WHERE ${DatabaseHelper.TEAM_ID} = $teamId")
        db.close()
    }

    fun addChampionshipToTeam(teamId: Int, championshipsToAdd: Int) {
        val db = dbHelper.writableDatabase
        db.execSQL("UPDATE ${DatabaseHelper.TEAMS_TABLE} SET ${DatabaseHelper.CHAMPIONSHIP} = ${DatabaseHelper.CHAMPIONSHIP} + $championshipsToAdd WHERE ${DatabaseHelper.TEAM_ID} = $teamId")
        db.close()
    }



}