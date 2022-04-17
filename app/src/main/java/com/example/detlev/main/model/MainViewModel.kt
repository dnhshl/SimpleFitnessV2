package com.example.detlev.main.model

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.*
import com.example.detlev.main.network.FitnessApi
import com.example.detlev.main.network.FitnessData
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private var _fitnessData = MutableLiveData<String>()
    val fitnessData: LiveData<FitnessData>
        = Transformations.map(_fitnessData) { parseJsonData(it) }

    private val TAG = "MainViewModel"


    fun getFitnessData() {
        Log.i(TAG, "entering getFitnessData()")
        // Daten im Hintergrund abrufen
        try {
            viewModelScope.launch {
                _fitnessData.value = FitnessApi.retrofitService.getData()
            }
        }  catch (e: Exception) {
            Log.i(TAG, "Error loading data ${e.message}$")
            _fitnessData.value = ""
        }

    }

    private fun parseJsonData(jsonString: String) : FitnessData {
        Log.i(TAG, "entering getFitnessData()")
        try {
            //response String zu einem JSON Objekt
            val obj = JSONObject(jsonString)
            return  FitnessData(
                // Einzelne Elemente des JSON Objects extrahieren
                fitness = obj.getDouble("fitness"),
                puls = obj.getInt("puls"),
                timestamp = obj.getString("isotimestamp")
            )
        } catch (e : JSONException) {
            e.printStackTrace()
            Log.i(TAG, "Error JSON Parsing ${e.message}")
            return FitnessData(0.0, 0, "")
        }
    }
}