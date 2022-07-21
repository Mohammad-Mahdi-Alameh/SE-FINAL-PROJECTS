package com.example.watchout_frontend_kotlin.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.api.RestApiService
import com.example.watchout_frontend_kotlin.models.GetNearInfrasInfo
import com.example.watchout_frontend_kotlin.others.Constants.REQUEST_LOCATION
import com.example.watchout_frontend_kotlin.services.TrackingService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        setSupportActionBar(findViewById(R.id.toolbar))
        val navController = findNavController(R.id.fragment_container_view_tag)
        bottomNav.setupWithNavController(navController)
        //infras:Infrastructural problems
        trackCurrentLocation(this)
        getNearInfras("29.46786", "-98.56586")
    }

    private fun trackCurrentLocation(context: Context?) {

        //Check all location permission granted or not
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocPermissions()
        } else {
            startTrackingService(this)
            //Criteria class indicating the application criteria for selecting a location provider

        }
    }

    private fun getNearInfras(latitude: String, longitude: String) {
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

    private fun showResult(data: String) {
        Log.i("result", data)
    }

    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), //permission in the manifest
            REQUEST_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //check if the request code matches the REQUEST_LOCATION
        if (requestCode == REQUEST_LOCATION) {
            //check if grantResults contains PERMISSION_GRANTED.If it does, call getCurrentLocation()
            if (grantResults.size == 1 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                trackCurrentLocation(this)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Location permission was denied",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515
        dist *= 1.609344   //transform dist from miles to km
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
    private fun startTrackingService(context: Context) {
        val startServiceIntent = Intent(context, TrackingService::class.java)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            context.startForegroundService(startServiceIntent)
        } else {
            // Pre-O behavior.
            context.startService(startServiceIntent)
        }

    }

}
