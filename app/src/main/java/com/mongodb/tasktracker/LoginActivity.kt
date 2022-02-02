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
    private lateinit var createUserButton: Button

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        username = findViewById(R.id.txtUsername)
        password = findViewById(R.id.txtPassword)
        loginButton = findViewById(R.id.btnLogin)

        loginButton.setOnClickListener { login(false) }
        //createUserButton.setOnClickListener { login(true) }

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
    private fun login(createUser: Boolean) {
        if (!validateCredentials()) {
            onLoginFailed("Invalid username or password")
            return
        }

        // while this operation completes, disable the buttons to login or create a new account
        createUserButton.isEnabled = false
        loginButton.isEnabled = false

        val username = this.username.text.toString()
        val password = this.password.text.toString()


        if (createUser) {
            // register a user using the Realm App we created in the TaskTracker class
            taskApp.emailPassword.registerUserAsync(username, password) {
                // re-enable the buttons after user registration returns a result
                createUserButton.isEnabled = true
                loginButton.isEnabled = true
                if (!it.isSuccess) {
                    onLoginFailed("Could not register user.")
                    Log.e(TAG(), "Error: ${it.error}")
                } else {
                    Log.i(TAG(), "Successfully registered user.")
                    // when the account has been created successfully, log in to the account
                    login(false)
                }
            }
        } else {
            val creds = Credentials.emailPassword(username, password)
            taskApp.loginAsync(creds) {
                // re-enable the buttons after user login returns a result
                loginButton.isEnabled = true
                createUserButton.isEnabled = true
                if (!it.isSuccess) {
                    onLoginFailed(it.error.message ?: "An error occurred.")
                } else {
                    /*
                    val user = taskApp.currentUser()
                    val mongoClient : MongoClient =
                        user?.getMongoClient("atlas-custom-user-data")!! // service for MongoDB Atlas cluster containing custom user data
                    val mongoDatabase : MongoDatabase =
                        mongoClient.getDatabase("UserData")!!
                    val mongoCollection : MongoCollection<Document> =
                        mongoDatabase.getCollection("CustomUserData")!!
                    mongoCollection.insertOne(Document("_id", user.id).append("phoneNumber", "911").append("_partition", "partition"))
                        .getAsync { result ->
                            if (result.isSuccess) {
                                Log.v(
                                    "EXAMPLE",
                                    "Inserted custom user data document. _id of inserted document: ${result.get().insertedId}"
                                )
                            } else {
                                Log.e(
                                    "EXAMPLE",
                                    "Unable to insert custom user data. Error: ${result.error}"
                                )
                            }
                        }
                        */
                    onLoginSuccess()
                }
            }
        }
    }
}
