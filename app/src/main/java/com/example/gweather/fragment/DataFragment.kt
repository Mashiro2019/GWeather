package com.example.gweather.fragment

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.gweather.R
import com.example.gweather.databinding.DataFragmentBinding
import com.example.gweather.utils.*
import com.example.gweather.viewModel.HomeActivityViewModel
import com.scwang.smart.refresh.header.BezierRadarHeader
import kotlinx.coroutines.launch


class DataFragment : Fragment() {

    private lateinit var binding:DataFragmentBinding

    //获取ViewModel
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[HomeActivityViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.data_fragment,
            container,
            false
        )
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /**
         * 初始化界面
         */
        initUI()

        initClickListener()

        /**
         * 更新数据
         */
        viewModel.apply {
            placeLiveData.observe(viewLifecycleOwner){ result ->
                //places 请求到的数据
                val places = result.getOrNull()
                if (places != null) {
                    //更新ViewModel内数据
                    places[0].now.apply {
                        viewModel.updateWeatherData(
                            temperature.toFloat(),
                            feelLike.toInt(),
                            humidity.toFloat(),
                            windDirection,
                            windSpeed.toFloat(),
                            text,
                            visibility.toFloat()
                        )
                    }
                    Log.d("updateWeather success", "数据更新完成")
                } else {
                    PopupWindowUtils.showShortMsg(requireContext(), "查询数据错误")
                    result.exceptionOrNull()?.printStackTrace()
                }
            }

            dailyAirQualityResult.observe(viewLifecycleOwner){AQResponse->
                val result = AQResponse.getOrNull()
                if(null!=result){
                    viewModel.updateAirQualityData(result.results[0].daily)
                }
            }

            temperature.observe(viewLifecycleOwner, { temperatureValue ->
                binding.temperatureValue.text = "$temperatureValue°"
            })

            viewModel.feelslike.observe(viewLifecycleOwner){feelslike->
                binding.feelsLikeValue.text = "体感温度 $feelslike°"
            }

            visibility.observe(viewLifecycleOwner){visibilityValue->
                binding.visibilityValue.text = ViewUtils.setBottomAlignment(visibilityValue.toString(),
                    "km",
                    binding.visibilityValue.textSize.toInt()
                )
            }

            humidity.observe(viewLifecycleOwner, { humidityValue->
                binding.humidityValue.text = ViewUtils.setBottomAlignment(humidityValue.toString(),
                    "%",
                    binding.humidityValue.textSize.toInt()
                )
            })

            windSpeed.observe(viewLifecycleOwner, { windSpeedValue ->
                binding.windSpeedValue.text = ViewUtils.setBottomAlignment(windSpeedValue.toString(),
                    "km/h",
                    binding.windSpeedValue.textSize.toInt()
                )
            })

            /**
             * 更新天气文字描述
             */
            weather.observe(viewLifecycleOwner, { weather ->
                when(weather){
                    "晴"->{
                        binding.apply {
                            weatherValue.text = "sun $weather"
                            weatherIcon.setImageResource(R.drawable.sun)
                        }
                    }
                    "多云"->
                    {
                        binding.apply {
                            weatherValue.text = "cloudy $weather"
                            weatherIcon.setImageResource(R.drawable.partly_cloudy)
                        }
                    }
                    "阵雨"->
                    {
                        binding.apply {
                            weatherValue.text = "showers $weather"
                            weatherIcon.setImageResource(R.drawable.light_rain)
                        }
                    }
                    "阴"->
                    {
                        binding.apply {
                            weatherValue.text = "cloudy $weather"
                            weatherIcon.setImageResource(R.drawable.partly_cloudy)
                        }
                    }
                }
            })

            chartModelUpdateSeriesArray.observe(viewLifecycleOwner){seriesArray->
                binding.airQualityChartView.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(seriesArray)
            }
        }
    }

    private fun initClickListener() {
        binding.apply {
            refreshLayout.setOnLoadMoreListener { refreshLayout ->
                var location: String
                lifecycleScope.launch {
                    location = AmapUtils.getLocation()
                    viewModel.searchPlaces(location)
                }
                refreshLayout.finishLoadMore(1500 /*,false*/) //传入false表示加载失败
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.R)
    private fun initUI() {
        binding.apply {
            airQualityChartView.aa_drawChartWithChartOptions(viewModel.aqiChart())

            //获取屏幕高度
            val screenHeight = DeviceInfUtils.getMobileScreenHeight(AppUtils.context)
            val screenWidth = DeviceInfUtils.getMobileScreenWidth(AppUtils.context)

            firstDataCardView.minimumHeight = screenHeight/20

            //设置温度字体样式
            temperatureValue.apply {
                textSize = screenWidth/20.toFloat()
                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            }
            weatherIcon.setImageResource(R.drawable.sun) //默认晴天
            //设置天气字体大小
            weatherValue.apply {
                textSize = screenWidth/50.toFloat()
                weatherValue.text = "sun 晴"
            }
            //设置体表温度字体样式
            feelsLikeValue.textSize = screenWidth/60.toFloat()
            //设置能见度字体样式
            visibilityValue.apply {
                textSize = screenWidth/80.toFloat()
                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            }
            //设置湿度字体样式
            humidityValue.apply {
                textSize = screenWidth/80.toFloat()
                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            }
            windSpeedValue.apply {
                textSize = screenWidth/80.toFloat()
                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            }

            //设置 Header 为 贝塞尔雷达 样式
            refreshLayout.setEnableLoadMore(false)
            //设置 Footer 为 球脉冲 样式
            refreshLayout.setRefreshHeader(
                BezierRadarHeader(binding.root.context).setEnableHorizontalDrag(true)
            )
        }
    }
}