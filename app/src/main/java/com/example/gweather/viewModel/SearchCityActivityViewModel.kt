package com.example.gweather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gweather.network.Repository

class SearchCityActivityViewModel:ViewModel() {

    private val requestCityList = MutableLiveData<Boolean>()

    val cityList = Transformations.switchMap(requestCityList){
        Repository.searchCity()
    }

    fun setRequestCityList(boolean: Boolean){
        requestCityList.postValue(boolean)
    }
}