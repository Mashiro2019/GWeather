package com.gcode.gweather.utils

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan

/**
 * 字体设置单例类
 */
object ViewUtils {

    fun setBottomAlignment(
        valueString: String,
        unitString: String,
        valueSize: Int
    ): SpannableStringBuilder {
        val spanString = SpannableStringBuilder(valueString + unitString)
        //绝对尺寸
        val absoluteSizeSpan = AbsoluteSizeSpan(2 * valueSize)
        spanString.setSpan(
            absoluteSizeSpan,
            0,
            valueString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spanString
    }
}