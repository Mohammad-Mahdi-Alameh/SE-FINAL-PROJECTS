package com.example.watchout_frontend_kotlin.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.api.RestApiService
import com.example.watchout_frontend_kotlin.models.LoginInfo
import com.example.watchout_frontend_kotlin.others.encryptAndSavePassword

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val register = findViewById<TextView>(R.id.navigateToRegister)
        val signin_btn = findViewById<Button>(R.id.signin_btn)
        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }
        signin_btn.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
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

        } else {
            val apiService = RestApiService()
            val loginInfo = LoginInfo(
                username = username,
                password = password
            )

            apiService.login(loginInfo) {
                if (it?.token != null) {
                    Log.i("token", it.token)
                    val sharedPreferences: SharedPreferences =
                        this.getPreferences(Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("token", it.token)
                    editor.putString("user_id", it.id.toString())
                    editor.putString("firstname", it.firstname)
                    editor.putString("lastname", it.lastname)
                    editor.putString("phonenumber", it.phonenumber.toString())
                    editor.putString("balance", it.balance.toString())
                    editor.putString("picture", it.picture)
                    editor.putString("username", username)
                    encryptAndSavePassword(this,password) // password will be encrypted and saved in shared preferences
                    editor.putString("token", it.token)
                    editor.apply()
                    editor.commit()
                } else {
                    Log.i("Login Error", "Wrong username or password !")
                }
            }
        }
    }

}

