package com.example.toyopenweather.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.toyopenweather.R
import com.example.toyopenweather.view.adapter.CityAdapter
import com.example.toyopenweather.viewmodel.HomeViewModel

class CityContentFragment : Fragment(R.layout.fragment_city_content) {

    private val cityAdapter by lazy { CityAdapter() }

    private val rvCity: RecyclerView by lazy {
        requireActivity().findViewById(R.id.rv_city)
    }

    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getCityList()

        rvCity.run {
            adapter = cityAdapter
        }

        cityAdapter.setOnItemClickListener { item ->
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_home, CityDetailFragment.newInstance(item.id))
                .commit()
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