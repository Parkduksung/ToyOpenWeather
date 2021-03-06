package com.example.toyopenweather.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.toyopenweather.R
import com.example.toyopenweather.base.BaseFragment
import com.example.toyopenweather.data.model.WeatherItem
import com.example.toyopenweather.databinding.FragmentCityDetailBinding
import com.example.toyopenweather.viewmodel.HomeViewModel

class CityDetailFragment : BaseFragment<FragmentCityDetailBinding>(R.layout.fragment_city_detail) {

    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        binding.viewModel = homeViewModel

        arguments?.getInt(KEY_CITY_ID)?.let { homeViewModel.getCurrentWeatherById(it) }

        homeViewModel.viewStateLiveData.observe(requireActivity()) { viewState ->
            (viewState as? HomeViewModel.HomeViewState)?.let { onChangedViewState(viewState) }
        }
    }

    private fun onChangedViewState(viewState: HomeViewModel.HomeViewState) {
        when (viewState) {
            is HomeViewModel.HomeViewState.GetCurrentWeather -> {
                upDataUi(viewState.weatherItem)
            }
        }
    }

    private fun upDataUi(weatherItem: WeatherItem) {
        with(binding) {
            tvName.text = weatherItem.name
            tvCountry.text = weatherItem.country
            tvWeather.text = weatherItem.weather
            tvHumidity.text = weatherItem.humidity
            tvCelsius.text = weatherItem.celsius
        }
    }

    companion object {

        const val KEY_CITY_ID = "key_city_id"

        fun newInstance(cityId: Int): CityDetailFragment =
            CityDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_CITY_ID, cityId)
                }
            }
    }

}