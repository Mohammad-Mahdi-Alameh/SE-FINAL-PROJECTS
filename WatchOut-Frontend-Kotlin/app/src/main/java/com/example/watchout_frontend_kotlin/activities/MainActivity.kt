package com.example.watchout_frontend_kotlin.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.watchout_frontend_kotlin.R

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val register = findViewById<TextView>(R.id.navigateToRegister)
        val signin_btn = findViewById<Button>(R.id.signin_btn)
        val username = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }
        signin_btn.setOnClickListener {
            //login function isn't called for testing reasons only
            // logIn(username,password,this)
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



}

