package com.example.gweather.model

import com.example.gweather.R
import com.example.gweather.adapter.BindingAdapterItem

/**
 * 返回Json数据格式
 * @property status String
 * @property results List<User>
 * @constructor
 */
data class CityResponse(val results:List<City>,val status:String)

class City(
    val city_id: String,
    val administrative_attribution:String?,
    val city_abbreviation: String?,
    val pinyin: String?
):BindingAdapterItem{
    override fun getViewType(): Int {
        return R.layout.item_city
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