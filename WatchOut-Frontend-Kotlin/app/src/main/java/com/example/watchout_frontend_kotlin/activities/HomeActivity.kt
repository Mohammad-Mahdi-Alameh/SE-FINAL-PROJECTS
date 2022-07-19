package com.example.watchout_frontend_kotlin.activities

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.api.RestApiService
import com.example.watchout_frontend_kotlin.models.GetNearInfrasInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_LOCATION = 1 //request code to identify specific permission request
        private const val TAG = "HomeActivity" // for debugging
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        setSupportActionBar(findViewById(R.id.toolbar))
        val navController = findNavController(R.id.fragment_container_view_tag)
        bottomNav.setupWithNavController(navController)
        //infras:Infrastructural problems
        getNearInfras("29.46786","-98.56586")
        }


    private fun getNearInfras(latitude : String , longitude : String) {
        val apiService = RestApiService()
        val getNearInfrasInfo = GetNearInfrasInfo(
            base_latitude = latitude,
            base_longitude = longitude
        )
        apiService.getNearInfras(getNearInfrasInfo) {
            if (it?.size != null) {
                Log.i("Near Infras", Gson().toJson(it))
                showResult(Gson().toJson(it))
            } else {
                Log.i("Error", "Error")

            }
    }

}
    private fun showResult(data:String) {
        Log.i("result",data)
    }

    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), //permission in the manifest
            REQUEST_LOCATION
        )
    }

}
