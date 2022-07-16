package com.example.watchout_frontend_kotlin.models

import com.google.gson.annotations.SerializedName

data class ReportInfo (
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?,
    @SerializedName("type") val type: String?
)