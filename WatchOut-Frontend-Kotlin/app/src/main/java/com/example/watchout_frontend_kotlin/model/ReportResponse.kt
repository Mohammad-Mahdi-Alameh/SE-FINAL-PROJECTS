package com.example.watchout_frontend_kotlin.model

import com.google.gson.annotations.SerializedName

data class ReportResponse (
    @SerializedName("message") val message: String?,
)