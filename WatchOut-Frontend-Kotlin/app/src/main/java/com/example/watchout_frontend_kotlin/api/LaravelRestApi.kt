package com.example.watchout_frontend_kotlin.api

import com.example.watchout_frontend_kotlin.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST

interface LaravelRestApi {

    @Headers("Content-Type: application/json")
    @POST("/api/v1/user/login")
    fun logIn(@Body loginInfo: LoginInfo): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/api/v1/user/signup")
    fun signUp(@Body signupInfo: SignupInfo): Call<SignupResponse>

    @POST("/api/v1/user/report")
    fun report(@HeaderMap authedHeaders: AuthenticatedHeaders,@Body reportInfo: ReportInfo): Call<ReportResponse>

    //infras : infrastructural problems
    @POST("/api/v1/user/getNearInfras")
    fun getNearInfras(@HeaderMap authedHeaders: AuthenticatedHeaders,@Body getNearInfrasInfo: GetNearInfrasInfo): Call<List<GetInfrasResponse>>








}