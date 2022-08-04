package com.example.watchout_frontend_kotlin.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.api.ApiMainHeadersProvider
import com.example.watchout_frontend_kotlin.api.RestApiService
import com.example.watchout_frontend_kotlin.models.EditProfileInfo
import com.example.watchout_frontend_kotlin.others.Constants
import com.example.watchout_frontend_kotlin.others.Constants.IMAGE_REQUEST_CODE
import com.example.watchout_frontend_kotlin.others.PublicFunctions
import com.mikhaellopez.circularimageview.CircularImageView
import java.io.ByteArrayOutputStream
import java.io.IOException

class ProfileActivity : AppCompatActivity() {
    private lateinit var profilePhoto: CircularImageView
    private lateinit var setPhoto: ImageView
    private lateinit var backArrow: ImageView
    private lateinit var editPassword: TextView
    private lateinit var firstnameEdit: EditText
    private lateinit var lastnameEdit: EditText
    private lateinit var phonenumberEdit: EditText
    private lateinit var usernameEdit: EditText
    private lateinit var passwordEdit: EditText
    private lateinit var confirmPasswordEdit: EditText
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var phoneNumber: String
    private lateinit var userName: String
    private lateinit var update: Button
    private var public = PublicFunctions()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        setContentView(R.layout.activity_profile)
        profilePhoto = findViewById(R.id.profile_photo)
        setPhoto = findViewById(R.id.set_photo)
        backArrow = findViewById(R.id.back_arrow)
        editPassword = findViewById(R.id.edit_password)
        firstnameEdit = findViewById(R.id.first_name)
        lastnameEdit = findViewById(R.id.last_name)
        phonenumberEdit = findViewById(R.id.phone_number)
        usernameEdit = findViewById(R.id.username)
        update = findViewById(R.id.update)
        firstnameEdit.setText(sharedPref.getString("firstname", ""))
        lastnameEdit.setText(sharedPref.getString("lastname", ""))
        phonenumberEdit.setText(sharedPref.getString("phonenumber", ""))
        usernameEdit.setText(sharedPref.getString("username", ""))
        profilePhoto.setOnClickListener {
            pickImageGallery()
        }
        setPhoto.setOnClickListener {
            pickImageGallery()
        }
        backArrow.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
            finish()
        }
        editPassword.setOnClickListener {
            popupPasswordDialog(this, R.layout.password_dialog, editor)
        }
        update.setOnClickListener {
            firstName = firstnameEdit.text.toString()
            lastName = lastnameEdit.text.toString()
            phoneNumber = phonenumberEdit.text.toString()
            userName = usernameEdit.text.toString()
            if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() ||
                userName.isEmpty()
            ) {

                Toast.makeText(
                    applicationContext,
                    "Please fill the missing fields !",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                if (!phoneNumber.all { char -> char.isDigit() }) {
                    Toast.makeText(
                        applicationContext,
                        "Phone number can contain only numbers !",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {

                    val jwtToken = sharedPref.getString("token", "")
                    val apiService = RestApiService()
                    val editProfileInfo = EditProfileInfo(
                        user_id = sharedPref.getString("user_id", "")?.toInt(),
                        firstname = sharedPref.getString("firstname", ""),
                        lastname = sharedPref.getString("lastname", ""),
                        phonenumber = sharedPref.getString("phonenumber", "")?.toInt(),
                        picture = sharedPref.getString("picture", ""),
                        username = sharedPref.getString("firstname", ""),
                        password = sharedPref.getString("password", ""),
                    )

                    val authenticatedHeaders =
                        jwtToken?.let { ApiMainHeadersProvider.getAuthenticatedHeaders(it) }
                    if (authenticatedHeaders != null) {
                        apiService.editProfile(authenticatedHeaders, editProfileInfo) {
                            if (it?.message == "updated successfully") {
                                editor.putString("firstname", firstName)
                                editor.putString("lastname", lastName)
                                editor.putString("phonenumber", phoneNumber)
                                editor.putString("username", userName)
                                editor.remove("password")
                                editor.apply()
                                editor.commit()
                                Log.i("message", "User edited successfully")
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        }
                    }
                }

            }
        }
    }

    private fun popupPasswordDialog(context: Context, id: Int, editor: SharedPreferences.Editor) {
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
        passwordEdit = view.findViewById(R.id.password)
        confirmPasswordEdit = view.findViewById(R.id.c_password)
        passwordEdit.setText(public.getDecryptedPassword(this))
        confirmPasswordEdit.setText(public.getDecryptedPassword(this))
        view.findViewById<View>(R.id.done_btn).setOnClickListener {
            val password = passwordEdit.text.toString()
            val confirmPassword = confirmPasswordEdit.text.toString()
            if (password != confirmPassword) {
                Toast.makeText(
                    this,
                    "Passwords don't match !",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                if (password.length < 6) {
                    Toast.makeText(
                        this,
                        "Password too short ! It should be minimum six characters",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    public.encryptAndSavePassword(this, password)
                    editor.putString("password", password)
                    popupDialog.dismiss()
                }
            }

        }

        popupDialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MapsActivity::class.java))
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
        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            profilePhoto.setImageURI(data?.data)
//            encodePicTo64(data?.data)
            val uri: Intent? = data
            try {
                if (uri != null) {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri.data)
                    // initialize byte stream
                    val stream = ByteArrayOutputStream()
                    // compress Bitmap
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    // Initialize byte array
                    val bytes = stream.toByteArray()
                    // get base64 encoded string
                    val imageString = Base64.encodeToString(bytes, Base64.DEFAULT)
                    // set encoded text on textview
                    editor.putString("picture", imageString)
                    editor.apply()
                    editor.commit()

                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

}