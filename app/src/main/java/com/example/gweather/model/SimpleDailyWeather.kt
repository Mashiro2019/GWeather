package com.example.gweather.model

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.gweather.R
import com.example.gweather.adapter.BindingAdapterItem
import com.example.gweather.utils.AppUtils

/**
 *作者:created by HP on 2021/3/11 20:15
 *邮箱:sakurajimamai2020@qq.com
 */
class SimpleDailyWeather(
    val date:String,
    val text_day:String,
    private val code_day:String,
    private val text_night:String,
    private val code_night:String,
    val high:String,
    val low:String,
): BindingAdapterItem {
    override fun getViewType(): Int {
        return R.layout.item_daily_weather
    }

    private val icon: Drawable? = when(text_day){
        "晴"->
            ContextCompat.getDrawable(AppUtils.context,R.drawable.sunny_day)
        "多云"->
            ContextCompat.getDrawable(AppUtils.context,R.drawable.partly_cloudy)
        "阵雨"->
            ContextCompat.getDrawable(AppUtils.context,R.drawable.light_rain)
        "阴"->
            ContextCompat.getDrawable(AppUtils.context,R.drawable.partly_cloudy)
        "雾"->
            ContextCompat.getDrawable(AppUtils.context,R.drawable.partly_cloudy)
        else -> ContextCompat.getDrawable(AppUtils.context,R.drawable.sunny_day)
    }

    fun getIcon(): Drawable? {
        return icon
    }
}