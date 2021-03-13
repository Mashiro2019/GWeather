package com.example.gweather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gweather.model.Account
import com.example.gweather.network.Repository

/**
 *作者:created by HP on 2021/3/11 14:39
 *邮箱:sakurajimamai2020@qq.com
 */
class RegisterActivityViewModel : ViewModel() {

    private val item = MutableLiveData<Account>()

    val resultLiveData = Transformations.switchMap(item){ account->
        Repository.createAccount(account)
    }

    /**
     * 创建用户
     * @param username String
     * @param password String
     * @param tel String
     * @param email String
     */
    fun createAccount(
        username: String,
        password: String,
        tel: String?,
        email: String?
    ) {
        item.postValue(
            Account(username, password, tel, email)
        )
    }
}