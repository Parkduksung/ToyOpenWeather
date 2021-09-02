package com.example.toyopenweather.view.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.toyopenweather.R
import com.example.toyopenweather.api.WeatherApi
import com.example.toyopenweather.data.repo.CityRepositoryImpl
import com.example.toyopenweather.data.repo.WeatherRepositoryImpl
import com.example.toyopenweather.data.source.local.CityLocalDataSourceImpl
import com.example.toyopenweather.data.source.remote.WeatherRemoteDataSourceImpl
import com.example.toyopenweather.util.CityImpl
import com.example.toyopenweather.view.adapter.CityAdapter
import com.example.toyopenweather.viewmodel.HomeViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CityContentFragment : Fragment(R.layout.fragment_city_content) {

    private val cityAdapter by lazy { CityAdapter() }

    private val rvCity: RecyclerView by lazy {
        requireActivity().findViewById(R.id.rv_city)
    }

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getCityList()

        rvCity.run {
            adapter = cityAdapter
        }

        homeViewModel.viewStateLiveData.observe(requireActivity()) { homeViewState ->
            when (homeViewState) {
                is HomeViewModel.HomeViewState.GetCityList -> {
                    cityAdapter.addAll(homeViewState.cityList)
                }
            }
        }


    }

}