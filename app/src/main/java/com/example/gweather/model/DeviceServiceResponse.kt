package com.example.gweather.model

data class SearchDeviceResult(val list:List<DeviceResponse>, val status:String)

data class ChangeConnectStatusResult(val status: String)