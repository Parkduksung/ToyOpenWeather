package com.example.toyopenweather.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.toyopenweather.R
import com.example.toyopenweather.base.BaseFragment
import com.example.toyopenweather.base.ViewState
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

        binding.viewModel = homeViewModel

        homeViewModel.getCityList()

        homeViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState: ViewState? ->
            (viewState as? HomeViewModel.HomeViewState)?.let { onChangedHomeViewState(viewState) }
        }
    }

    private fun onChangedHomeViewState(viewState: HomeViewModel.HomeViewState) {
        when (viewState) {
            is HomeViewModel.HomeViewState.GetCityList -> {
                cityAdapter.addAll(viewState.cityList)
            }

            is HomeViewModel.HomeViewState.GetCityItem -> {
                Toast.makeText(requireContext(), viewState.cityItem.name, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}