package com.example.watchout_frontend_kotlin.models

import com.google.gson.annotations.SerializedName
//infras : infrastructural problems
data class GetNearInfrasInfo (
    @SerializedName("base_latitude") val base_latitude: Double?,
    @SerializedName("base_longitude") val base_longitude: Double?
)