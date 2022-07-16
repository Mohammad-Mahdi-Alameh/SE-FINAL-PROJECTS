package com.example.watchout_frontend_kotlin.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.api.RestApiService
import com.example.watchout_frontend_kotlin.models.LoginInfo
import com.example.watchout_frontend_kotlin.models.SignupInfo

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
//        val database = Firebase.database
//        val myRef = database.getReference("message")
//        myRef.setValue("Hello, World!")


//        var i = 0
        btn.setOnClickListener {
            logIn()
//            val myRef = database.getReference("message")
//            myRef.setValue(i++)Sett
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
        else{
          startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            val apiService = RestApiService()
            val loginInfo = LoginInfo(
                username = username,
                password = password)

            apiService.login(loginInfo) {
                if (it?.token != null) {
                    Log.i("token", it.token)
                    val sharedPreferences: SharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("token", it.token)
                    editor.apply()
                    editor.commit()
                } else {
                    Log.i("Login Error", "Wrong username or password !")
                }
            }
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
            val apiService = RestApiService()
            val signupInfo = SignupInfo(
                firstname = firstname,
                lastname = lastname,
                username = username,
                password = password,
                c_password = confirmPassword)

            apiService.signUp(signupInfo ) {
                if (it?.message == "User successfully registered") {
                    Log.i("message", "User added successfully")
                } else {
                    //Error on laravel validator because password < 6
                    if(password.length < 6) {
                        Log.i("Signup Error", "Password too short ! It should be minimum six characters")
                    }
                    //Error on laravel validator because username exists already
                    else
                    Log.i("Signup Error", "Username exists please choose another one !")
                }
            }
        }


            }


        }

