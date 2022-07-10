package com.example.watchout_frontend_kotlin

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            btn.text = "Log In";
            btn.setOnClickListener {
                logIn()
            }

        }

    }

    private fun logIn() {
        val username = username.text.toString()
        val password = password.text.toString()
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Please fill the missing fields !", Toast.LENGTH_SHORT)
                .show()

        }


    }

    private fun signUp() {
        val firstname = first_name.text.toString()
        val lastname = last_name.text.toString()
        val username = new_username.text.toString()
        val password = new_password.text.toString()
        val confirmPassword = confirm_password.text.toString()
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