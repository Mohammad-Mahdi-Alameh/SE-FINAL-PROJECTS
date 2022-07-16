package com.example.watchout_frontend_kotlin.models

import com.google.gson.annotations.SerializedName
//infras : infrastructural problems
data class GetNearInfrasResponse (
    @SerializedName("latitude") val latitude: Boolean?,
    @SerializedName("longitude") val longitude: String?,
    @SerializedName("type_id") val message: String?,
)