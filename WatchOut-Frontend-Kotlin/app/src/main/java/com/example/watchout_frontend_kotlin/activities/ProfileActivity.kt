package com.example.watchout_frontend_kotlin.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.others.Constants
import com.example.watchout_frontend_kotlin.others.Constants.IMAGE_REQUEST_CODE
import com.example.watchout_frontend_kotlin.others.getDecryptedPassword
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
        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setContentView(R.layout.activity_profile)
        profilePhoto = findViewById(R.id.profile_photo)
        setPhoto = findViewById(R.id.set_photo)
        backArrow = findViewById(R.id.back_arrow)
        editPassword = findViewById(R.id.edit_password)
        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name)
        phoneNumber = findViewById(R.id.phone_number)
        username = findViewById(R.id.username)

        firstName.setText(sharedPref.getString("firstname",""))
        lastName.setText(sharedPref.getString("lastname",""))
        phoneNumber.setText(sharedPref.getString("phonenumber",""))
        username.setText(sharedPref.getString("username",""))
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
        editPassword.setOnClickListener {
            popupPasswordDialog(this, R.layout.password_dialog)
        }


    }

    private fun popupPasswordDialog(context: Context, id: Int) {
        val view = View.inflate(context, id, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)

        val popupDialog = builder.create()
        popupDialog.show()
        popupDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        popupDialog.window?.setGravity(Gravity.CENTER)
        popupDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        popupDialog.window?.setLayout(
            ActionBar.LayoutParams.WRAP_CONTENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        popupDialog.setCancelable(false)
        password = view.findViewById(R.id.password)
        confirmPassword = view.findViewById(R.id.c_password)
        password.setText(getDecryptedPassword( this))
        confirmPassword.setText(getDecryptedPassword(this))
        view.findViewById<View>(R.id.done_btn).setOnClickListener {
            if (password.text.toString() != confirmPassword.text.toString()) {
                Toast.makeText(
                    this,
                    "Passwords don't match !",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                popupDialog.dismiss()
            }
        }
        popupDialog.show()
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