package com.example.watchout_frontend_kotlin.model

import com.google.gson.annotations.SerializedName

data class ReportInfo (
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("longitude") val longitude: String?
)