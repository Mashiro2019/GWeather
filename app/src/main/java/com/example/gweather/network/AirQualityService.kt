package com.example.gweather.network

import com.example.gweather.model.AQResponse
import com.example.gweather.utils.AppUtils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityService {
    /**
     * 请求空气质量数据
     * @param location String
     * @return Call<AQResponse>
     */
    @GET("v3/air/daily.json?key=${AppUtils.TOKEN}&language=zh-Hans")
    fun searchAirQuality(@Query("location") location:String,
                         @Query("days") days:Int): Call<AQResponse>
}