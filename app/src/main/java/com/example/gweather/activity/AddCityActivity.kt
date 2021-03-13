package com.example.gweather.activity

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gweather.R
import com.example.gweather.adapter.BindingAdapterItem
import com.example.gweather.adapter.MyBindingAdapter
import com.example.gweather.databinding.ActivityAddCityBinding
import com.example.gweather.model.PlaceInf
import com.example.gweather.utils.PopupWindowUtils
import com.example.gweather.viewModel.AddCityActivityViewModel


class AddCityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCityBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[AddCityActivityViewModel::class.java]
    }

    private val cityList:MutableList<BindingAdapterItem> = ArrayList()
    private val adapter = MyBindingAdapter(cityList)
    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_city)

        //初始化toolbar
        setSupportActionBar(binding.controlToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchCity(it) }
                return false
            }

            @RequiresApi(api = Build.VERSION_CODES.Q)
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        adapter.setItemClick(object : MyBindingAdapter.ItemClick {
            override fun onItemClickListener(position: Int) {
                AlertDialog.Builder(binding.root.context)
                    .setTitle("提示消息")
                    .setMessage("是否添加该城市")
                    .setPositiveButton("确定"
                    ) { _, _ ->
                        val city = cityList[position] as PlaceInf
                        //添加城市
                        viewModel.addCity(
                            city.id,
                            city.path,
                            city.name,
                            ""
                        )
                    }
                    .setNegativeButton("取消",null)
                    .create().show()
            }
        })
        binding.cityList.adapter = adapter
        binding.cityList.layoutManager = layoutManager

        viewModel.cityListJson.observe(this){result->
            val cities = result.getOrNull()
            if (cities != null){
                for(item in cities){
                    cityList.add(item)
                }
            }
            adapter.notifyDataSetChanged()
        }

        viewModel.result.observe(this){result->
            val res = result.getOrNull()
            if (res != null){
                if(res == "TRUE"){
                    PopupWindowUtils.showShortMsg(binding.root.context,"添加成功")
                }else{
                    PopupWindowUtils.showShortMsg(binding.root.context,"添加失败")
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_device_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}