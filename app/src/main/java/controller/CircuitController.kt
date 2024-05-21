package controller
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.DatabaseHelper
import java.security.MessageDigest


class CircuitController(context: Context) {
    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    fun addCircuit(length: Float, laps: Int, first: Int, distance: Float, name: String, record: String) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.CIRCUIT_LENGTH, length)
            put(DatabaseHelper.CIRCUIT_LAPS, laps)
            put(DatabaseHelper.CIRCUIT_FIRST, first)
            put(DatabaseHelper.CIRCUIT_DISTANCE, distance)
            put(DatabaseHelper.CIRCUIT_NAME, name)
            put(DatabaseHelper.CIRCUIT_RECORD, record)
        }
        db.insert(DatabaseHelper.CIRCUIT_TABLE, null, contentValues)
        db.close()
    }
    fun getAllCircuits(): Cursor? {
        val db = dbHelper.readableDatabase
        return db.query(DatabaseHelper.CIRCUIT_TABLE, null, null, null, null, null, null)
    }
    fun getCircuitByName(name: String): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.CIRCUIT_NAME} = ?"
        val selectionArgs = arrayOf(name)
        return db.query(DatabaseHelper.CIRCUIT_TABLE, null, selection, selectionArgs, null, null, null)
    }
    fun updateCircuitRecord(circuitId: Int, newRecord: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.CIRCUIT_RECORD, newRecord)
        }
        val selection = "${DatabaseHelper.CIRCUIT_ID} = ?"
        val selectionArgs = arrayOf(circuitId.toString())
        db.update(DatabaseHelper.CIRCUIT_TABLE, values, selection, selectionArgs)
        db.close()
    }
    fun deleteCircuitById(circuitId: Int) {
        val db = dbHelper.writableDatabase
        val selection = "${DatabaseHelper.CIRCUIT_ID} = ?"
        val selectionArgs = arrayOf(circuitId.toString())
        db.delete(DatabaseHelper.CIRCUIT_TABLE, selection, selectionArgs)
        db.close()
    }
    fun updateCircuit(circuitId: Int, newLength: Float, newLaps: Int, newFirst: Int, newDistance: Float, newName: String, newRecord: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.CIRCUIT_LENGTH, newLength)
            put(DatabaseHelper.CIRCUIT_LAPS, newLaps)
            put(DatabaseHelper.CIRCUIT_FIRST, newFirst)
            put(DatabaseHelper.CIRCUIT_DISTANCE, newDistance)
            put(DatabaseHelper.CIRCUIT_NAME, newName)
            put(DatabaseHelper.CIRCUIT_RECORD, newRecord)
        }
        val selection = "${DatabaseHelper.CIRCUIT_ID} = ?"
        val selectionArgs = arrayOf(circuitId.toString())
        db.update(DatabaseHelper.CIRCUIT_TABLE, values, selection, selectionArgs)
        db.close()
    }
    fun getCircuitIDByName(name: String): Int? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.CIRCUIT_NAME} = ?"
        val selectionArgs = arrayOf(name)
        val cursor = db.query(DatabaseHelper.CIRCUIT_TABLE, arrayOf(DatabaseHelper.CIRCUIT_ID), selection, selectionArgs, null, null, null)
        var circuitId: Int? = null
        cursor.use {
            if (it.moveToFirst()) {
                circuitId = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.CIRCUIT_ID))
            }
        }
        return circuitId
    }

}