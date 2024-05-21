package controller

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.DatabaseHelper

class RaceController(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun addRace(round: Int, date: String, name: String) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.RACE_ROUND, round)
            put(DatabaseHelper.RACE_DATE, date)
            put(DatabaseHelper.RACE_NAME, name)
        }
        db.insert(DatabaseHelper.RACES_TABLE, null, contentValues)
        db.close()
    }
    fun getAllRaces(): Cursor? {
        val db = dbHelper.readableDatabase
        return db.query(DatabaseHelper.RACES_TABLE, null, null, null, null, null, null)
    }
    fun getRaceById(raceId: Int): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.RACE_ID} = ?"
        val selectionArgs = arrayOf(raceId.toString())
        return db.query(DatabaseHelper.RACES_TABLE, null, selection, selectionArgs, null, null, null)
    }
    fun updateRaceRound(raceId: Int, newRound: Int) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.RACE_ROUND, newRound)
        }
        val selection = "${DatabaseHelper.RACE_ID} = ?"
        val selectionArgs = arrayOf(raceId.toString())
        db.update(DatabaseHelper.RACES_TABLE, values, selection, selectionArgs)
        db.close()
    }
    fun getRaceByRound(round: Int): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.RACE_ROUND} = ?"
        val selectionArgs = arrayOf(round.toString())
        return db.query(DatabaseHelper.RACES_TABLE, null, selection, selectionArgs, null, null, null)
    }
    fun getRaceByDate(date: String): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.RACE_DATE} = ?"
        val selectionArgs = arrayOf(date)
        return db.query(DatabaseHelper.RACES_TABLE, null, selection, selectionArgs, null, null, null)
    }
    fun getRaceByCircuitName(circuitName: String): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.RACE_NAME} = ?"
        val selectionArgs = arrayOf(circuitName)
        return db.query(DatabaseHelper.RACES_TABLE, null, selection, selectionArgs, null, null, null)
    }
    fun deleteRaceById(raceId: Int) {
        val db = dbHelper.writableDatabase
        val selection = "${DatabaseHelper.RACE_ID} = ?"
        val selectionArgs = arrayOf(raceId.toString())
        db.delete(DatabaseHelper.RACES_TABLE, selection, selectionArgs)
        db.close()
    }

    fun updateRaceDate(raceId: Int, newDate: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.RACE_DATE, newDate)
        }
        val selection = "${DatabaseHelper.RACE_ID} = ?"
        val selectionArgs = arrayOf(raceId.toString())
        db.update(DatabaseHelper.RACES_TABLE, values, selection, selectionArgs)
        db.close()
    }
    fun updateRace(raceId: Int, newRound: Int, newDate: String, newName: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.RACE_ROUND, newRound)
            put(DatabaseHelper.RACE_DATE, newDate)
            put(DatabaseHelper.RACE_NAME, newName)
        }
        val selection = "${DatabaseHelper.RACE_ID} = ?"
        val selectionArgs = arrayOf(raceId.toString())
        db.update(DatabaseHelper.RACES_TABLE, values, selection, selectionArgs)
        db.close()
    }
    fun getRaceIDByName(raceName: String): Int? {
        val db = dbHelper.readableDatabase
        val columns = arrayOf(DatabaseHelper.RACE_ID)
        val selection = "${DatabaseHelper.RACE_NAME} = ?"
        val selectionArgs = arrayOf(raceName)
        var raceId: Int? = null

        db.query(
            DatabaseHelper.RACES_TABLE,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                raceId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.RACE_ID))
            }
        }

        db.close()
        return raceId
    }


}