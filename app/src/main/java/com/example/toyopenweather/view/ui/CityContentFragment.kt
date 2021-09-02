package com.example.toyopenweather.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.toyopenweather.R
import com.example.toyopenweather.base.BaseFragment
import com.example.toyopenweather.databinding.FragmentCityContentBinding
import com.example.toyopenweather.view.adapter.CityAdapter
import com.example.toyopenweather.viewmodel.HomeViewModel

class CityContentFragment :
    BaseFragment<FragmentCityContentBinding>(R.layout.fragment_city_content) {

    private val cityAdapter by lazy { CityAdapter() }

    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initViewModel()

    }

    private fun initUi() {
        binding.rvCity.run {
            adapter = cityAdapter
        }

        cityAdapter.setOnItemClickListener { cityItem ->
            homeViewModel.routeDetail(cityItem.id)
        }
    }

    private fun initViewModel() {
        homeViewModel.getCityList()

        homeViewModel.viewStateLiveData.observe(requireActivity()) { viewState ->
            when (viewState) {
                is HomeViewModel.HomeViewState.GetCityList -> {
                    cityAdapter.addAll(viewState.cityList)
                }

                is HomeViewModel.HomeViewState.ErrorGetCityList -> {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.error_get_city_list),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

}