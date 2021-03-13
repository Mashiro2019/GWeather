package com.example.gweather

import com.example.gweather.adapter.BindingAdapterItem
import com.example.gweather.model.City


/**
 *作者:created by HP on 2021/3/8 18:11
 *邮箱:sakurajimamai2020@qq.com
 */
object SearchCity {
    fun searchSongByCityName(
        cities: List<BindingAdapterItem>,
        searchName: String
    ): List<BindingAdapterItem> {
        val searchResult: MutableList<BindingAdapterItem> = ArrayList()
        for (bean in cities) {
            val item: City = bean as City
            if (item.city_abbreviation?.contains(searchName) == true) {
                searchResult.add(bean)
            }
        }
        return searchResult
    }
}