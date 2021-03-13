package com.example.gweather.utils

import android.annotation.SuppressLint
import android.util.Log
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

/**
 * 高德地图工具类
 */
object AmapUtils {
    private lateinit var mLocationOption:AMapLocationClientOption
    @SuppressLint("StaticFieldLeak")
    private lateinit var mLocationClient:AMapLocationClient

    //经纬度信息
    private var latitude:Float = 0f //获取纬度
    private var longitude:Float = 0f //获取经度
    //获取位置描述
    private var address:String = ""

    //https://docs.seniverse.com/api/start/common.html#%E5%9C%B0%E7%82%B9-location
    /**
     * 将请求到的数据转换成Api location中要求格式
     * 详情参照上方链接
     * @return String
     */
    fun getLocation(): String {
        /**
         * 设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
         */
        mLocationClient.apply {
            stopLocation()
            startLocation()
        }
        return String.format("%.2f",latitude)+":"+String.format("%.2f",longitude)
    }

    /**
     * 获取当前地理位置描述
     * @return String
     */
    fun getAddress() = address

    /**
     * 启动定位客户端，同时启动本地定位服务。
     */
    fun startClient(){
        //声明AMapLocationClientOption对象并初始化
        mLocationOption = AMapLocationClientOption().apply {
            /**
             * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
             */
            locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
            /**
             * 设置单次
             */
            isOnceLocation = true
            /**
             * 设置精度
             */
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        }
        //声明AMapLocationClient类对象 初始化定位
        mLocationClient = AMapLocationClient(AppUtils.context).apply {
            //设置定位回调监听
            setLocationListener(mAMapLocationListener)
            setLocationOption(mLocationOption)
            startLocation()
        }
    }

    /**
     * 销毁定位客户端，同时销毁本地定位服务。
     */
    fun destroyClient(){
        mLocationClient.onDestroy();
    }

    /**
     * 接收数据并解析
     */
    private val mAMapLocationListener = AMapLocationListener { amapLocation->
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                //https://lbs.amap.com/api/android-location-sdk/guide/android-location/getlocation
                /**
                 * 可在其中解析AmapLocation获取相应内容。
                 * 更多参数可以参考上方链接
                 */
                latitude = amapLocation.latitude.toFloat()//获取纬度
                longitude = amapLocation.longitude.toFloat()//获取经度
                address = amapLocation.address.toString()
                Log.d("AmapSuccess", "$latitude:$longitude")
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e(
                    "AmapError", "location Error, ErrCode:" + amapLocation.errorCode + ", errInfo:" + amapLocation.errorInfo
                )
            }
        }
    }
}