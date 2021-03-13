package com.example.gweather.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *作者:created by HP on 2021/3/11 14:45
 *邮箱:sakurajimamai2020@qq.com
 */
/**
 * 返回Json数据格式
 * @property status String
 * @property results List<User>
 * @constructor
 */
data class UserResponse(val results:List<User>,val status:String)

/**
 * 用户信息数据类
 * @property email String
 * @property tel String
 * @property username String
 * @constructor
 */
data class User(
    @SerializedName("Email") val email:String,
    @SerializedName("Tel") val tel:String,
    @SerializedName("Username") val username:String
): Serializable

data class Account(
    val username: String,
    val password:String,
    val tel: String?,
    val email: String?
)