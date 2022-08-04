package com.example.watchout_frontend_kotlin.models

import com.google.gson.annotations.SerializedName

//infras : infrastructural problems
data class GetInfrasResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("type") val type: String?,
    @SerializedName("false_infra") val falseInfra: Int?,
    @SerializedName("is_fixed") val isFixed: Int?,
    @SerializedName("user_id") val userId: Int?,
    @SerializedName("pending") val pending: Int?,
    @SerializedName("accepted") val accepted: Int?,
    @SerializedName("rejected") val rejected: Int?,
    @SerializedName("created_at") val date: String?,
)