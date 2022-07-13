package com.example.watchout_frontend_kotlin.api

import android.util.Log
import com.example.watchout_frontend_kotlin.model.LoginInfo
import com.example.watchout_frontend_kotlin.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {

    fun login(loginInfo: LoginInfo, onResult: (LoginResponse?) -> Unit){
        val retrofit = ServiceBuilder.buildService(LaravelRestApi::class.java)
        retrofit.logIn(loginInfo).enqueue(
            object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.i("error","error in getting response")
                    onResult(null)
                }
                override fun onResponse( call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                    if (response.code() == 400) {
                        response.errorBody()?.let { Log.e("error", it.string()) }
                    }
                    Log.d("User",response.toString())
                }
            }
        )
    }
}