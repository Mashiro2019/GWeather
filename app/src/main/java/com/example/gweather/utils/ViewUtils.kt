package com.example.gweather.utils

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

/**
 * 字体设置单例类
 */
object ViewUtils{
    /**
     * @param valueString String
     * @param unitString String
     * @return SpannableStringBuilder?
     */
    fun setBottomAlignment(valueString: String, unitString: String): SpannableStringBuilder {
        val spanString = SpannableStringBuilder(valueString + unitString)
        //绝对尺寸
        val absoluteSizeSpan = AbsoluteSizeSpan(60)
        spanString.setSpan(
            absoluteSizeSpan,
            valueString.length,
            spanString.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        // 字体颜色
        val colorSpan = ForegroundColorSpan(Color.BLACK)
        spanString.setSpan(
            colorSpan,
            valueString.length,
            spanString.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        // 字体加粗
        val styleSpan = StyleSpan(Typeface.BOLD)
        spanString.setSpan(styleSpan, 0, valueString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spanString
    }

    fun setBottomAlignment(valueString: String, unitString: String, valueSize:Int): SpannableStringBuilder {
        val spanString = SpannableStringBuilder(valueString + unitString)
        //绝对尺寸
        val absoluteSizeSpan = AbsoluteSizeSpan(2*valueSize)
        spanString.setSpan(
            absoluteSizeSpan,
            0,
            valueString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spanString
    }
}