package com.example.fitshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.realm.mongodb.Credentials
import kotlinx.android.synthetic.main.activity_login.*

/*
* LoginActivity: launched whenever a user isn't already logged in. Allows a user to enter email
* and password credentials to log in to an existing account or create a new account.
*/
class LoginActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var partition: String

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        username = findViewById(R.id.loginUsername)
        password = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginBtn)

        loginButton.setOnClickListener { login() }


        tvReset.setOnClickListener {
            val intent = Intent(this, ResetActivity::class.java)
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
        finish()
        val intent =Intent(this, MainActivity::class.java);
        startActivity(intent);

      //  val intent =Intent(this, MapsActivity::class.java);
      //  startActivity(intent);

    }

    private fun onLoginFailed(errorMsg: String) {
        Log.e("Login", errorMsg)
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

        fitApp.loginAsync(creds) {
            if (!it.isSuccess) {
                Log.v("Login", "Failed")
                onLoginFailed(it.error.message ?: "An error occurred.")
                loginButton.isEnabled = true

            } else {
                onLoginSuccess()
            }
        }
    }
}
