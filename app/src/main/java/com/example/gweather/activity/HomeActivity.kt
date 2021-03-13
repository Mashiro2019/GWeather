package com.example.gweather.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.gweather.R
import com.example.gweather.adapter.FragmentAdapter
import com.example.gweather.databinding.ActivityHomeBinding
import com.example.gweather.fragment.CityFragment
import com.example.gweather.fragment.DataFragment
import com.example.gweather.fragment.HomeFragment
import com.example.gweather.model.User
import com.example.gweather.utils.AmapUtils
import com.example.gweather.viewModel.WeatherDataViewModel
import nl.joery.animatedbottombar.AnimatedBottomBar


class HomeActivity : BaseActivity() {
    private lateinit var user: User

    private lateinit var binding: ActivityHomeBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[WeatherDataViewModel::class.java]
    }

    //添加主界面Fragment
    private val fragments = ArrayList<Fragment>()

    /**
     * 应用启动时初次刷新
     */
    private var isFirstUpdate: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)

        initUI()

        viewModel.searchPlaces(AmapUtils.getLocation())

        viewModel.currentPage.observe(this){
            binding.viewPager.currentItem = it
            binding.bottomNavView.selectTabAt(it)
        }

        val adapter = FragmentAdapter(fragments, supportFragmentManager)
        binding.viewPager.apply{
            this.adapter = adapter
            /**
             * view_pager滑动事件
             */
            addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
                override fun onPageSelected(i: Int) {
                    // Selecting a tab at a specific position
                    binding.bottomNavView.selectTabAt(i)
                    if (i == 1 && isFirstUpdate) {
                        val dataFragment: DataFragment =
                            fragments[i] as DataFragment
                        dataFragment.getSelectedMessage()
                        isFirstUpdate = false //设置为false
                    }
                }

                override fun onPageScrollStateChanged(i: Int) {}
            })
        }

        /**
         * 底部导航栏点击事件
         */
        binding.bottomNavView.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
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
                if (index == 1 && isFirstUpdate) {
                    val dataFragment: DataFragment =
                        fragments[index] as DataFragment
                    dataFragment.getSelectedMessage()
                    isFirstUpdate = false //设置为false
                }
            }
        })
    }

    private fun initUI() {

        user = intent.getSerializableExtra("userData") as User

        fragments.apply {
            add(HomeFragment())
            add(DataFragment())
            add(CityFragment())
        }

        //设置顶部栏
        setSupportActionBar(binding.homeToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.mipmap.title_bar)
        }

        //默认选中Mail
        binding.sidebarView.apply {
            //setCheckedItem(R.id.navMail)
            setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.navLocation->{
                        val dialog = AlertDialog.Builder(binding.sidebarView.context)
                        dialog.apply {
                            setTitle("当前位置")
                            setIcon(R.drawable.address_icon)
                            setMessage(AmapUtils.getAddress())
                            setPositiveButton("确定") { dialog, _ ->
                                dialog.cancel()
                            }
                        }
                        dialog.show()
                    }

                    R.id.navExit->{
                        //退出登录
                        val intent = Intent("com.example.takisukaze.SIGN_OUT")
                        sendBroadcast(intent)
                    }
                }
                true
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //打开侧边栏
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                //打开时赋值
                binding.sidebarView.findViewById<TextView>(R.id.userName).text = user.username
                binding.sidebarView.findViewById<TextView>(R.id.mailText).text = user.email
            }
        }
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        AmapUtils.destroyClient()
    }
}