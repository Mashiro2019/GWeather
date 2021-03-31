package com.gcode.gweather.model

import com.example.gweather.R
import com.gcode.gweather.adapter.BindingAdapterItem
import com.gcode.gweather.utils.AppUtils

/**
 *作者:created by HP on 2021/3/11 20:15
 *邮箱:sakurajimamai2020@qq.com
 */
class SimpleDailyWeather(
    val date: String,
    private val text_day: String,
    private val code_day: String,
    private val text_night: String,
    private val code_night: String,
    val high: String,
    val low: String,
    val windSpeed: String,
    val humidity: String
) : BindingAdapterItem {
    override fun getViewType(): Int {
        return R.layout.item_daily_weather
    }

    private val icon: String = when (text_day) {
        "晴" ->
            AppUtils.context.resources.getString(R.string.ic_sunny_day)
        "多云" ->
            AppUtils.context.resources.getString(R.string.ic_partly_cloudy)
        "阵雨" ->
            AppUtils.context.resources.getString(R.string.ic_shower)
        "阴" ->
            AppUtils.context.resources.getString(R.string.ic_cloudy_day)
        "雾" ->
            AppUtils.context.resources.getString(R.string.ic_fog)
        else -> AppUtils.context.resources.getString(R.string.ic_sunny_day)
    }

    fun getIcon(): String {
        return icon
    }
}