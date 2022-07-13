package com.example.watchout_frontend_kotlin.api

import com.example.watchout_frontend_kotlin.model.LoginInfo
import com.example.watchout_frontend_kotlin.model.LoginResponse
import com.example.watchout_frontend_kotlin.model.SignupInfo
import com.example.watchout_frontend_kotlin.model.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LaravelRestApi {

    @Headers("Content-Type: application/json")
    @POST("/api/v1/user/login")
    fun logIn(@Body loginInfo: LoginInfo): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/api/v1/user/signup")
    fun signUp(@Body signupInfo: SignupInfo): Call<SignupResponse>
}