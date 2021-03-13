package com.example.gweather.activity

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.gweather.R
import com.example.gweather.databinding.ActivitySignInBinding
import com.example.gweather.utils.AppUtils
import com.example.gweather.utils.EncryptionUtils
import com.example.gweather.viewModel.SignInActivityViewModel

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding

    private val myViewModel by lazy {
        ViewModelProvider(this)[SignInActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_sign_in
        )

        binding.signIn.setOnClickListener {
            //不直接获取明文字符串
            if(binding.usernameEditText.text.toString().trim()==""||binding.passwordEditText.text.toString().trim()==""){
                val intent = Intent("com.example.takisukaze.FORCE_OFFLINE")
                sendBroadcast(intent)
            }
            else{
                val userName:String = EncryptionUtils.encode(
                    binding.usernameEditText.text.toString().trim()
                )
                val userPass:String = EncryptionUtils.encode(
                    binding.passwordEditText.text.toString().trim()
                )
                myViewModel.userLogin(userName, userPass)
            }
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(AppUtils.context,SignUpActivity::class.java)
            startActivity(intent)
        }

        myViewModel.loginLiveData.observe(this) { result ->
            val userList = result.getOrNull()
            //如果结果为空则强制重启
            if (userList != null) {
                val intent = Intent(AppUtils.context, HomeActivity::class.java)
                intent.putExtra("userData", userList[0])
                startActivity(intent)
            } else {
                val intent = Intent("com.example.takisukaze.FORCE_OFFLINE")
                sendBroadcast(intent)
            }
        }
    }
}