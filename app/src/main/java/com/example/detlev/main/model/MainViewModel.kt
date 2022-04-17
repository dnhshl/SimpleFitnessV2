package com.example.detlev.main.model

import android.util.Log
import androidx.lifecycle.*
import com.example.detlev.main.network.FitnessApi
import com.example.detlev.main.network.FitnessData
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val TAG = "MainViewModel"

    private var _fitnessData = MutableLiveData<String>()
    val fitnessData: LiveData<FitnessData>
        = Transformations.map(_fitnessData) { parseJsonData(it) }


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

    private fun parseJsonData(jsonString: String): FitnessData {
        try {
            val obj = JSONObject(jsonString)
            return FitnessData(
                obj.getDouble("fitness"),
                obj.getInt("puls"),
                obj.getString("isotimestamp")
            )
        } catch (e: Exception) {
            Log.i(TAG, "Error parsing JSON ${e.message}")
            return FitnessData(0.0, 0, "")
        }
    }
}