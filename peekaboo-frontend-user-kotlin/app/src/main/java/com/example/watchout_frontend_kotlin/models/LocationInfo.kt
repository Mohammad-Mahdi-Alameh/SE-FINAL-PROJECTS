package com.example.watchout_frontend_kotlin.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class LocationInfo(
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
    var speed: Double? =0.0


)