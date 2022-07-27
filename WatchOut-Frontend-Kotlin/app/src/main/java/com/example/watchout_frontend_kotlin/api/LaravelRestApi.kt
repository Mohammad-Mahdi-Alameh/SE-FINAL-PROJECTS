package com.example.watchout_frontend_kotlin.api

import com.example.watchout_frontend_kotlin.models.*
import retrofit2.Call
import retrofit2.http.*

interface LaravelRestApi {

    @POST("/api/v1/login")
    fun logIn(
        @HeaderMap authedHeaders: PublicHeaders,
        @Body loginInfo: LoginInfo
    ): Call<LoginResponse>

    @POST("/api/v1/user/signup")
    fun signUp(
        @HeaderMap authedHeaders: PublicHeaders,
        @Body signupInfo: SignupInfo
    ): Call<SignupResponse>

    @POST("/api/v1/user/report")
    fun report(
        @HeaderMap authedHeaders: AuthenticatedHeaders,
        @Body reportInfo: ReportInfo
    ): Call<ReportResponse>

    //infras : infrastructural problems
    @POST("/api/v1/user/getNearInfras")
    fun getNearInfras(
        @HeaderMap authedHeaders: AuthenticatedHeaders,
        @Body getNearInfrasInfo: GetNearInfrasInfo
    ): Call<List<GetInfrasResponse>>

    @GET("/api/v1/user/getAllInfras/")
    fun getAllInfras(
        @HeaderMap authedHeaders: AuthenticatedHeaders,
        @Query("user_id") user_id: String?,
    ): Call<List<GetInfrasResponse>>

    @PUT("/api/v1/user/editProfile")
    fun editProfile(
        @HeaderMap authedHeaders: AuthenticatedHeaders,
        @Body editProfileInfo: EditProfileInfo
    ): Call<EditProfileResponse>


}