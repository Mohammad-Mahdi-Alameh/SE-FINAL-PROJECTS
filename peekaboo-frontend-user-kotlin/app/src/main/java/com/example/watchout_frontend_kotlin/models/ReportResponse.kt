package com.example.watchout_frontend_kotlin.models

import com.google.gson.annotations.SerializedName

data class ReportResponse (
    @SerializedName("message") val message: String?,
)