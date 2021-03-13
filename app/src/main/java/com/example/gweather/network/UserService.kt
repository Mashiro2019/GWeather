package com.example.gweather.network

import com.example.gweather.model.Account
import com.example.gweather.model.ResultResponse
import com.example.gweather.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *作者:created by HP on 2021/3/11 14:44
 *邮箱:sakurajimamai2020@qq.com
 */
interface UserService {
    /**
     * 用户登录请求
     * @param username String
     * @param password String
     * @return Call<UserResponse>
     */
    @GET("login")
    fun userLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<UserResponse>

    /**
     * 创建用户请求
     * @param account Account
     * @return Call<ResultResponse>
     */
    @POST("create")
    fun createAccount(
        @Body account: Account
    ): Call<ResultResponse>
}