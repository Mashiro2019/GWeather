package com.example.gweather.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.gweather.R
import com.example.gweather.databinding.ActivityStartBinding
import com.example.gweather.utils.AmapUtils

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_start)

        AmapUtils.startClient()

        //initView()

        binding.signIn.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    //    private fun initView() {
//        binding.videoView.apply {
//            //设置播放加载路径
//            setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.test2))
//            //播放
//            start()
//            //循环播放
//            setOnCompletionListener { binding.videoView.start() }
//        }
//    }
//
//    //返回重启加载
//    override fun onRestart() {
//        initView()
//        super.onRestart()
//    }
//
//    //防止锁屏或者切出的时候，音乐在播放
//    override fun onStop() {
//        binding.videoView.stopPlayback()
//        super.onStop()
//    }
}