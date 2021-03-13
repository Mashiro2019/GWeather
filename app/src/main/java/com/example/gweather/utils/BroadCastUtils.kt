package com.example.gweather.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter

/**
 * 广播工具类
 */
object BroadCastUtils {
    /**
     * 蓝牙广播
     * @return IntentFilter
     */
    fun registerBluetoothReceiver():IntentFilter {
        return IntentFilter().apply {
            //开始查找
            addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
            //结束查找
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            //查找设备
            addAction(BluetoothDevice.ACTION_FOUND)
            //设备扫描模式改变
            addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)
            //绑定状态
            addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        }
    }
}