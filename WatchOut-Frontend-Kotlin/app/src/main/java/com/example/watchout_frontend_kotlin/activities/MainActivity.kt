package com.example.watchout_frontend_kotlin.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.api.ApiMainHeadersProvider
import com.example.watchout_frontend_kotlin.api.RestApiService
import com.example.watchout_frontend_kotlin.models.LoginInfo
import com.example.watchout_frontend_kotlin.utilities.Utilities

class MainActivity : AppCompatActivity() {
    private lateinit var  username : EditText
    private lateinit var  password : EditText
    private var  public = Utilities()
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkToken()
        setContentView(R.layout.activity_main)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        val register = findViewById<TextView>(R.id.navigateToRegister)
        val signinBtn = findViewById<Button>(R.id.signin_btn)
        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }
        signinBtn.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
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
                val authenticatedHeaders =
                  ApiMainHeadersProvider.getPublicHeaders()

                apiService.login(authenticatedHeaders,loginInfo) {
                    if (it?.token != null) {
                        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
                        val editor: SharedPreferences.Editor = sharedPref.edit()
                        editor.putString("token", it.token)
                        editor.putString("user_id", it?.id.toString())
                        editor.putString("firstname", it?.firstname)
                        editor.putString("lastname", it?.lastname)
                        editor.putString("phonenumber", it?.phonenumber.toString())
                        editor.putString("balance", it?.balance.toString())
                        editor.putString("total_reports", it?.totalReports.toString())
                        editor.putString("total_false_infras", it?.totalFalseInfras.toString())
                        editor.putString("picture", it?.picture)
                        editor.putString("username", username)
                        public.encryptAndSavePassword(
                            this,
                            password
                        ) // password will be encrypted and saved in shared preferences
                        editor.apply()
                        editor.commit()
                        startActivity(Intent(this, MapsActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Wrong username or password !",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }
    }

    private fun checkToken() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val token = sharedPref.getString("token", "")
        if (token?.length!! > 0) {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}

