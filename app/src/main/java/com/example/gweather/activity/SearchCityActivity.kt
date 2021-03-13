package com.example.gweather.activity

import android.content.Intent
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
import com.example.gweather.Constant
import com.example.gweather.R
import com.example.gweather.SearchCity
import com.example.gweather.adapter.BindingAdapterItem
import com.example.gweather.adapter.MyBindingAdapter
import com.example.gweather.databinding.ActivitySearchCityBinding
import com.example.gweather.model.City
import com.example.gweather.viewModel.SearchCityActivityViewModel


class SearchCityActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE = 0
    }

    private lateinit var binding:ActivitySearchCityBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[SearchCityActivityViewModel::class.java]
    }

    private val cityList:MutableList<BindingAdapterItem> = ArrayList()
    private val adapter = MyBindingAdapter(cityList)
    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_city)

        //请求数据库中数据
        viewModel.setRequestCityList(true)

        //初始化toolbar
        setSupportActionBar(binding.controlToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        adapter.setItemClick(object : MyBindingAdapter.ItemClick {
            override fun onItemClickListener(position: Int) {
                val city = cityList[position] as City
                val cityID = city.city_id
                val intent = Intent()
                intent.putExtra("city_id", cityID)
                setResult(Constant.RESULT_OK,intent)
                finish()
            }
        })

        binding.cityList.adapter = adapter
        binding.cityList.layoutManager = layoutManager

        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val cities = query?.let { SearchCity.searchSongByCityName(cityList, it) }
                cityList.clear()
                if (cities != null) {
                    cityList.addAll(cities)
                }
                adapter.notifyDataSetChanged()
                return false
            }

            @RequiresApi(api = Build.VERSION_CODES.Q)
            override fun onQueryTextChange(newText: String?): Boolean {
                if (cityList.size < 1 || cityList.isEmpty()) {
                    return false
                }
                return false
            }
        })

        viewModel.cityList.observe(this){ result->
            val cityListRes = result.getOrNull()
            if (cityListRes!=null){
                cityList.apply {
                    for(item in cityListRes){
                        add(item)
                    }
                }
            }
            adapter.notifyDataSetChanged()
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