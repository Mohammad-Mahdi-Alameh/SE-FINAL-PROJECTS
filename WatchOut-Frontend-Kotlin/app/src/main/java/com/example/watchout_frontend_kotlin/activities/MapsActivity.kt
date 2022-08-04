package com.example.watchout_frontend_kotlin.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.marginBottom
import androidx.drawerlayout.widget.DrawerLayout
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.api.ApiMainHeadersProvider
import com.example.watchout_frontend_kotlin.api.RestApiService
import com.example.watchout_frontend_kotlin.databinding.ActivityMapsBinding
import com.example.watchout_frontend_kotlin.models.GetNearInfrasInfo
import com.example.watchout_frontend_kotlin.models.LocationInfo
import com.example.watchout_frontend_kotlin.models.ReportInfo
import com.example.watchout_frontend_kotlin.services.TrackingService
import com.example.watchout_frontend_kotlin.utilities.Constants
import com.example.watchout_frontend_kotlin.utilities.Constants.INFRA_CHANNEL_ID
import com.example.watchout_frontend_kotlin.utilities.Constants.INFRA_CHANNEL_NAME
import com.example.watchout_frontend_kotlin.utilities.Constants.INFRA_NOTIFICATION_ID
import com.example.watchout_frontend_kotlin.utilities.Utilities
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONArray
import java.time.LocalDateTime


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnInfoWindowClickListener {
    //Views
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var hamburger: ImageView
    private lateinit var coins: TextView
    private lateinit var report: Button
    private lateinit var latLng: LatLng
    private lateinit var fusedLocClient: FusedLocationProviderClient

    //Utilities
    private var public = Utilities()

    //Firebase
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbReference: DatabaseReference = database.getReference("Live-Tracking")

    //Shared Preferences
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var jwtToken: String
    //Intents
//    private lateinit var notificationIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupLocClient()
        createNotificationChannel(INFRA_CHANNEL_ID, INFRA_CHANNEL_NAME)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPref.edit()
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        coins = findViewById(R.id.coins)
        val balance = sharedPref.getString("balance", "0")
        coins.text = balance
        report = findViewById(R.id.report_btn)
        hamburger = findViewById(R.id.hamburger_btn)
        drawerLayout = findViewById(R.id.drawer_layout)
        hamburger.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        //managing the drawer navigation
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            drawerLayout.closeDrawer(GravityCompat.START)
            when (id) {
                R.id.edit_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                }
                R.id.history -> {
                    startActivity(Intent(this, HistoryActivity::class.java))
                    finish()
                }
                R.id.requests -> {
                    startActivity(Intent(this, RequestsActivity::class.java))
                    finish()
                }
                R.id.logout -> {
                    //linking to the logout api will be done later
                    logout()
                }

                else -> return@OnNavigationItemSelectedListener true
            }
            true
        })


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        trackCurrentLocation(this)
        report.setOnClickListener {
            showBottomSheet(0.0, 0.0)
        }

    }

    //function showing the bottom sheet (dialog) and managing it and adding the needed listeners
    private fun showBottomSheet(latitude: Double, longitude: Double) {
        val bottomSheetDialog = BottomSheetDialog(
            this, R.style.BottomSheetDialogTheme
        )
        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.layout_bottom_sheet,
            findViewById<LinearLayout>(R.id.bottomSheet)
        )
        bottomSheetView.findViewById<View>(R.id.cancel_btn).setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetView.findViewById<View>(R.id.bump).setOnClickListener {
            bottomSheetDialog.dismiss()
            reportFunctionCaller(it, latitude, longitude)
            public.popupAlertDialog(this, R.layout.alert_dialog)
            addCoins()
        }
        bottomSheetView.findViewById<View>(R.id.turn).setOnClickListener {
            bottomSheetDialog.dismiss()
            reportFunctionCaller(it, latitude, longitude)
            public.popupAlertDialog(this, R.layout.alert_dialog)
            addCoins()
        }
        bottomSheetView.findViewById<View>(R.id.hole).setOnClickListener {
            bottomSheetDialog.dismiss()
            reportFunctionCaller(it, latitude, longitude)
            public.popupAlertDialog(this, R.layout.alert_dialog)
            addCoins()
        }
        bottomSheetView.findViewById<View>(R.id.blockage).setOnClickListener {
            bottomSheetDialog.dismiss()
            reportFunctionCaller(it, latitude, longitude)
            public.popupAlertDialog(this, R.layout.alert_dialog)
            addCoins()
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    //logout
    private fun logout() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val jwtToken = sharedPref.getString("token", "")
        val apiService = RestApiService()
        val authenticatedHeaders =
            jwtToken?.let { ApiMainHeadersProvider.getAuthenticatedHeaders(it) }
        if (authenticatedHeaders != null) {
            val authenticatedHeaders =
                ApiMainHeadersProvider.getAuthenticatedHeaders(jwtToken)
            apiService.logout(authenticatedHeaders) {
                if (it?.message == "logout") {
                    editor = sharedPref.edit()
                    editor.clear()
                    editor.commit()
                    stopTrackingService(this)
                    startActivity(
                        Intent(
                            this, MainActivity::
                            class.java
                        )
                    )
                    finish()
                }
            }
        }
    }

    //function to add coins to the user balance
    private fun addCoins() {
        val jwtToken = sharedPref.getString("token", "")
        val userId = sharedPref.getString("user_id", "")?.toInt()
        val apiService = RestApiService()
        val authenticatedHeaders =
            jwtToken?.let { ApiMainHeadersProvider.getAuthenticatedHeaders(it) }
        if (authenticatedHeaders != null) {
            if (userId != null) {
                apiService.addCoins(authenticatedHeaders, userId) {
                    if (it?.message == "added successfully") {
                        var balance = coins.text.toString().toInt()
                        balance += 5
                        coins.text = balance.toString()
                        editor.putString("balance", balance.toString())
                        editor.apply()
                        editor.commit()
                    }
                }
            }
        }
    }

    //putting the markers and setting the listener on the firebase and setting the info window
// settings and its on click and the map onclick
// when the map finishes loading and is ready to be used
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getAllInfras()
        dbReference = Firebase.database.reference
        dbReference.addValueEventListener(locListener)
        googleMap.setOnInfoWindowClickListener(this)
        googleMap.setOnMapClickListener(this)
        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(arg0: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View? {
                val info = LinearLayout(applicationContext)
                info.orientation = LinearLayout.VERTICAL
                val title = TextView(applicationContext)
                title.setTextColor(Color.BLACK)
                title.gravity = Gravity.CENTER
                title.setTypeface(null, Typeface.BOLD)
                title.text = marker.title
                val snippet = TextView(applicationContext)
                snippet.setTextColor(Color.GRAY)
                snippet.marginBottom
                snippet.text = marker.snippet
                val report = TextView(applicationContext)
                report.text = "Press this box to report false alarm"
                report.gravity = Gravity.CENTER
                report.setTextColor(Color.BLACK)
                info.addView(title)
                info.addView(snippet)
                info.addView(report)
                return info
            }
        })

    }

    //function to start the foreground service
    private fun startTrackingService(context: Context) {
        val startServiceIntent = Intent(context, TrackingService::class.java)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            context.startForegroundService(startServiceIntent)
        } else {
            // Pre-O behavior.
            context.startService(startServiceIntent)
        }

    }

    //function to stop the foreground service
    private fun stopTrackingService(context: Context) {
        val stopServiceIntent = Intent(context, TrackingService::class.java)
        context.stopService(stopServiceIntent)
    }

    //function that gives the green light to the startTrackingFunction to start after
// granting location permissions, and if no request them
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

    //function to request location permissions
    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), //permission in the manifest
            Constants.REQUEST_LOCATION
        )
    }

    //function to handle the result of the  location request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //check if the request code matches the REQUEST_LOCATION
        if (requestCode == Constants.REQUEST_LOCATION) {
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

    //this listener is the responsible to handle every updates of the live location,
// sent from our foreground service The "TrackingService"
    private var locListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()) {
                sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                val nearInfras = sharedPref.getString("Near Infras", "")
                //get the exact longitude and latitude and speed from the database "Live Tracking"
                val location = snapshot.child("Live-Tracking").getValue(LocationInfo::class.java)
                val locationLat = location?.latitude
                val locationLong = location?.longitude
                val speed = location?.speed

                Log.i("lat", locationLat.toString())
                Log.i("long", locationLong.toString())
                Log.i("speed", speed.toString())

                if (locationLat != null && locationLong != null && speed != null) {
                    // create a LatLng object from location
                    latLng = LatLng(locationLat, locationLong)
                    //create a marker at the read location and display it on the map
                    mMap.addMarker(
                        MarkerOptions().position(latLng)
                            .icon(bitmapFromVector(applicationContext, R.drawable.ic_tracker))
                            .zIndex(
                                10.0F
                            )
                    )
                    //specify how the map camera is updated
                    val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
                    //update the camera with the CameraUpdate object
                    mMap.moveCamera(update)
                    if (nearInfras != null) {
                    }
                    if (nearInfras != null) {
                        if (nearInfras.isNotEmpty()) {
                            if (checkValidity(
                                    locationLat,
                                    locationLong,
                                    nearInfras
                                )
                            ) {      //if near infras from shared preferences exist ,
                                Log.i(
                                    "yarab",
                                    nearInfras
                                )                              //then check if the user is maximum far 2km from them
                                //else get new list,and if validated, then keep tracking them
                                makeUserSafe(
                                    locationLat,
                                    locationLong,
                                    speed,
                                    nearInfras
                                )  //and when they still have 7 seconds to reach infra, send the
                                //notification,and of course time remaining is being calculated
                            } else {                                                        //by diving distance remaining on speed
                                getNearInfras(locationLat, locationLong, speed)
                            }
                        } else {
                            getNearInfras(locationLat, locationLong, speed)
                        }
                    }
                }
            }
        }

        // show this toast if there is an error while reading from the database
        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(applicationContext, "Could not read from database", Toast.LENGTH_LONG)
                .show()
        }


    }

    //function that initiates the sending of the notification
// to the user if he is less than 7 seconds to reach an infra
    private fun makeUserSafe(liveLat: Double, liveLong: Double, speed: Double, nearInfras: String) {
        val array = JSONArray(nearInfras)
        (0 until array.length()).forEach {
            var infra = array.getJSONObject(it)
            val timeRemaining = getDistance(
                liveLat,
                liveLong,
                infra["latitude"] as Double,
                infra["longitude"] as Double
            ) / speed
            if (timeRemaining < 7) {
                Log.i("hamdella", timeRemaining.toString())
                sendNotification(applicationContext)
            }

        }

    }

    //function that checks if the list of near infras are up-to-date
// (by checking if it far from live position more than 2km, and if so it should be replaced by new list)
    private fun checkValidity(liveLat: Double, liveLong: Double, nearInfras: String): Boolean {
        val array = JSONArray(nearInfras)
        (0 until array.length()).forEach { it ->
            var infra = array.getJSONObject(it)
            val distance = getDistance(
                liveLat,
                liveLong,
                infra["latitude"] as Double,
                infra["longitude"] as Double
            )

            if (distance > 2) {
                return false
            }
        }
        return true
    }

    private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    //function to get all infras from database
    private fun getAllInfras() {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val jwtToken = sharedPref.getString("token", "")
        val apiService = RestApiService()
        val authenticatedHeaders =
            jwtToken?.let { ApiMainHeadersProvider.getAuthenticatedHeaders(it) }
        if (authenticatedHeaders != null) {
            apiService.getAllInfras(authenticatedHeaders, 0) {
                if (it?.size != null) {
                    Log.i("All Infras", Gson().toJson(it))
                    setMarkers(Gson().toJson(it))
                } else {
                    Log.i("Error", "Error")

                }
            }
        }

    }

    //function that traverses list of all infras and call addMarker function for every infra
    private fun setMarkers(data: String) {
        val array = JSONArray(data)
        (0 until array.length()).forEach {
            var infra = array.getJSONObject(it)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                addMarker(
                    infra["latitude"] as Double,
                    infra["longitude"] as Double,
                    infra["type"] as String,
                    infra["created_at"] as String,
                    infra["id"] as Int
                )
            }

        }
    }

    //function to add the convenient marker for a specific infra according to its type
    private fun addMarker(
        latitude: Double,
        longitude: Double,
        type: String,
        dateCreated: String,
        id: Int
    ) {

        latLng = LatLng(latitude, longitude)
        val date = dateCreated?.split(".")?.get(0) ?: ""
        if (type != null) {
            when (type) {
                "Hole" -> {
                    mMap.addMarker(
                        MarkerOptions().position(latLng)
                            .icon(bitmapFromVector(applicationContext, R.drawable.hole_marker))
                            .title("Hole")
                            .snippet(" id : $id \n $latLng\n Created at : $date")
                    )

                }
                "Blockage" -> {
                    mMap.addMarker(
                        MarkerOptions().position(latLng)
                            .icon(
                                bitmapFromVector(
                                    applicationContext,
                                    R.drawable.blockage_marker
                                )
                            )
                            .title("Blockage")
                            .snippet(" id : $id \n $latLng\n Created at : $date")
                    )

                }
                "Turn" -> {
                    mMap.addMarker(
                        MarkerOptions().position(latLng)
                            .icon(bitmapFromVector(applicationContext, R.drawable.turn_marker))
                            .title("Sharp Turn")
                            .snippet(" id : $id \n $latLng\n Created at : $date")
                    )

                }
                "Bump" -> {
                    mMap.addMarker(
                        MarkerOptions().position(latLng)
                            .icon(bitmapFromVector(applicationContext, R.drawable.bump_marker))
                            .title("Hard Bump")
                            .snippet(" id : $id \n $latLng\n Created at : $date")
                    )

                }
            }

        }


    }

    private fun getInfraType(v: View): String {
        val type = ""
        if (v != null) {
            when (v.id) {
                R.id.hole -> {
                    return type.replace("", "Hole")
                }
                R.id.turn -> {
                    return type.replace("", "Turn")
                }
                R.id.bump -> {
                    return type.replace("", "Bump")
                }
                R.id.blockage -> {
                    return type.replace("", "Blockage")
                }
            }
        }
        return type
    }

    private fun report(type: String, latitude: Double, longitude: Double) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val jwtToken = sharedPref.getString("token", "")
        val id = sharedPref.getString("user_id", "")?.toInt()
        val apiService = RestApiService()
        val authenticatedHeaders =
            jwtToken?.let { ApiMainHeadersProvider.getAuthenticatedHeaders(it) }
        if (authenticatedHeaders != null) {
            val reportInfo = ReportInfo(
                latitude = latitude,
                longitude = longitude,
                type = type,
                id = id
            )
            val authenticatedHeaders =
                ApiMainHeadersProvider.getAuthenticatedHeaders("")
            apiService.report(authenticatedHeaders, reportInfo) {
                if (it?.message == "Infrastructural problem reported successfully") {
                    val latLng = LatLng(latitude, longitude)
                    Log.i("Report Succeeded", it.message)
                    if (id != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            addMarker(latitude, longitude, type, LocalDateTime.now().toString(), id)

                        }
                    }
                } else {

                    Log.i("Error", "Report Failed !")
                }
            }
        }
    }

    private fun setupLocClient() {
        fusedLocClient =
            LocationServices.getFusedLocationProviderClient(this)
    }

    @SuppressLint("MissingPermission")
    private fun reportFunctionCaller(v: View, latitude: Double, longitude: Double) {
        val type = getInfraType(v)
        if (latitude != 0.0 && longitude != 0.0) {
            report(type, latitude, longitude)
        } else {
            fusedLocClient.lastLocation.addOnCompleteListener {
                // lastLocation is a task running in the background
                val location = it.result //obtain location
                //reference to the database
                if (location != null) {
                    report(type, location.latitude, location.longitude)
                } else {
                    // if location is null , log an error message
                    Log.e("error", "No location found")
                }


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

    private fun getNearInfras(latitude: Double, longitude: Double, speed: Double) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharedPref.edit()
        jwtToken = sharedPref.getString("token", "").toString()
        val apiService = RestApiService()
        val authenticatedHeaders =
            jwtToken?.let { ApiMainHeadersProvider.getAuthenticatedHeaders(it) }
        if (authenticatedHeaders != null) {
            val getNearInfrasInfo = GetNearInfrasInfo(
                base_latitude = latitude,
                base_longitude = longitude,
            )
            apiService.getNearInfras(authenticatedHeaders, getNearInfrasInfo) {
                if (it?.size != 0) {
                    Log.i("Near Infras", Gson().toJson(it))
                    editor.putString("Near Infras", Gson().toJson(it))
                    editor.apply()
                    editor.commit()
                    makeUserSafe(latitude, longitude, speed, Gson().toJson(it))
                } else {
                    Log.i("Error", "Error")

                }
            }

        }
    }


    override fun onMarkerClick(p0: Marker): Boolean {
        TODO("Not yet implemented")
    }

    override fun onInfoWindowClick(marker: Marker) {
        var infraId = (marker.snippet?.subSequence(6, 8) ?: null).toString()
        if (!infraId[1].isDigit()) {
            infraId = infraId[0].toString()
        }
        jwtToken = sharedPref.getString("token", "").toString()
        val apiService = RestApiService()
        val authenticatedHeaders =
            jwtToken?.let { ApiMainHeadersProvider.getAuthenticatedHeaders(it) }
        if (authenticatedHeaders != null) {
            apiService.reportFalseInfra(authenticatedHeaders, infraId.toInt()) {
                if (it?.message == "reported successfully") {
                    Toast.makeText(
                        applicationContext,
                        "Thanks for your feedback ! The Admin will check it ASAP !",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else {
                    Log.i("Error", "Error")

                }
            }

        }


    }

    override fun onMapClick(latLng: LatLng) {
        showBottomSheet(latLng.latitude, latLng.longitude)
    }

    private fun sendNotification(c: Context) {
        val notificationIntent = Intent(this, MapsActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notificationManager = NotificationManagerCompat.from(c)
        val notification = NotificationCompat.Builder(c, INFRA_CHANNEL_ID)
            .setContentTitle("Caution ! Infra Ahead ")
            .setContentText("Press to open app , or press below button to report false a false alarm ")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSmallIcon(R.drawable.app_peekaboo)
            .setSound(Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${c.packageName}/${R.raw.infra_notification}"))
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(INFRA_NOTIFICATION_ID, notification)

    }

    private fun createNotificationChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                    .apply {
                        lightColor = Color.CYAN
                        enableLights(true)
                    }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

}

