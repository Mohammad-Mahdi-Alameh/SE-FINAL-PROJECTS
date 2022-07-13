package com.example.watchout_frontend_kotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.btn)
        val logIn = findViewById<TextView>(R.id.logIn)
        val signUp = findViewById<TextView>(R.id.signUp)
        val logInLayout = findViewById<LinearLayout>(R.id.logInLayout)
        val signUpLayout = findViewById<LinearLayout>(R.id.signUpLayout)
        btn.setOnClickListener {
            logIn()
        }
        signUp.setOnClickListener {
            signUp.background = resources.getDrawable(R.drawable.toggled_button, null)
            signUp.setTextColor(resources.getColor(R.color.textColor, null))
            signUpLayout.visibility = View.VISIBLE
            logIn.background = null
            logInLayout.visibility = View.GONE
            logIn.setTextColor(resources.getColor(R.color.pinkColor, null))
            btn.text = "Sign Up"
            btn.setOnClickListener {
                signUp()
            }
        }
        logIn.setOnClickListener {
            signUp.background = null
            signUp.setTextColor(resources.getColor(R.color.pinkColor, null))
            signUpLayout.visibility = View.GONE
            logIn.background = resources.getDrawable(R.drawable.toggled_button, null)
            logInLayout.visibility = View.VISIBLE
            logIn.setTextColor(resources.getColor(R.color.textColor, null))
            btn.text = "Log In"
            btn.setOnClickListener {
                logIn()
            }

        }

    }

    private fun logIn() {
        val username = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Please fill the missing fields !",
                Toast.LENGTH_SHORT
            )
                .show()

        }
        else {
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }
    }


    private fun signUp() {
        val firstname = findViewById<EditText>(R.id.first_name).text.toString()
        val lastname = findViewById<EditText>(R.id.last_name).text.toString()
        val username = findViewById<EditText>(R.id.new_username).text.toString()
        val password = findViewById<EditText>(R.id.new_password).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.confirm_password).text.toString()
        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty() ||
            confirmPassword.isEmpty() ) {
            Toast.makeText(applicationContext, "Please fill the missing fields !", Toast.LENGTH_SHORT)
                .show()
        }
        else{
            if( password != confirmPassword ){
                Toast.makeText(applicationContext, "Entered passwords don't match !", Toast.LENGTH_SHORT)
                    .show()
            }

            }


        }
    }

