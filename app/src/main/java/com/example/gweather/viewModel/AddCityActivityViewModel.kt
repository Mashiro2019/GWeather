package com.example.gweather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gweather.model.City
import com.example.gweather.network.Repository

class AddCityActivityViewModel : ViewModel() {

    private val cityCode = MutableLiveData<String>()

    private val city = MutableLiveData<City>()

    val cityListJson = Transformations.switchMap(cityCode){city->
        Repository.searchPlace(city)
    }

    val result = Transformations.switchMap(city){city->
        Repository.addCity(city)
    }

    /**
     * 根据Code搜索城市
     * @param cityCode String
     */
    fun searchCity(
        cityCode: String,
    ) {
        this.cityCode.postValue(cityCode)
    }

    /**
     * 添加城市
     * @param city_id String
     * @param administrative_attribution String?
     * @param city_abbreviation String?
     * @param pinyin String?
     */
    fun addCity(
        city_id: String,
        administrative_attribution:String?,
        city_abbreviation: String?,
        pinyin: String?
    ) {
        city.postValue(City(city_id, administrative_attribution, city_abbreviation, pinyin))
    }
}