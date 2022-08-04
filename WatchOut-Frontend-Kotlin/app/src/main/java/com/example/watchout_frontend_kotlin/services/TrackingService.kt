package com.example.watchout_frontend_kotlin.services

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.activities.MapsActivity
import com.example.watchout_frontend_kotlin.utilities.Constants.SERVICE_CHANNEL_ID
import com.example.watchout_frontend_kotlin.utilities.Constants.SERVICE_CHANNEL_NAME
import com.example.watchout_frontend_kotlin.utilities.Constants.SERVICE_NOTIFICATION_ID
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TrackingService : Service() {
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbReference: DatabaseReference = database.getReference("Live-Tracking")

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    @SuppressLint("MissingPermission")
    //although permission is given and everything is perfect and its working fine
    // (compiling, running, building, and tracking)
    // but there is a chunk in the following function staying red
    // which bothers me I alot so added this supressLint
    // to just remove the red color and we can remove it and everything will stay perfect
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



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
//        setupLocClient()
        trackCurrentLocation(this)
        return START_STICKY
    }


    private fun showNotification() {

        //It requires to create notification channel when android version is more than or equal to oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val notificationIntent = Intent(this, MapsActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, SERVICE_CHANNEL_ID)
            .setContentTitle("Location Service")
            .setContentText("PEEKABOO is tracking you !")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.app_peekaboo)
            .setOngoing(true)
            .build()
        startForeground(SERVICE_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        val serviceChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                SERVICE_CHANNEL_ID, SERVICE_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager!!.createNotificationChannel(serviceChannel)

    }
}
