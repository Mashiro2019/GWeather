package com.example.gweather.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.gweather.R
import com.example.gweather.databinding.ActivitySignUpBinding
import com.example.gweather.utils.AppUtils
import com.example.gweather.utils.PopupWindowUtils
import com.example.gweather.viewModel.RegisterActivityViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private val myViewModel by lazy {
        ViewModelProvider(this)[RegisterActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        binding.createAccountButton.setOnClickListener {
            //获取信息
            myViewModel.createAccount(
                binding.usernameEditText.text.toString().trim(),
                binding.passwordEditText.text.toString().trim(),
                binding.telEditText.text.toString().trim(),
                binding.emailEditText.text.toString().trim()
            )
        }

        //设置创建用户指令
        myViewModel.resultLiveData.observe(this, { result->
            val res = result.getOrNull()
            if(res.equals("TRUE")){
                PopupWindowUtils.showShortMsg(AppUtils.context,"注册成功")
            }else{
                PopupWindowUtils.showShortMsg(AppUtils.context,"注册失败")
            }
        })
    }

}