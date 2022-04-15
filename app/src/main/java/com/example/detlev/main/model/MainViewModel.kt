package com.example.detlev.main.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detlev.main.network.FitnessApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var _fitnessData = MutableLiveData<String>()
    val fitnessData: LiveData<String>
        get() = _fitnessData


    fun getFitnessData() {
        viewModelScope.launch {
            val listResult = FitnessApi.retrofitService.getData()
            _fitnessData.value = listResult
        }
    }
}