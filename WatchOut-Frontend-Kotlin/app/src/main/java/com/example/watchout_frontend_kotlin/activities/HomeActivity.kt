package com.example.watchout_frontend_kotlin.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.api.RestApiService
import com.example.watchout_frontend_kotlin.models.GetNearInfrasInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

//import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        setSupportActionBar(findViewById(R.id.toolbar))
        val navController = findNavController(R.id.fragment_container_view_tag)
        bottomNav.setupWithNavController(navController)

        //infras:Infrastructural problems
        val apiService = RestApiService()
        val getNearInfrasInfo = GetNearInfrasInfo(
            base_latitude = "29.46786",
            base_longitude = "-98.56586")

        apiService.getNearInfras(getNearInfrasInfo) {
            if (it?.size != null) {
                Log.i("Near Infras", Gson().toJson(it))

            } else {
                Log.i("Error", "Error")
            }
        }

    }
}

