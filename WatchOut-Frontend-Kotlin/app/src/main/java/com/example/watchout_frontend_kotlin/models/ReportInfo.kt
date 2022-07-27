package com.example.watchout_frontend_kotlin.models

import com.google.gson.annotations.SerializedName

data class ReportInfo (
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("type") val type: String?,
    @SerializedName("user_id") val id: Int?,
)