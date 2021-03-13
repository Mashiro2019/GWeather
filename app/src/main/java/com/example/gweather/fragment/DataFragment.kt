package com.example.gweather.fragment

import android.annotation.SuppressLint
import android.graphics.Color
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
import app.futured.donut.DonutSection
import com.example.gweather.R
import com.example.gweather.databinding.DataFragmentBinding
import com.example.gweather.dialogfragment.WindDescribeFragment
import com.example.gweather.utils.*
import com.example.gweather.viewModel.WeatherDataViewModel
import com.scwang.smart.refresh.footer.BallPulseFooter
import com.scwang.smart.refresh.header.BezierRadarHeader
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import java.text.SimpleDateFormat
import java.util.*


class DataFragment : Fragment() {

    private lateinit var binding:DataFragmentBinding

    private val windDirectionDegreeAmount = DonutSection(
        name = "wind_direction_degree_amount",
        color = Color.parseColor("#3867d6"),
        amount = 0.1f
    )

    //获取ViewModel
    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[WeatherDataViewModel::class.java]
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
                            windDirectionDegree.toFloat(),
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

            windDirection.observe(viewLifecycleOwner,{windDirectionValue->
                binding.windDirectionValue.text = windDirectionValue
            })

            windSpeed.observe(viewLifecycleOwner, { windSpeedValue ->
                binding.windSpeedValue.text = ViewUtils.setBottomAlignment(windSpeedValue.toString(),
                    "km/h",
                    binding.windSpeedValue.textSize.toInt()
                )
            })

            windDirectiondegree.observe(viewLifecycleOwner){windDirectiondegreeValue->
                val windDirectionDegreeAmountOther = DonutSection(
                    name = "wind_direction_degree_amount",
                    color = Color.parseColor("#3867d6"),
                    amount = windDirectiondegreeValue.toFloat()
                )
                binding.windDirectionDegreeAmountDonut.cap = 360f
                binding.windDirectionDegreeAmountDonut.submitData(listOf(windDirectionDegreeAmountOther))

                binding.windDirectionDegreeValue.text = windDirectiondegreeValue.toString()
            }

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

    @SuppressLint("SimpleDateFormat")
    private fun initClickListener() {
        binding.apply {
            refreshLayout.setOnRefreshListener { refreshLayout ->
                viewModel.searchPlaces(AmapUtils.getLocation())
                binding.updateTimeTextView.text = String.format(
                    resources.getString(R.string.current_time),
                    SimpleDateFormat("yyyy").format(Date()).toInt(),
                    SimpleDateFormat("MM").format(Date()).toInt(),
                    SimpleDateFormat("dd").format(Date()).toInt(),
                    SimpleDateFormat("hh").format(Date()).toInt(),
                    SimpleDateFormat("mm").format(Date()).toInt(),
                    SimpleDateFormat("ss").format(Date()).toInt()
                )
                binding.updateAddressTextView.text = AmapUtils.getAddress()
                refreshLayout.finishRefresh(2000 /*,false*/) //传入false表示刷新失败
            }
            refreshLayout.setOnLoadMoreListener { refreshLayout ->
                viewModel.searchPlaces(AmapUtils.getLocation())
                binding.updateTimeTextView.text = String.format(
                    resources.getString(R.string.current_time),
                    SimpleDateFormat("yyyy").format(Date()).toInt(),
                    SimpleDateFormat("MM").format(Date()).toInt(),
                    SimpleDateFormat("dd").format(Date()).toInt(),
                    SimpleDateFormat("hh").format(Date()).toInt(),
                    SimpleDateFormat("mm").format(Date()).toInt(),
                    SimpleDateFormat("ss").format(Date()).toInt()
                )
                binding.updateAddressTextView.text = AmapUtils.getAddress()
                refreshLayout.finishLoadMore(2000 /*,false*/) //传入false表示加载失败
            }
            windInfCardView.setOnClickListener {
                //显示提示信息页面
                val dialog = WindDescribeFragment()
                dialog.show(childFragmentManager,"DataFragment")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.R)
    private fun initUI() {
        binding.apply {
            airQualityChartView.aa_drawChartWithChartModel(viewModel.chartModel)

            windDirectionDegreeAmountDonut.cap = 360f
            windDirectionDegreeAmountDonut.submitData(listOf(windDirectionDegreeAmount))
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
            windDirectionName.textSize = screenWidth/80.toFloat()
            windDirectionValue.apply {
                textSize = screenWidth/40.toFloat()
                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            }
            windDirectionDegreeName.textSize = screenWidth/80.toFloat()
            windDirectionDegreeValue.apply {
                textSize = screenWidth/40.toFloat()
                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            }

            //设置 Header 为 贝塞尔雷达 样式
            refreshLayout.setRefreshHeader(
                BezierRadarHeader(requireContext()).setEnableHorizontalDrag(
                    true
                )
            )
            //设置 Footer 为 球脉冲 样式
            refreshLayout.setRefreshFooter(
                BallPulseFooter(requireContext()).setSpinnerStyle(
                    SpinnerStyle.Translate
                )
            )
        }
    }

    /**
     * 初次打开程序时提前预加载数据
     */
    @SuppressLint("SimpleDateFormat")
    fun getSelectedMessage(){
        viewModel.searchPlaces(AmapUtils.getLocation())
        binding.updateTimeTextView.text = String.format(
            resources.getString(R.string.current_time),
            SimpleDateFormat("yyyy").format(Date()).toInt(),
            SimpleDateFormat("MM").format(Date()).toInt(),
            SimpleDateFormat("dd").format(Date()).toInt(),
            SimpleDateFormat("hh").format(Date()).toInt(),
            SimpleDateFormat("mm").format(Date()).toInt(),
            SimpleDateFormat("ss").format(Date()).toInt()
        )
        binding.updateAddressTextView.text = AmapUtils.getAddress()
    }
}