package com.example.fanf1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import controller.UserController

class CreateAccountActivity : ComponentActivity() {
    private val userController = UserController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val buttonCreateAccount = findViewById<Button>(R.id.buttonCreateAccount)

        buttonCreateAccount.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (validateInput(username, password, confirmPassword)) {
                if (userController.isUsernameTaken(username)) {
                    showToast("Username is already taken")
                } else {
                    userController.addUser(username, password, 0)
                    showToast("Account created successfully")
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                }
            }
        }
    }

    private fun validateInput(username: String, password: String, confirmPassword: String): Boolean {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Please fill in all fields")
            return false
        }
        if (password != confirmPassword) {
            showToast("Passwords do not match")
            return false
        }
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$".toRegex()
        if (!password.matches(passwordPattern)) {
            showToast("Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one digit, and one special character")
            return false
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
