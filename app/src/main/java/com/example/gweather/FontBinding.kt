package com.example.gweather

import android.graphics.Typeface
import androidx.databinding.BindingConversion
import com.example.gweather.utils.AppUtils

object FontBinding {
    @BindingConversion
    @JvmStatic
    fun convertStringToFace(s: String?): Typeface {
        return try {
            Typeface.createFromAsset(AppUtils.context.assets, s)
        } catch (e: Exception) {
            throw e
        }
    }
}