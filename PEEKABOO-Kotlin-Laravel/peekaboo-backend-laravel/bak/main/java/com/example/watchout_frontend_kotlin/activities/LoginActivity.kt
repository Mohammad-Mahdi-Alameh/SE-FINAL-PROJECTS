package com.example.watchout_frontend_kotlin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.watchout_frontend_kotlin.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val register= findViewById<TextView>(R.id.navigateToRegister)
        register.setOnClickListener{
            val intent = Intent (this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}