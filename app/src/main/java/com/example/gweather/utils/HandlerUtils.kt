package com.example.gweather.utils

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.example.gweather.Constant

@SuppressLint("HandlerLeak")
class HandlerUtils : Handler(Looper.getMainLooper()) {
    override fun handleMessage(message: Message) {
        super.handleMessage(message)
        when (message.what) {
            Constant.MSG_GOT_DATA -> PopupWindowUtils.showShortMsg(AppUtils.context,"data_icon:" + message.obj.toString())
            Constant.MSG_ERROR -> PopupWindowUtils.showShortMsg(AppUtils.context,"error:" + message.obj.toString())
            Constant.MSG_CONNECTED_TO_SERVER -> PopupWindowUtils.showShortMsg(AppUtils.context,"连接到服务端")
            Constant.MSG_GOT_A_CLINET -> PopupWindowUtils.showShortMsg(AppUtils.context,"找到服务端")
        }
    }
}