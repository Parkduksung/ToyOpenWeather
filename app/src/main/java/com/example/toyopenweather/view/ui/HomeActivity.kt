package com.example.toyopenweather.view.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.toyopenweather.R
import com.example.toyopenweather.base.BaseActivity
import com.example.toyopenweather.base.ViewState
import com.example.toyopenweather.databinding.ActivityHomeBinding
import com.example.toyopenweather.viewmodel.HomeViewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
    }

    private fun initViewModel() {
        homeViewModel.viewStateLiveData.observe(this@HomeActivity) { viewState: ViewState? ->

            (viewState as? HomeViewModel.HomeViewState)?.let { onChangedViewState(viewState) }
        }
    }

    private fun onChangedViewState(viewState: HomeViewModel.HomeViewState) {

        when (viewState) {
            is HomeViewModel.HomeViewState.RouteContent -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_home, CityContentFragment()).commit()
            }

            is HomeViewModel.HomeViewState.RouteDetail -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container_home,
                        CityDetailFragment.newInstance(viewState.cityId)
                    ).commit()
            }

            is HomeViewModel.HomeViewState.ErrorGetCityItem -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_not_search_city_item),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            is HomeViewModel.HomeViewState.ErrorGetCityList -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_get_city_list),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            is HomeViewModel.HomeViewState.ErrorGetCurrentWeather -> {
                Toast.makeText(
                    this,
                    resources.getString(R.string.error_get_current_weather),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

}