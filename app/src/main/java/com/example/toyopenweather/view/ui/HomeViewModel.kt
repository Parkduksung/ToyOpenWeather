package com.example.toyopenweather.view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toyopenweather.data.model.CityItem
import com.example.toyopenweather.data.repo.CityRepository
import com.example.toyopenweather.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val cityRepository: CityRepository) : ViewModel() {

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

    sealed class HomeViewState {
        data class GetCityList(val cityList: List<CityItem>) : HomeViewState()
        object ErrorGetCityList : HomeViewState()
    }

}
