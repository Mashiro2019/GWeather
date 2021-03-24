package com.example.gweather.model

import com.example.gweather.adapter.BindingAdapterItem

/**
 * 城市对象
 * @property city_id String 城市Id
 * @property administrative_attribution String? 行政区划
 * @property city_abbreviation String? 城市简称
 * @property pinyin String? 城市拼音
 * @constructor
 */
class City(
    val city_id: String,
    val administrative_attribution:String?,
    val city_abbreviation: String?,
    val pinyin: String?
):BindingAdapterItem{
    override fun getViewType(): Int {
        return 0
    }
}

/**
 * 操作结果
 * @property status String
 * @constructor
 */
data class ResultResponse(
    val status:String
)