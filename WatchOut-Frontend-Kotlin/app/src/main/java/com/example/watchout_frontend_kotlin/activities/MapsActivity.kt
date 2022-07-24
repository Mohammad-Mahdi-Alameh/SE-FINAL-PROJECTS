package com.example.watchout_frontend_kotlin.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.databinding.ActivityMapsBinding
import com.example.watchout_frontend_kotlin.models.LocationInfo
import com.example.watchout_frontend_kotlin.others.Constants
import com.example.watchout_frontend_kotlin.others.popupAlertDialog
import com.example.watchout_frontend_kotlin.services.TrackingService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var hamburger: ImageView
    private lateinit var coins: TextView
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var dbReference: DatabaseReference = database.getReference("Live-Tracking")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hamburger = findViewById(R.id.hamburger_btn)
        coins = findViewById(R.id.coins)
        drawerLayout = findViewById(R.id.drawer_layout)
        hamburger.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            drawerLayout.closeDrawer(GravityCompat.START)
            when (id) {
                R.id.edit_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                }
                R.id.history -> Toast.makeText(
                    this,
                    "Your Report History is Clicked",
                    Toast.LENGTH_SHORT
                ).show()
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
        val report = findViewById<Button>(R.id.report_btn)
        report.setOnClickListener {
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
                popupAlertDialog(this, R.layout.alert_dialog)
                addCoins()
            }
            bottomSheetView.findViewById<View>(R.id.turn).setOnClickListener {
                bottomSheetDialog.dismiss()
                popupAlertDialog(this, R.layout.alert_dialog)
                addCoins()
            }
            bottomSheetView.findViewById<View>(R.id.hole).setOnClickListener {
                bottomSheetDialog.dismiss()
                popupAlertDialog(this, R.layout.alert_dialog)
                addCoins()
            }
            bottomSheetView.findViewById<View>(R.id.blockage).setOnClickListener {
                bottomSheetDialog.dismiss()
                popupAlertDialog(this, R.layout.alert_dialog)
                addCoins()
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

    }

    private fun logout() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.commit()
        stopTrackingService(this)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun addCoins() {
        //to implement later :
        // 1)store new value in shared preferences
        // 2)call api to add it to database
        // 3)pass userId as a parameter
        var balance = coins.text.toString().toInt()
        balance += 5
        coins.text = balance.toString()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        dbReference = Firebase.database.reference
        dbReference.addValueEventListener(locListener)

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

    private fun stopTrackingService(context: Context) {
        val stopServiceIntent = Intent(context, TrackingService::class.java)
        context.stopService(stopServiceIntent)
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

    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), //permission in the manifest
            Constants.REQUEST_LOCATION
        )
    }

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



                if (locationLat != null && locationLong != null) {
                    // create a LatLng object from location
                    val latLng = LatLng(locationLat, locationLong)
                    //create a marker at the read location and display it on the map
                    mMap.addMarker(
                        MarkerOptions().position(latLng)
                            .icon(bitmapFromVector(applicationContext, R.drawable.ic_tracker))
                    )
                    //specify how the map camera is updated
                    val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
                    //update the camera with the CameraUpdate object
                    mMap.moveCamera(update)
                } else {
                    // if location is null , log an error message
                    Log.e("Error", "user location cannot be found")
                }
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


}
