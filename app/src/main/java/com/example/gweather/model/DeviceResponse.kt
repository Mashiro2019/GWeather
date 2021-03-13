package com.example.gweather.model

/**
 * 返回设备信息
 * @property devAngle Int
 * @property devAutoRainClose Int
 * @property devConnectStatus String
 * @property devDirection String
 * @property devName String
 * @property devNo Int
 * @property devRoomName String
 * @property devStatus Int
 * @constructor
 */
data class DeviceResponse(
    val devAngle:Int,
    val devAutoRainClose:Int,
    val devConnectStatus:String,
    val devDirection:String,
    val devName:String,
    val devNo:Int,
    val devRoomName:String,
    val devStatus:Int)