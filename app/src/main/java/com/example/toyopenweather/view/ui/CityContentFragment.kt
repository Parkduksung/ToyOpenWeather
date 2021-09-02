package com.example.toyopenweather.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.toyopenweather.R
import com.example.toyopenweather.base.BaseFragment
import com.example.toyopenweather.databinding.FragmentCityContentBinding
import com.example.toyopenweather.view.adapter.CityAdapter
import com.example.toyopenweather.viewmodel.HomeViewModel

class CityContentFragment :
    BaseFragment<FragmentCityContentBinding>(R.layout.fragment_city_content) {

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

        cityAdapter.setOnItemClickListener { cityItem ->
            homeViewModel.routeDetail(cityItem.id)
        }

        homeViewModel.viewStateLiveData.observe(requireActivity()) { viewState ->
            when (viewState) {
                is HomeViewModel.HomeViewState.GetCityList -> {
                    cityAdapter.addAll(viewState.cityList)
                }

                is HomeViewModel.HomeViewState.ErrorGetCityList -> {
                    Toast.makeText(requireContext(), "Not Load Asset File...", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

}