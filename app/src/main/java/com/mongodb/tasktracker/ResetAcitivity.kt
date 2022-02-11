package com.mongodb.tasktracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import io.realm.mongodb.Credentials
import kotlinx.android.synthetic.main.activity_reset.*

class ResetAcitivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)
        username = findViewById(R.id.txtUsername)
        password = findViewById(R.id.txtNewPassword)

        resetBtn.setOnClickListener { reset() }
    }

    private fun onLoginSuccess() {
        // successful login ends this activity, bringing the user back to the project activity
        //finish()
        val intent = Intent(this, ProjectActivity::class.java);
        startActivity(intent);
    }

    private fun reset() {
        val username = this.username.text.toString()
        val password = this.password.text.toString()
        val creds = Credentials.emailPassword(username, password)

        taskApp.loginAsync(creds) {
            //createUserButton.isEnabled = true
            if (!it.isSuccess) {
                Log.v(
                    "Login",
                    it.error.toString()
                )

            } else {
                val user = taskApp.currentUser()
                taskApp.emailPassword.callResetPasswordFunctionAsync(username, password, null){ result ->
                    if (result.isSuccess) {
                        Log.v(
                            "Reset",
                            "Success"
                        )
                        onLoginSuccess()
                    } else {
                        Log.e(
                            "Reset",
                            "Unable to reset user password. Error: ${result.error}"
                        )
                    }
                }
            }
        }
    }
}