package com.example.toyopenweather.view.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.toyopenweather.R
import com.example.toyopenweather.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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