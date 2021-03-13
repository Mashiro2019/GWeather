package com.example.gweather.network

import android.util.Log
import androidx.lifecycle.liveData
import com.example.gweather.model.Account
import com.example.gweather.model.City
import kotlinx.coroutines.Dispatchers

object Repository {
    fun searchPlaceWeather(location:String) = liveData(Dispatchers.IO) {
        val result = try {
            val dataResponse = Network.searchPlaceWeather(location)
            if(dataResponse.results.isNotEmpty()){
                val place =  dataResponse.results
                Result.success(place)
            }else{
                Result.failure(RuntimeException("response data array is empty is ${dataResponse.results.isEmpty()}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }

    fun searchDailyWeather(location:String) = liveData(Dispatchers.IO) {
        val result = try {
            val dataResponse = Network.searchDailyWeather(location)
            if(dataResponse.results.isNotEmpty()){
                val place =  dataResponse.results
                Result.success(place)
            }else{
                Result.failure(RuntimeException("response data array is empty is ${dataResponse.results.isEmpty()}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }

    fun searchPlace(location:String) = liveData(Dispatchers.IO) {
        val result = try {
            val dataResponse = Network.searchPlace(location)
            if(dataResponse.results.isNotEmpty()){
                val place =  dataResponse.results
                Result.success(place)
            }else{
                Result.failure(RuntimeException("response data array is empty is ${dataResponse.results.isEmpty()}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }

    fun searchCity() = liveData(Dispatchers.IO){
        val result = try {
            val userResponse = Network.searchCity()
            if(userResponse.results.isNotEmpty()){
                val user =  userResponse.results
                Result.success(user)
            }else{
                Result.failure(RuntimeException("response data array is empty is ${userResponse.results.isEmpty()}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }

    fun addCity(city: City) = liveData(Dispatchers.IO){
        val result = try {
            val resultResponse = Network.addCity(city)
            if(resultResponse.status == "TRUE"){
                val result = resultResponse.status
                Result.success(result)
            }else{
                Result.failure(RuntimeException("response data is empty"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }

    /**
     * 搜索天气数据
     * @param location String 坐标
     * @param days Int 天数
     * @return LiveData<Result<AQResponse>>
     */
    fun searchAirQuality(location: String,days:Int) = liveData(Dispatchers.IO){
        val result = try {
            val resultResponse = Network.searchAirQuality(location,days)
            if(resultResponse.results.isNotEmpty()){
                Log.d("AQSuccess",resultResponse.results[0].lastUpdate)
                Result.success(resultResponse)
            }else{
                Log.d("AQError","searchAirQuality() response data is empty")
                Result.failure(RuntimeException("response data is empty"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }

    fun userLogin(username:String,password:String) = liveData(Dispatchers.IO){
        val result = try {
            val userResponse = Network.userLogin(username, password)
            if(userResponse.results.isNotEmpty()){
                val user =  userResponse.results
                Result.success(user)
            }else{
                Result.failure(RuntimeException("response data array is empty is ${userResponse.results.isEmpty()}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }

    fun createAccount(account: Account) = liveData(Dispatchers.IO){
        val result = try {
            val resultResponse = Network.createAccount(account)
            if(resultResponse.status == "TRUE"){
                val result = resultResponse.status
                Result.success(result)
            }else{
                Result.failure(RuntimeException("response data is empty"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }
}