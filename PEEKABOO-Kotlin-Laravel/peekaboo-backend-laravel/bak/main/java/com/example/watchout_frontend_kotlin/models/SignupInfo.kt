package com.example.watchout_frontend_kotlin.models

import com.google.gson.annotations.SerializedName

data class SignupInfo (
    @SerializedName("firstname") val firstname: String?,
    @SerializedName("lastname") val lastname: String?,
    @SerializedName("phonenumber") val phonenumber: Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("c_password") val c_password: String?
)