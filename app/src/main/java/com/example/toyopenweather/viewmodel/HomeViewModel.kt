package com.example.toyopenweather.viewmodel

import android.app.Application
import com.example.toyopenweather.base.BaseViewModel
import com.example.toyopenweather.base.ViewState
import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.model.WeatherItem
import com.example.toyopenweather.data.repo.CityRepository
import com.example.toyopenweather.data.repo.WeatherRepository
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class HomeViewModel(app: Application) : BaseViewModel(app) {

    private val cityRepository by inject<CityRepository>(CityRepository::class.java)
    private val weatherRepository by inject<WeatherRepository>(WeatherRepository::class.java)

    fun getCityList() {
        viewModelMainScope.launch {
            when (val getCityListResult = cityRepository.getCityList()) {
                is Result.Success -> {
                    viewStateChanged(HomeViewState.GetCityList(getCityListResult.value))
                }
                is Result.Failure -> {
                    viewStateChanged(HomeViewState.ErrorGetCityList)
                }
            }
        }
    }

    fun getCurrentWeatherById(id: Int) {
        viewModelMainScope.launch {
            when (val getCurrentWeatherResult = weatherRepository.getCurrentWeatherById(id = id)) {
                is Result.Success -> {
                    viewStateChanged(HomeViewState.GetCurrentWeather(getCurrentWeatherResult.value.toWeatherItem()))
                }

                is Result.Failure -> {
                    viewStateChanged(HomeViewState.ErrorGetCurrentWeather)
                }
            }
        }
    }


    fun routeContent() {
        viewStateChanged(HomeViewState.RouteContent)
    }

    fun routeDetail(cityId: Int) {
        viewStateChanged(HomeViewState.RouteDetail(cityId = cityId))
    }

    sealed class HomeViewState : ViewState {
        data class GetCityList(val cityList: List<CityItem>) : HomeViewState()
        data class GetCurrentWeather(val weatherItem: WeatherItem) : HomeViewState()
        object ErrorGetCityList : HomeViewState()
        object ErrorGetCurrentWeather : HomeViewState()
        data class RouteDetail(val cityId: Int) : HomeViewState()
        object RouteContent : HomeViewState()
    }

}
