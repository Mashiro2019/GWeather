package com.gcode.gweather.activity

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.gweather.R
import com.example.gweather.databinding.ActivityHomeBinding
import com.gcode.gutils.utils.MsgWindowUtils
import com.gcode.gweather.adapter.FragmentAdapter
import com.gcode.gweather.fragment.CityFragment
import com.gcode.gweather.fragment.DataFragment
import com.gcode.gweather.fragment.HomeFragment
import com.gcode.gweather.utils.AmapUtils
import com.gcode.gweather.viewModel.HomeActivityViewModel
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.launch
import nl.joery.animatedbottombar.AnimatedBottomBar


class HomeActivity : BaseActivity() {
    companion object {
        const val GPS_SETTING = 100
    }

    private lateinit var binding: ActivityHomeBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeActivityViewModel::class.java]
    }

    private val fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    Log.i("HomeActivity", "All permissions are granted")
                } else {
                    MsgWindowUtils.showShortMsg(this, "These permissions are denied: $deniedList")
                }
            }


        AmapUtils.startClient()

        initUI()

        isGpsOPen()

        viewModel.currentPage.observe(this) {
            binding.viewPager.currentItem = it
            binding.bottomNavView.selectTabAt(it)
        }

        val adapter = FragmentAdapter(fragments, supportFragmentManager)
        binding.viewPager.apply {
            this.adapter = adapter
            /**
             * view_pager滑动事件
             */
            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
                override fun onPageSelected(i: Int) {
                    // Selecting a tab at a specific position
                    binding.bottomNavView.selectTabAt(i)
                }

                override fun onPageScrollStateChanged(i: Int) {}
            })
        }

        binding.bottomNavView.setOnTabSelectListener(object :
            AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                binding.viewPager.currentItem = newIndex
            }

            // An optional method that will be fired whenever an already selected tab has been selected again.
            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                binding.viewPager.currentItem = index
            }
        })
    }

    private fun initUI() {

        fragments.apply {
            add(HomeFragment())
            add(DataFragment())
            add(CityFragment())
        }

        //设置顶部栏
        setSupportActionBar(binding.homeToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.spin_list -> {
                viewModel.spin.value?.let { viewModel.spinList(!it) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        AmapUtils.destroyClient()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_SETTING -> {
                isGpsOPen()
            }
        }
    }

    private fun isGpsOPen() {
        //判断GPS是否打开
        if (AmapUtils.isOPen(this)) {
            Log.i("HomeActivity", "定位已经打开")
            var location: String
            lifecycleScope.launch {
                location = AmapUtils.getLocation()
                viewModel.searchPlaces(location)
                viewModel.setGpsStatus(true)
            }
        } else {
            viewModel.setGpsStatus(false)
            Log.i("HomeActivity", "定位未打开")
            AlertDialog.Builder(this)
                .setTitle("提示消息")
                .setMessage("定位未打开,请前往设置界面打开")
                .setPositiveButton(
                    "确定"
                ) { _, _ ->
                    val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivityForResult(settingsIntent, GPS_SETTING)
                }
                .setNegativeButton("取消", null)
                .create().show()
        }
    }
}