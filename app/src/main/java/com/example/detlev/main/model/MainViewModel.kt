package com.example.detlev.main.model

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detlev.main.network.FitnessApi
import com.example.detlev.main.network.FitnessData
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private var _fitnessData = MutableLiveData<FitnessData>()
    val fitnessData: LiveData<FitnessData>
        get() = _fitnessData

    private val TAG = "MainViewModel"


    fun getFitnessData() {
        // Daten im Hintergrund abrufen
        try {
            viewModelScope.launch {
                val jsonString = FitnessApi.retrofitService.getData()
                // sobald die Daten da sind, werden sie per LiveData angezeigt
                parseJsonData(jsonString)
            }
        }  catch (e: Exception) {
            Log.i(TAG, "Error Internet Access ${e.message}$")
            //_fitnessData.value = "Error Internet Access ${e.message}$"
        }
    }

    private fun parseJsonData(jsonString: String) {
        try {
            //response String zu einem JSON Objekt
            val obj = JSONObject(jsonString)
            _fitnessData.value =  FitnessData(
                fitness = obj.getDouble("fitness"),
                puls = obj.getInt("puls"),
                timestamp = obj.getString("isotimestamp")
            )
        } catch (e : JSONException) {
            e.printStackTrace()
            Log.i(TAG, "Error JSON Parsing ${e.message}")
            //return FitnessData(-1.0, -1, "2000-01-01T00:00:00.00000+00:00")
        }
    }

}