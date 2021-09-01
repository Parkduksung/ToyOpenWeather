package com.example.toyopenweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toyopenweather.api.response.WeatherResponse
import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.repo.CityRepository
import com.example.toyopenweather.data.repo.WeatherRepository
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _viewStateLiveData = MutableLiveData<HomeViewState>()
    val viewStateLiveData: LiveData<HomeViewState> = _viewStateLiveData

    suspend fun getCityList() {
        CoroutineScope(Dispatchers.Main).launch {
            when (val getCityListResult = cityRepository.getCityList()) {
                is Result.Success -> {
                    _viewStateLiveData.value = HomeViewState.GetCityList(getCityListResult.value)
                }
                is Result.Failure -> {
                    _viewStateLiveData.value = HomeViewState.ErrorGetCityList
                }
            }
        }
    }

    suspend fun getCurrentWeatherById(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            when (val getCurrentWeatherResult = weatherRepository.getCurrentWeatherById(id = id)) {
                is Result.Success -> {
                    _viewStateLiveData.value =
                        HomeViewState.GetCurrentWeather(getCurrentWeatherResult.value)
                }

                is Result.Failure -> {
                    _viewStateLiveData.value = HomeViewState.ErrorGetCurrentWeather
                }
            }
        }

    }

    sealed class HomeViewState {
        data class GetCityList(val cityList: List<CityItem>) : HomeViewState()
        data class GetCurrentWeather(val weatherResponse: WeatherResponse) : HomeViewState()
        object ErrorGetCityList : HomeViewState()
        object ErrorGetCurrentWeather : HomeViewState()
    }

}
