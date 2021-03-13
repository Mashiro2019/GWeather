package com.example.gweather.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.gweather.Constant
import com.example.gweather.R
import com.example.gweather.activity.AddCityActivity
import com.example.gweather.activity.SearchCityActivity
import com.example.gweather.databinding.CityFragmentBinding
import com.example.gweather.viewModel.WeatherDataViewModel

class CityFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[WeatherDataViewModel::class.java]
    }

    private lateinit var binding:CityFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.city_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //设置设备搜索
        binding.searchCity.setOnClickListener {
            val intent = Intent(activity, SearchCityActivity::class.java)
            startActivityForResult(intent,Constant.SEARCH_ACTIVITY)
        }
        //设置设备管理
        binding.addCity.setOnClickListener {
            val intent = Intent(activity, AddCityActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Constant.RESULT_OK){
            when(requestCode){
                Constant.SEARCH_ACTIVITY -> {
                    if (data != null) {
                        data.getStringExtra("city_id")?.let { viewModel.searchPlaces(it) }
                        viewModel.setCurrentPage(1)
                    }
                }
            }
        }
    }
}