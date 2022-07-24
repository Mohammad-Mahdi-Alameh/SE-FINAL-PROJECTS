package com.example.watchout_frontend_kotlin.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.api.RestApiService
import com.example.watchout_frontend_kotlin.models.SignupInfo
import com.example.watchout_frontend_kotlin.others.logIn

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val back_arrow = findViewById<ImageView>(R.id.back_arrow)
        val login = findViewById<TextView>(R.id.navigateToLogin)
        val register_btn = findViewById<Button>(R.id.register_btn)
        back_arrow.setOnClickListener {
            navigateToLogin()
        }
        login.setOnClickListener {
            navigateToLogin()
        }
        register_btn.setOnClickListener {
            register()
        }

    }

    private fun navigateToLogin() {
        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
        finish()
    }

    private fun register() {
        val firstname = findViewById<EditText>(R.id.first_name).text.toString()
        val lastname = findViewById<EditText>(R.id.last_name).text.toString()
        val phonenumber = findViewById<EditText>(R.id.phone_number).text.toString()
        val username = findViewById<EditText>(R.id.new_username).text.toString()
        val password = findViewById<EditText>(R.id.new_password).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.confirm_password).text.toString()
        if (firstname.isEmpty() || lastname.isEmpty() || phonenumber.isEmpty() ||
            username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
        ) {
            Toast.makeText(
                applicationContext,
                "Please fill the missing fields !",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            if (!phonenumber.all { char -> char.isDigit() }) {
                Toast.makeText(
                    applicationContext,
                    "Phone number can contain only numbers !",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return
            }
            if (password != confirmPassword) {
                Toast.makeText(
                    applicationContext,
                    "Entered passwords don't match !",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return
            }
            val apiService = RestApiService()
            val signupInfo = SignupInfo(
                firstname = firstname,
                lastname = lastname,
                phonenumber = phonenumber.toInt(),
                username = username,
                password = password,
                c_password = confirmPassword
            )

            apiService.signUp(signupInfo) {
                if (it?.message == "User successfully registered") {
                    Log.i("message", "User added successfully")
                    logIn(username,password,this)
                } else {
                    //Error on laravel validator because password < 6
                    if (password.length < 6) {
                        Log.i(
                            "Signup Error",
                            "Password too short ! It should be minimum six characters"
                        )
                    }
                    //Error on laravel validator because username exists already
                    else
                        Log.i("Signup Error", "Username exists please choose another one !")
                }
            }
        }
    }


}