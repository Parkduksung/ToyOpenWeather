package com.example.toyopenweather.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val _viewStateLiveData = MutableLiveData<ViewState>()
    val viewStateLiveData: LiveData<ViewState> = _viewStateLiveData

    protected val viewModelMainScope = CoroutineScope(Dispatchers.Main)
    protected val viewModelIOScope = CoroutineScope(Dispatchers.IO)

    protected fun viewStateChanged(viewState: ViewState) {
        _viewStateLiveData.value = viewState
        _viewStateLiveData.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelMainScope.cancel()
        viewModelIOScope.cancel()
    }
}

interface ViewState