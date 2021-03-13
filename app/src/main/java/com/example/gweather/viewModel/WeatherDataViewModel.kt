package com.example.gweather.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gweather.model.AQDailyAirQuality
import com.example.gweather.network.Repository
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType


class WeatherDataViewModel : ViewModel() {

    companion object{
        //请求天数
        private const val DAYS:Int = 5
    }

    //chartModel 刷新数据
    private val _chartModelUpdateSeriesArray = MutableLiveData<Array<AASeriesElement>>()

    val chartModelUpdateSeriesArray:LiveData<Array<AASeriesElement>>
        get() = _chartModelUpdateSeriesArray

    private val _chartModel : AAChartModel = AAChartModel()
        .chartType(AAChartType.Spline)
        .title("未来五天的空气质量数据")
        .subtitle("aqi与pm25数据")
        .yAxisTitle("数值")
        .xAxisLabelsEnabled(false)
        .backgroundColor("#FFFFFF")
        .dataLabelsEnabled(true)
        .series(arrayOf(
            AASeriesElement()
                .name("空气质量指数")
                .data(arrayOf(0.0, 0.0, 0.0, 0.0, 0.0)),
            AASeriesElement()
                .name("pm2.5")
                .data(arrayOf(0.0, 0.0, 0.0, 0.0, 0.0))
        ))

    /**
     * 将_chartModel暴露给外部
     */
    val chartModel:AAChartModel
        get() = _chartModel

    /**
     * location是请求天气等数据的核心
     */
    private val _location = MutableLiveData<String>()

    private val _temperature = MutableLiveData<Float>()
    private val _feelslike = MutableLiveData<Int>()
    private val _humidity = MutableLiveData<Float>()
    private val _windDirection = MutableLiveData<String>()
    private val _windDirectionDegree = MutableLiveData<Int>()
    private val _windSpeed = MutableLiveData<Float>()
    private val _weather = MutableLiveData<String>()
    private val _visibility = MutableLiveData<Float>()
    private val _aqDailyDataList = MutableLiveData<List<AQDailyAirQuality>>() //天气数据

    /**
     * 只暴露不可变LiveData给外部
     */
    val temperature:LiveData<Float>
        get() = _temperature

    val feelslike:LiveData<Int>
        get() = _feelslike

    val humidity:LiveData<Float>
        get() = _humidity

    val windDirection:LiveData<String>
        get() = _windDirection

    val windDirectiondegree:LiveData<Int>
        get() = _windDirectionDegree

    val windSpeed:LiveData<Float>
        get() = _windSpeed

    val weather:LiveData<String>
        get() = _weather

    val visibility:LiveData<Float>
        get() = _visibility

    /**
     * 初始化
     */
    init{
        _temperature.value = 0f
        _feelslike.value = 0
        _humidity.value = 0f
        _windDirection.value = ""
        _windDirectionDegree.value = 0
        _windSpeed.value = 0f
        _weather.value = ""
        _visibility.value = 0f
        _aqDailyDataList.value = ArrayList()
    }

    /**
     * 刷新天气数据
     * @param temperatureValue Float 温度，单位为c摄氏度或f华氏度
     * @param feelslikeValue Int 体感温度，单位为c摄氏度或f华氏度
     * @param humidityValue Float 相对湿度，0~100，单位为百分比
     * @param windSpeedValue Float 风速，单位为km/h公里每小时或mph英里每小时
     * @param windDirectionDegree Float 风向角度，范围0~360，0为正北，90为正东，180为正南，270为正西
     * @param weather String
     * @param visibility Float 能见度，单位为km公里或mi英里
     */
    fun updateWeatherData(
        temperatureValue: Float,
        feelslikeValue: Int,
        humidityValue: Float,
        windDirectionValue:String,
        windSpeedValue: Float,
        windDirectionDegree: Float,
        weather: String,
        visibility: Float
    ){
        /**
         * postValue保证在任何线程都可以调用
         */
        _temperature.postValue(temperatureValue)
        _feelslike.postValue(feelslikeValue)
        _humidity.postValue(humidityValue)
        _windDirection.postValue(windDirectionValue)
        _windSpeed.postValue(windSpeedValue)
        _windDirectionDegree.postValue(windDirectionDegree.toInt())
        _weather.postValue(weather)
        _visibility.postValue(visibility)
    }

    /**
     * 刷新天气数据
     * @param daily MutableList<AQDailyAirQuality>
     */
    fun updateAirQualityData(daily:List<AQDailyAirQuality>){
        _chartModelUpdateSeriesArray.postValue(arrayOf(
            AASeriesElement()
                .name("空气质量指数")
                .data(arrayOf(daily[0].aqi.toFloat(), daily[1].aqi.toFloat(), daily[2].aqi.toFloat(), daily[3].aqi.toFloat(), daily[4].aqi.toFloat())),
            AASeriesElement()
                .name("pm2.5")
                .data(arrayOf(daily[0].pm25.toFloat(), daily[1].pm25.toFloat(), daily[2].pm25.toFloat(), daily[3].pm25.toFloat(), daily[4].pm25.toFloat()))
        ))
    }

    val placeLiveData = Transformations.switchMap(_location){location->
        Repository.searchPlaceWeather(location)
    }

    val dailyAirQualityResult = Transformations.switchMap(_location){location->
        Repository.searchAirQuality(location,DAYS)
    }

    val dailyWeatherResult = Transformations.switchMap(_location){location->
        Repository.searchDailyWeather(location)
    }

    fun searchPlaces(location:String){
        _location.value = location
    }

    val currentPage = MutableLiveData<Int>()

    fun setCurrentPage(index:Int){
        currentPage.postValue(index)
    }
}