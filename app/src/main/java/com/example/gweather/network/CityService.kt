package com.example.gweather.network

import com.example.gweather.model.City
import com.example.gweather.model.CityResponse
import com.example.gweather.model.ResultResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CityService {
    /**
     * 查找城市请求
     * @return Call<UserResponse>
     */
    @GET("search")
    fun searchCity(): Call<CityResponse>

    /**
     * 添加城市请求
     * @param city City
     * @return Call<ResultResponse>
     */
    @POST("add")
    fun addCity(
        @Body city: City
    ): Call<ResultResponse>
}