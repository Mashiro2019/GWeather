package com.example.gweather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gweather.network.Repository

/**
 *作者:created by HP on 2021/3/11 14:37
 *邮箱:sakurajimamai2020@qq.com
 */
class SignInActivityViewModel:ViewModel() {
    /**
     * 用户信息缩略类
     * @property username String
     * @property password String
     * @constructor
     */
    private inner class TempUser(val username:String, val password:String)

    private val userLiveData = MutableLiveData<TempUser>()

    val loginLiveData = Transformations.switchMap(userLiveData){ Object->
        Repository.userLogin(Object.username,Object.password)
    }


    fun userLogin(username: String,password: String){
        userLiveData.postValue(TempUser(username, password))
    }
}