package controller

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.DatabaseHelper
import java.security.MessageDigest



class UserController(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun addUser(username: String, password: String, isAdmin: Int) {
        // Check if the username already exists
        if (isUsernameTaken(username)) {
            // Username is already taken, handle this case (e.g., show an error message)
            println("Username '$username' is already taken.")
            return
        }

        val hashedPassword = hashPassword(password) // Hash the password
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.USER_USERNAME, username)
            put(DatabaseHelper.USER_PASSWORD, hashedPassword)
            put(DatabaseHelper.USER_ISADMIN, isAdmin)
        }
        db.insert(DatabaseHelper.USERS_TABLE, null, contentValues)
        db.close()
    }
    fun isUsernameTaken(username: String): Boolean {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.USER_USERNAME} = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(
            DatabaseHelper.USERS_TABLE,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val isTaken = cursor.count > 0 // If count is greater than 0, username is taken
        cursor.close()
        db.close()
        return isTaken
    }
    fun getAllUsers(): Cursor? {
        val db = dbHelper.readableDatabase
        return db.query(DatabaseHelper.USERS_TABLE, null, null, null, null, null, null)
    }

    fun getUserByUsername(username: String): Cursor? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.USER_USERNAME} = ?"
        val selectionArgs = arrayOf(username)
        return db.query(DatabaseHelper.USERS_TABLE, null, selection, selectionArgs, null, null, null)
    }

    fun updateUserPassword(username: String, newPassword: String) {
        val hashedPassword = hashPassword(newPassword) // Hash the new password
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.USER_PASSWORD, hashedPassword)
        }
        val selection = "${DatabaseHelper.USER_USERNAME} = ?"
        val selectionArgs = arrayOf(username)
        db.update(DatabaseHelper.USERS_TABLE, values, selection, selectionArgs)
        db.close()
    }

    fun deleteUserByUsername(username: String) {
        val db = dbHelper.writableDatabase
        val selection = "${DatabaseHelper.USER_USERNAME} = ?"
        val selectionArgs = arrayOf(username)
        db.delete(DatabaseHelper.USERS_TABLE, selection, selectionArgs)
        db.close()
    }
    fun checkLoginCredentials(username: String, password: String): Boolean {
        val hashedPassword = hashPassword(password) // Hash the provided password
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.USER_USERNAME} = ? AND ${DatabaseHelper.USER_PASSWORD} = ?"
        val selectionArgs = arrayOf(username, hashedPassword)
        val cursor = db.query(
            DatabaseHelper.USERS_TABLE,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val isValid = cursor.count > 0 // Check if any matching user is found
        cursor.close()
        db.close()
        return isValid
    }
    fun isAdmin(username: String): Boolean {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.USER_USERNAME} = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(
            DatabaseHelper.USERS_TABLE,
            arrayOf(DatabaseHelper.USER_ISADMIN), // Only select the isAdmin column
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        var isAdmin = false
        if (cursor.moveToFirst()) {
            val isAdminValue = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_ISADMIN))
            isAdmin = isAdminValue == 1
        }
        cursor.close()
        db.close()
        return isAdmin
    }


}
