package com.example.watchout_frontend_kotlin.api

import com.example.watchout_frontend_kotlin.models.*
import retrofit2.Call
import retrofit2.http.*

interface LaravelRestApi {

    @POST("/api/v1/user/login")
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
    @POST("/api/v1/user/get_near_infras")
    fun getNearInfras(
        @HeaderMap authedHeaders: AuthenticatedHeaders,
        @Body getNearInfrasInfo: GetNearInfrasInfo
    ): Call<List<GetInfrasResponse>>

    @GET("/api/v1/user/get_all_infras/{user_id}")
    fun getAllInfras(
        @HeaderMap authedHeaders: AuthenticatedHeaders,
        @Path("user_id") id: Int,
    ): Call<List<GetInfrasResponse>>

    @PUT("/api/v1/user/edit_profile")
    fun editProfile(
        @HeaderMap authedHeaders: AuthenticatedHeaders,
        @Body editProfileInfo: EditProfileInfo
    ): Call<SingleMessageResponse>

    @PUT("/api/v1/user/add_coins/{user_id}")
    fun addCoins(
        @HeaderMap authedHeaders: AuthenticatedHeaders,
        @Path("user_id") userId: Int,
    ): Call<AddCoinsResponse>

    @PUT("/api/v1/user/false_infra/{infra_id}")
    fun reportFalseInfra(
        @HeaderMap authedHeaders: AuthenticatedHeaders,
        @Path("infra_id") infraId: Int,
    ): Call<SingleMessageResponse>

    @POST("/api/v1/user/logout")
    fun logout(
        @HeaderMap authedHeaders: AuthenticatedHeaders,
    ): Call<SingleMessageResponse>



}