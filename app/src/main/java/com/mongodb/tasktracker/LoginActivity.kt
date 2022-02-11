package com.mongodb.tasktracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import io.realm.mongodb.Credentials
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import kotlinx.android.synthetic.main.login.*
import org.bson.Document

/*
* LoginActivity: launched whenever a user isn't already logged in. Allows a user to enter email
* and password credentials to log in to an existing account or create a new account.
*/
class LoginActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        username = findViewById(R.id.loginUsername)
        password = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginBtn)

        loginButton.setOnClickListener { login() }

        tvForgot.setOnClickListener {
            val intent = Intent(this, ResetAcitivity::class.java)
            startActivity(intent)
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true)
    }

    private fun onLoginSuccess() {
        // successful login ends this activity, bringing the user back to the project activity
        //finish()
        val intent =Intent(this, ProjectActivity::class.java);
        startActivity(intent);
    }

    private fun onLoginFailed(errorMsg: String) {
        Log.e(TAG(), errorMsg)
        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_LONG).show()
    }

    private fun validateCredentials(): Boolean = when {
        // zero-length usernames and passwords are not valid (or secure), so prevent users from creating accounts with those client-side.
        username.text.toString().isEmpty() -> false
        password.text.toString().isEmpty() -> false
        else -> true
    }

    // handle user authentication (login) and account creation
    private fun login() {
        if (!validateCredentials()) {
            onLoginFailed("Invalid username or password")
            return
        }

        // while this operation completes, disable the buttons to login
        loginButton.isEnabled = false

        val username = this.username.text.toString()
        val password = this.password.text.toString()
        val creds = Credentials.emailPassword(username, password)

        taskApp.loginAsync(creds) {

            if (!it.isSuccess) {
                onLoginFailed(it.error.message ?: "An error occurred.")

            } else {

                onLoginSuccess()
            }
        }
    }
}
