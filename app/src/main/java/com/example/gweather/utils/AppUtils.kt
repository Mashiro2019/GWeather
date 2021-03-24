package com.example.gweather.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Typeface

/**
 * 获取全局Context
 */
class AppUtils:Application(){
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        //心知天气私钥
        const val TOKEN = "SIdsDE0o7sEAIFYnM"
        lateinit var appIcon:Typeface
        lateinit var managerFragment:Typeface
        lateinit var iconDesign:Typeface
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        appIcon = Typeface.createFromAsset(applicationContext.assets,"fonts/appIcon.ttf")
        managerFragment = Typeface.createFromAsset(applicationContext.assets,"fonts/managerFragment.ttf")
        iconDesign = Typeface.createFromAsset(applicationContext.assets,"fonts/iconDesign.ttf")
    }
}