package com.example.watchout_frontend_kotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signUp.setOnClickListener {
            signUp.background = resources.getDrawable(R.drawable.toggled_button,null)
            signUp.setTextColor(resources.getColor(R.color.textColor,null))
            signUpLayout.visibility = View.VISIBLE
            logIn.background = null
            logInLayout.visibility = View.GONE
            logIn.setTextColor(resources.getColor(R.color.pinkColor,null))
            btn.text = "Sign Up"
        }
        logIn.setOnClickListener {
            signUp.background = null
            signUp.setTextColor(resources.getColor(R.color.pinkColor,null))
            signUpLayout.visibility = View.GONE
            logIn.background = resources.getDrawable(R.drawable.toggled_button,null)
            logInLayout.visibility = View.VISIBLE
            logIn.setTextColor(resources.getColor(R.color.textColor,null))
            btn.text = "Log In";

        }
        btn.setOnClickListener {
            startActivity(Intent(this@MainActivity,HomeActivity::class.java))
        }
    }
}