package com.example.gweather.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.gweather.BR
import java.io.Serializable

/**
 *作者:created by Entropy on 2020/12/30 13:46
 *邮箱:sakurajimamai2020@qq.com
 */
class Device(devName: String,
             deviceConnectStatus: String,
             deviceStatus: Boolean) :
    BaseObservable(), Serializable{

    //设备名称
    @get:Bindable
    @set:Bindable
    var devName: String = devName
        set(value) {
            field = value //更新支持字段的值
            notifyPropertyChanged(BR.devName)
        }

    //设备链接状态 未连接 已连接
    @get:Bindable
    @set:Bindable
    var deviceConnectStatus: String = deviceConnectStatus
        set(value) {
            field = value //更新支持字段的值
            notifyPropertyChanged(BR.deviceStatus)
        }

    //设备状态 开启 关闭
    @get:Bindable
    @set:Bindable
    var deviceStatus: Boolean = deviceStatus
        set(value) {
            field = value //更新支持字段的值
            notifyPropertyChanged(BR.deviceStatus)
        }
}