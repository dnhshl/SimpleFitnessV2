package com.example.detlev.main.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detlev.main.network.FitnessApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val TAG = "MainViewModel"

    private var _fitnessData = MutableLiveData<String>()
    val fitnessData: LiveData<String>
        get() = _fitnessData

    fun getFitnessData() {
        viewModelScope.launch {
            try {
                val jsonString = FitnessApi.retrofitService.getData()
                _fitnessData.value = jsonString
            } catch (e: Exception) {
                Log.i(TAG, "Error loading data ${e.message}")
            }
        }
    }
}