package com.example.fitshare


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import io.realm.mongodb.Credentials
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_login.*

import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import org.bson.Document

class RegisterActivity : AppCompatActivity() {
    private lateinit var Reg_Email: EditText
    private lateinit var Reg_Password: EditText
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Reg_Email = findViewById(R.id.txtReg_Email)
        Reg_Password = findViewById(R.id.txtReg_Password)


        /*
        ivLeft.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }*/

        btnRegister.setOnClickListener {
            Register()
        }
    }

    /*
    override fun onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true)
    }
    */

    private fun onLoginFailed(errorMsg: String) {
        Log.e("Login", errorMsg)
        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_LONG).show()
    }

    private fun validateCredentials(): Boolean = when {
        // zero-length usernames and passwords are not valid (or secure), so prevent users from creating accounts with those client-side.
        Reg_Email.text.toString().isEmpty() -> false
        Reg_Password.text.toString().isEmpty() -> false
        else -> true
    }

    private fun Register() {
        val Reg_Email = this.Reg_Email.text.toString()
        val Reg_Password = this.Reg_Password.text.toString()

        // register a user using the Realm App we created in the TaskTracker class
        fitApp.emailPassword.registerUserAsync(Reg_Email, Reg_Password) {
            // re-enable the buttons after user registration returns a result
            //createUserButton.isEnabled = true

            if (!it.isSuccess) {
                onLoginFailed("Could not register user.")
                Log.e("Register", "Error: ${it.error}")
                btnRegister.isEnabled = true
            } else {
                Log.i("Register", "Successfully registered user.")

                // when the account has been created successfully, log in to the account
                val creds = Credentials.emailPassword(Reg_Email, Reg_Password)
                fitApp.loginAsync(creds) {
                    // re-enable the buttons after user login returns a result

                    //createUserButton.isEnabled = true
                    if (!it.isSuccess) {
                        onLoginFailed(it.error.message ?: "An error occurred.")
                    } else {
                        val user = fitApp.currentUser()
                        //val customUserData : Document? = user?.customData
                        val mongoClient : MongoClient =
                            user?.getMongoClient("atlas-custom-user-data")!! // service for MongoDB Atlas cluster containing custom user data
                        val mongoDatabase : MongoDatabase =
                            mongoClient.getDatabase("UserData")!!
                        val mongoCollection : MongoCollection<Document> =
                            mongoDatabase.getCollection("CustomUserData")!!

                        mongoCollection.insertOne(Document("_id", user.id).append("FName", FName)
                            .append("LName", LName).append("Reg_Username", Reg_Username).append("Reg_Email", Reg_Email)
                            .append("Reg_Password", Reg_Password).append("Phone", Phone).append("_partition", "partition"))

                        mongoCollection.insertOne(Document("_id", user.id).append("FName", FName).append("LName", LName).append("Reg_Username", Reg_Username).append("Reg_Email", Reg_Email).append("Phone", Phone).append("_partition", "partition"))

                            .getAsync { result ->
                                if (result.isSuccess) {
                                    Log.v(
                                        "CustomData",
                                        "Inserted custom user data document. _id of inserted document: ${result.get().insertedId}"
                                    )
                                } else {
                                    Log.e(
                                        "CustomData",
                                        "Unable to insert custom user data. Error: ${result.error}"
                                    )
                                }
                            }
                        onRegisterSuccess()
                    }

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

                }
            }
        }
    }
