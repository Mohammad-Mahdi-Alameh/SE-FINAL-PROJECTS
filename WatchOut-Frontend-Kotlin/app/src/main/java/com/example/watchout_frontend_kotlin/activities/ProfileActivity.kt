package com.example.watchout_frontend_kotlin.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.others.Constants
import com.example.watchout_frontend_kotlin.others.Constants.IMAGE_REQUEST_CODE
import com.mikhaellopez.circularimageview.CircularImageView

class ProfileActivity : AppCompatActivity() {
    private lateinit var profilePhoto: CircularImageView
    private lateinit var setPhoto: ImageView
    private lateinit var backArrow: ImageView
    private lateinit var editPassword: TextView
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        profilePhoto = findViewById(R.id.profile_photo)
        setPhoto = findViewById(R.id.set_photo)
        backArrow = findViewById(R.id.back_arrow)
        editPassword = findViewById(R.id.edit_password)
        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name)
        phoneNumber = findViewById(R.id.phone_number)
        username = findViewById(R.id.username)
        profilePhoto.setOnClickListener {
            pickImageGallery()
        }
        setPhoto.setOnClickListener {
            pickImageGallery()
        }
        backArrow.setOnClickListener {
            startActivity(Intent(this,MapsActivity::class.java))
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MapsActivity::class.java))
        finish()
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        if (ActivityCompat.checkSelfPermission(
                this!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestStoragePermissions()
        } else {
        startActivityForResult(intent, IMAGE_REQUEST_CODE)

        }
    }
    private fun requestStoragePermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), //permission in the manifest
            Constants.REQUEST_STORAGE
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //check if the request code matches the REQUEST_LOCATION
        if (requestCode == Constants.REQUEST_STORAGE) {
            //check if grantResults contains PERMISSION_GRANTED.If it does, call getCurrentLocation()
            if (grantResults.size == 1 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                pickImageGallery()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Storage permission was denied",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            profilePhoto.setImageURI(data?.data)
        }
    }
}