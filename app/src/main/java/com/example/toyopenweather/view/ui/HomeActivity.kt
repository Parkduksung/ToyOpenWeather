package com.example.toyopenweather.view.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.example.toyopenweather.R
import com.example.toyopenweather.base.BaseActivity
import com.example.toyopenweather.databinding.ActivityHomeBinding
import com.example.toyopenweather.viewmodel.HomeViewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
    }

    private fun initViewModel() {
        homeViewModel.viewStateLiveData.observe(this) { viewState ->
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
            }
        }
    }


}