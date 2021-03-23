package com.example.gweather

import java.io.Serializable

/**
 *作者:created by Entropy on 2021/1/16 15:09
 *邮箱:sakurajimamai2020@qq.com
 *给定状态参数常量
 */
object Constant : Serializable {
    const val CONNECTTION_UUID = "00001101-0000-1000-8000-00805F9B34FB"

    /**
     * 开始监听
     */
    const val MSG_START_LISTENING = 1

    /**
     * 结束监听
     */
    const val MSG_FINISH_LISTENING = 2

    /**
     * 有客户端连接
     */
    const val MSG_GOT_A_CLINET = 3

    /**
     * 连接到服务器
     */
    const val MSG_CONNECTED_TO_SERVER = 4

    /**
     * 获取到数据
     */
    const val MSG_GOT_DATA = 5

    /**
     * HomeActivity标识符
     */
    const val HOME_ACTIVITY_CODE = 1

    /**
     * SignInActivity标识符
     */
    const val SIGN_IN_ACTIVITY_CODE = 1

    /**
     * SignUpActivity标识符
     */
    const val SIGN_UP_ACTIVITY_CODE = 1

    /**
     * 出错
     */
    const val MSG_ERROR = -1

    /**
     * 数据传递使用
     */
    const val RESULT_OK = 1

    /**
     * 绑定布局
     */
    const val SEARCH_ACTIVITY = 500
}