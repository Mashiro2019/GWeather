package com.example.gweather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    //天气请求根路径
    private const val WEATHER_BASE_URL = "https://api.seniverse.com/"

    private val weatherRetrofit = Retrofit.Builder()
        .baseUrl(WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //用户数据请求根路径
    private const val USER_BASE_URL = "https://login.entropy2020.cn/"

    private val userRetrofit = Retrofit.Builder()
        .baseUrl(USER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> weatherCreate(serviceClass:Class<T>):T = weatherRetrofit.create(serviceClass)

    fun <T> userCreate(serviceClass:Class<T>):T = userRetrofit.create(serviceClass)

    //inline fun <reified T> create():T = create(T::class.java)
}