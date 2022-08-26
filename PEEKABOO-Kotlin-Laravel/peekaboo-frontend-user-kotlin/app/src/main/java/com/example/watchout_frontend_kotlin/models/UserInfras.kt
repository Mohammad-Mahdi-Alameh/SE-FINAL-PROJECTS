package com.example.watchout_frontend_kotlin.models

import com.google.gson.annotations.SerializedName

data class UserInfras (
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("type") val type: String?,
    @SerializedName("created_at") val date: String?,
    )