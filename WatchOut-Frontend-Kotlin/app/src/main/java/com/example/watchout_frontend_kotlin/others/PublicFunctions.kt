package com.example.watchout_frontend_kotlin.others

import android.content.Context
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.example.watchout_frontend_kotlin.R
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec


class PublicFunctions{

fun popupAlertDialog(context: Context, id: Int) {
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
    popupDialog.setCancelable(true)
    view.findViewById<View>(R.id.okay_btn).setOnClickListener {
        popupDialog.dismiss()
    }
    popupDialog.show()
}
// fun buildDialog(context: Context, id: Int) : AlertDialog{
//    val view = View.inflate(context, id, null)
//    val builder = AlertDialog.Builder(context)
//    builder.setView(view)
//
//    val popupDialog = builder.create()
//    popupDialog.show()
//    popupDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//    popupDialog.window?.setGravity(Gravity.CENTER)
//    popupDialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
//    popupDialog.window?.setLayout(
//        ActionBar.LayoutParams.WRAP_CONTENT,
//        ActionBar.LayoutParams.WRAP_CONTENT
//    )
//
//    return popupDialog
//}

fun encryptAndSavePassword(context: Context, strToEncrypt: String): ByteArray {
    val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
    val keygen = KeyGenerator.getInstance("AES")
    keygen.init(256)
    val key = keygen.generateKey()
    saveSecretKey(context, key)
    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    val cipherText = cipher.doFinal(plainText)
    saveInitializationVector(context, cipher.iv)

    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(cipherText)
    val strToSave =
        String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPref.edit()
    editor.putString("encrypted_password", strToSave)
    editor.apply()

    return cipherText
}

fun getDecryptedPassword(context: Context): String {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val strEncryptedPassword = sharedPref.getString("encrypted_password", "")
    val bytes = android.util.Base64.decode(strEncryptedPassword, android.util.Base64.DEFAULT)
    val ois = ObjectInputStream(ByteArrayInputStream(bytes))
    val passwordToDecryptByteArray = ois.readObject() as ByteArray
    val decryptedPasswordByteArray = decrypt(context, passwordToDecryptByteArray)

    val decryptedPassword = StringBuilder()
    for (b in decryptedPasswordByteArray) {
        decryptedPassword.append(b.toInt().toChar())
    }

    return decryptedPassword.toString()
}

fun decrypt(context: Context, dataToDecrypt: ByteArray): ByteArray {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    val ivSpec = IvParameterSpec(getSavedInitializationVector(context))
    cipher.init(Cipher.DECRYPT_MODE, getSavedSecretKey(context), ivSpec)
    val cipherText = cipher.doFinal(dataToDecrypt)
    return cipherText
}

fun saveSecretKey(context: Context, secretKey: SecretKey) {
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(secretKey)
    val strToSave =
        String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPref.edit()
    editor.putString("secret_key", strToSave)
    editor.apply()
}

fun getSavedSecretKey(context: Context): SecretKey {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val strSecretKey = sharedPref.getString("secret_key", "")
    val bytes = android.util.Base64.decode(strSecretKey, android.util.Base64.DEFAULT)
    val ois = ObjectInputStream(ByteArrayInputStream(bytes))
    val secretKey = ois.readObject() as SecretKey
    return secretKey
}

fun saveInitializationVector(context: Context, initializationVector: ByteArray) {
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(initializationVector)
    val strToSave =
        String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPref.edit()
    editor.putString("initialization_vector", strToSave)
    editor.apply()
}

fun getSavedInitializationVector(context: Context): ByteArray {
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    val strInitializationVector = sharedPref.getString("initialization_vector", "")
    val bytes = android.util.Base64.decode(strInitializationVector, android.util.Base64.DEFAULT)
    val ois = ObjectInputStream(ByteArrayInputStream(bytes))
    val initializationVector = ois.readObject() as ByteArray
    return initializationVector
}
}
//fun logIn(username: String, password: String, context: Context) {
//    if (username.isEmpty() || password.isEmpty()) {
//        Toast.makeText(
//            context,
//            "Please fill the missing fields !",
//            Toast.LENGTH_SHORT
//        )
//            .show()
//
//    } else {
//        val apiService = RestApiService()
//        val loginInfo = LoginInfo(
//            username = username,
//            password = password
//        )
//
//        apiService.login(loginInfo) {
//            if (it != null) {
//                if (it.token != null) {
//                    Log.i("token", it.token)
//                    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
//                    val editor: SharedPreferences.Editor = sharedPref.edit()
//                    editor.putString("token", it.token)
//                    editor.putString("user_id", it.id.toString())
//                    editor.putString("firstname", it.firstname)
//                    editor.putString("lastname", it.lastname)
//                    editor.putString("phonenumber", it.phonenumber.toString())
//                    editor.putString("balance", it.balance.toString())
//                    editor.putString("picture", it?.picture)//because user may not have a picture
//                    editor.putString("username", username)
//                    encryptAndSavePassword(
//                        context,
//                        password
//                    ) // password will be encrypted and saved in shared preferences
//                    editor.putString("token", it.token)
//                    editor.apply()
//                    editor.commit()
//                } else {
//                    Log.i("Login Error", "Wrong username or password !")
//                }
//            }
//        }
//    }
//}
