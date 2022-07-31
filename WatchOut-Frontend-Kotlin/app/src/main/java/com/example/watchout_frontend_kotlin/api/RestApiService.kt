package com.example.watchout_frontend_kotlin.api

import android.util.Log
import com.example.watchout_frontend_kotlin.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

    fun login(
        authedHeaders: PublicHeaders,
        loginInfo: LoginInfo,
        onResult: (LoginResponse?) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(LaravelRestApi::class.java)
        retrofit.logIn(authedHeaders, loginInfo).enqueue(
            object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.i("error", "error in getting response")
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val addedUser = response.body()
                    onResult(addedUser)
                    ////
                    if (response.code() == 400) {
                        response.errorBody()?.let { Log.e("error", it.string()) }
                    }
                    Log.d("User", response.toString())
                }
            }
        )
    }

    fun signUp(
        authedHeaders: PublicHeaders,
        signupInfo: SignupInfo,
        onResult: (SignupResponse?) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(LaravelRestApi::class.java)
        retrofit.signUp(authedHeaders, signupInfo).enqueue(
            object : Callback<SignupResponse> {
                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.i("error", "error in getting response")
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    val addedUser = response.body()
                    Log.d("Added User", response.body().toString())
                    onResult(addedUser)


                }
            }
        )
    }

    fun report(
        authedHeaders: AuthenticatedHeaders,
        reportInfo: ReportInfo,
        onResult: (ReportResponse?) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(LaravelRestApi::class.java)
        retrofit.report(authedHeaders, reportInfo).enqueue(
            object : Callback<ReportResponse> {
                override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                    Log.i("error", "error in getting response")
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<ReportResponse>,
                    response: Response<ReportResponse>
                ) {
                    val submittedReport = response.body()
                    Log.d("Response", response.body().toString())
                    onResult(submittedReport)


                }
            }
        )
    }

    //infras:Infrastructural problems
    fun getNearInfras(
        authedHeaders: AuthenticatedHeaders,
        getNearInfrasInfo: GetNearInfrasInfo,
        onResult: (List<GetInfrasResponse>?) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(LaravelRestApi::class.java)
        retrofit.getNearInfras(authedHeaders, getNearInfrasInfo).enqueue(
            object : Callback<List<GetInfrasResponse>> {
                override fun onFailure(call: Call<List<GetInfrasResponse>>, t: Throwable) {
                    Log.d("error", "error in getting response")
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<List<GetInfrasResponse>>,
                    response: Response<List<GetInfrasResponse>>
                ) {
                    val nearInfras = response.body()
                    Log.d("Near Infras", response.toString())
                    onResult(nearInfras)


                }
            }
        )
    }

    fun getAllInfras(
        authedHeaders: AuthenticatedHeaders,
        user_id: Int?,
        onResult: (List<GetInfrasResponse>?) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(LaravelRestApi::class.java)
        retrofit.getAllInfras(authedHeaders, user_id).enqueue(
            object : Callback<List<GetInfrasResponse>> {
                override fun onFailure(call: Call<List<GetInfrasResponse>>, t: Throwable) {
                    Log.d("error", "error in getting response")
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<List<GetInfrasResponse>>,
                    response: Response<List<GetInfrasResponse>>
                ) {
                    val nearInfras = response.body()
                    Log.d("All Infras", response.toString())
                    onResult(nearInfras)


                }
            }
        )
    }
    fun editProfile(
        authedHeaders: AuthenticatedHeaders,
        editProfileInfo: EditProfileInfo,
        onResult: (SingleMessageResponse?) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(LaravelRestApi::class.java)
        retrofit.editProfile(authedHeaders, editProfileInfo).enqueue(
            object : Callback<SingleMessageResponse> {
                override fun onFailure(call: Call<SingleMessageResponse>, t: Throwable) {
                    Log.d("error", "error in getting response")
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<SingleMessageResponse>,
                    response: Response<SingleMessageResponse>
                ) {
                    val result = response.body()
                    Log.d("message", response.toString())
                    onResult(result)


                }
            }
        )
    }
    fun addCoins(
        authedHeaders: AuthenticatedHeaders,
        user_id: Int,
        onResult: (AddCoinsResponse?) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(LaravelRestApi::class.java)
        retrofit.addCoins(authedHeaders, user_id).enqueue(
            object : Callback<AddCoinsResponse> {
                override fun onFailure(call: Call<AddCoinsResponse>, t: Throwable) {
                    Log.d("error", "error in getting response")
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<AddCoinsResponse>,
                    response: Response<AddCoinsResponse>
                ) {
                    val result = response.body()
                    Log.d("Result", response.toString())
                    onResult(result)


                }
            }
        )
    }
    fun reportFalseInfra(
        authedHeaders: AuthenticatedHeaders,
        infraId: Int,
        onResult: (SingleMessageResponse?) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(LaravelRestApi::class.java)
        retrofit.reportFalseInfra(authedHeaders, infraId).enqueue(
            object : Callback<SingleMessageResponse> {
                override fun onFailure(call: Call<SingleMessageResponse>, t: Throwable) {
                    Log.d("error", "error in getting response")
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<SingleMessageResponse>,
                    response: Response<SingleMessageResponse>
                ) {
                    val result = response.body()
                    Log.d("Result", response.toString())
                    onResult(result)


                }
            }
        )
    }
}