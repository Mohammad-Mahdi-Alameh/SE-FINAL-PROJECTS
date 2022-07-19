package com.example.watchout_frontend_kotlin.activities

import android.Manifest
import android.content.pm.PackageManager
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
import com.example.watchout_frontend_kotlin.models.LocationInfo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class HomeActivity : AppCompatActivity() {
    private lateinit var fusedLocClient: FusedLocationProviderClient
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbReference: DatabaseReference = database.getReference("test")
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



        setupLocClient()
        trackCurrentLocation()
        dbReference = Firebase.database.reference
        dbReference.addValueEventListener(locListener)
        getNearInfras("29.46786","-98.56586")

        }


    private fun getNearInfras(latitude : String , longitude : String) {
        val apiService = RestApiService()
        val getNearInfrasInfo = GetNearInfrasInfo(
            base_latitude = latitude,
            base_longitude = longitude)
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



    private fun setupLocClient() {
        fusedLocClient =
            LocationServices.getFusedLocationProviderClient(this)
    }
    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), //permission in the manifest
            REQUEST_LOCATION
        )
    }


    private fun trackCurrentLocation() {
        // Check if the ACCESS_FINE_LOCATION permission was granted before requesting a location
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {

            // call requestLocPermissions() if permission isn't granted
            requestLocPermissions()
        } else {

            fusedLocClient.lastLocation.addOnCompleteListener {
                // lastLocation is a task running in the background
                val location = it.result //obtain location which contains longitude , latitude , speed , altitude and much more ......
                //reference to the database
                //val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                val ref: DatabaseReference = database.getReference("test")
                if (location != null) {


                    //Save the location data to the database
                    ref.setValue(location)
                } else {
                    // if location is null , log an error message
                    Log.e("error", "No location found")
                }



            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //check if the request code matches the REQUEST_LOCATION
        if (requestCode == HomeActivity.REQUEST_LOCATION)
        {
            //check if grantResults contains PERMISSION_GRANTED.If it does, call getCurrentLocation()
            if (grantResults.size == 1 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                trackCurrentLocation()
            } else {
                Toast.makeText(applicationContext, "Location permission was denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val locListener = object : ValueEventListener {
        //     @SuppressLint("LongLogTag")
        override fun onDataChange(snapshot: DataSnapshot) {
            if(snapshot.exists()){
                //get the exact longitude and latitude and speed from the database "test"
                val location = snapshot.child("test").getValue(LocationInfo::class.java)
                val locationLat = location?.latitude
                val locationLong = location?.longitude
                val speed = location?.speed
                //trigger reading of location from database using the button
                Log.i("lat", locationLat.toString())
                Log.i("long", locationLong.toString())
                Log.i("speed", speed.toString())
//                getCurrentLocation()
                    }
                    else {
                        // if location is null , log an error message
                        Log.e(TAG, "user location cannot be found")
                    }
                }



        // show this toast if there is an error while reading from the database
        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(applicationContext, "Could not read from database", Toast.LENGTH_LONG).show()
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
        return  dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

}
