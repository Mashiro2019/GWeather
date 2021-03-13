package com.example.gweather.network

import com.example.gweather.model.DailyDataResponse
import com.example.gweather.model.NowDataResponse
import com.example.gweather.utils.AppUtils
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceWeatherService {
    /**
     * 查询当日天气
     * @param location String
     * @return Call<DataResponse>
     */
    @GET("v3/weather/now.json?key=${AppUtils.TOKEN}&language=zh-Hans&unit=c")
    fun searchPlaceWeather(@Query("location") location:String):Call<NowDataResponse>

    /**
     * 调查最近几天的天气
     * @param location String
     * @return Call<DataResponse>
     */
    @GET("v3/weather/daily.json?key=${AppUtils.TOKEN}&language=zh-Hans&unit=c&start=0&days=5")
    fun searchDailyWeather(@Query("location") location:String):Call<DailyDataResponse>
}