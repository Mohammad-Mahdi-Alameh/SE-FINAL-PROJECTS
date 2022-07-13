package com.example.watchout_frontend_kotlin.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("success") val success: Boolean?,
    @SerializedName("token") val token: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("is_admin") val is_admin: String?
)