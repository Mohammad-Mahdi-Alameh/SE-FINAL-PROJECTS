package com.example.watchout_frontend_kotlin.models

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

//infras : infrastructural problems
data class GetInfrasResponse (
    @SerializedName("id") val id: Int?,
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("type") val type: String?,
    @SerializedName("false_alarms") val falseAlarms: Int?,
    @SerializedName("user_id") val userId: Int?,
    @SerializedName("created_at") val date: Timestamp?,
)