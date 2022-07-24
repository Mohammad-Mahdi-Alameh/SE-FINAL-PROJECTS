package com.example.watchout_frontend_kotlin.models

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("success") val success: Boolean?,
    @SerializedName("token") val token: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("firstname") val firstname: String?,
    @SerializedName("lastname") val lastname: String?,
    @SerializedName("phonenumber") val phonenumber: Int?,
    @SerializedName("balance") val balance: Int?,
    @SerializedName("picture") val picture: String?,
    @SerializedName("is_admin") val is_admin: String?
)
