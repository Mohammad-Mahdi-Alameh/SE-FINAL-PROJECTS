package com.example.watchout_frontend_kotlin.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.watchout_frontend_kotlin.activities.HomeActivity
import com.example.watchout_frontend_kotlin.models.LocationInfo
import com.example.watchout_frontend_kotlin.others.Constants.CHANNEL_ID
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TrackingService : Service() {
//    private lateinit var fusedLocClient: FusedLocationProviderClient
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbReference: DatabaseReference = database.getReference("Live-Tracking")

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

//    private fun setupLocClient() {
//        fusedLocClient =
//            LocationServices.getFusedLocationProviderClient(this)
//    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }


    private fun trackCurrentLocation(context: Context?) {

        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //Criteria class indicating the application criteria for selecting a location provider
            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_FINE
            criteria.isSpeedRequired = true
            criteria.speedAccuracy = Criteria.ACCURACY_HIGH

            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val provider = locationManager.getBestProvider(criteria, true)

            if (provider != null) {
                locationManager.requestLocationUpdates(
                    provider, 1, 0.1f, object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            if (location != null) {
                                dbReference.setValue(location)
                            } else {
                                // if location is null , log an error message
                                Log.e("error", "No location found")
                            }


                        }

                        override fun onProviderDisabled(provider: String) {
                            //Provider disabled
                        }

                        override fun onProviderEnabled(provider: String) {
                            //Provider enabled
                        }

                        override fun onStatusChanged(
                            provider: String?,
                            status: Int,
                            extras: Bundle?
                        ) {

                        }

                    })
            }
        }
    }

    private val locListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                //get the exact longitude and latitude and speed from the database "Live Tracking"
                val location = snapshot.child("Live-Tracking").getValue(LocationInfo::class.java)
                val locationLat = location?.latitude
                val locationLong = location?.longitude
                val speed = location?.speed

                Log.i("lat", locationLat.toString())
                Log.i("long", locationLong.toString())
                Log.i("speed", speed.toString())
            } else {
                // if location is null , log an error message
                Log.e("Error", "user location cannot be found")
            }
        }


        // show this toast if there is an error while reading from the database
        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(applicationContext, "Could not read from database", Toast.LENGTH_LONG)
                .show()
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
//        setupLocClient()
        trackCurrentLocation(this)
        dbReference = Firebase.database.reference
        dbReference.addValueEventListener(locListener)
        return START_STICKY
    }


    private fun showNotification() {

        //It requires to create notification channel when android version is more than or equal to oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val notificationIntent = Intent(this, HomeActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Location Service")
            .setContentText("WatchOut! is running")
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        val serviceChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                CHANNEL_ID, "Location Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager!!.createNotificationChannel(serviceChannel)

    }
}
