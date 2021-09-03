package com.example.toyopenweather.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
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

    val inputCityLiveData = MutableLiveData<String>()

    private val cityRepository by inject<CityRepository>(CityRepository::class.java)
    private val weatherRepository by inject<WeatherRepository>(WeatherRepository::class.java)


    fun getCityList() {
        viewModelMainScope.launch {
            cityRepository.isExistCityList()
            if (cityRepository.isExistCityList()) {
                getAllCityItem()
            } else {
                when (val getCityListResult = cityRepository.getAssetCityList()) {
                    is Result.Success -> {
                        if (cityRepository.registerCityList(getCityListResult.value)) {
                            getAllCityItem()
                        } else {
                            viewStateChanged(HomeViewState.ErrorGetCityList)
                        }
                    }
                    is Result.Failure -> {
                        viewStateChanged(HomeViewState.ErrorGetCityList)
                    }
                }
            }
        }
    }


    private fun getAllCityItem() {
        viewModelMainScope.launch {
            when (val getAllCityEntityResult = cityRepository.getAllCityEntity()) {
                is Result.Success -> {
                    viewStateChanged(HomeViewState.GetCityList(getAllCityEntityResult.value.map { it.toCityItem() }))
                }

                is Result.Failure -> {
                    viewStateChanged(HomeViewState.ErrorGetCityList)
                }
            }
        }
    }

    fun getCityItem() {
        viewModelMainScope.launch {
            inputCityLiveData.value?.let { searchCityName ->
                when (val getCityEntity = cityRepository.getCityEntity(searchCityName)) {
                    is Result.Success -> {
                        if (getCityEntity.value == null) {
                            viewStateChanged(HomeViewState.ErrorGetCityItem)
                        } else {
                            viewStateChanged(HomeViewState.GetCityItem(getCityEntity.value.toCityItem()))
                        }
                    }
                    is Result.Failure -> {
                        viewStateChanged(HomeViewState.ErrorGetCityItem)
                    }
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
        data class GetCityItem(val cityItem: CityItem) : HomeViewState()
        data class GetCurrentWeather(val weatherItem: WeatherItem) : HomeViewState()
        object ErrorGetCityList : HomeViewState()
        object ErrorGetCityItem : HomeViewState()
        object ErrorGetCurrentWeather : HomeViewState()
        data class RouteDetail(val cityId: Int) : HomeViewState()
        object RouteContent : HomeViewState()
    }

}
