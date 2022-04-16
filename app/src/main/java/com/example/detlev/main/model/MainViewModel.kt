package com.example.detlev.main.model

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detlev.main.network.FitnessApi
import com.example.detlev.main.network.FitnessData
import kotlinx.coroutines.*
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
                // Daten umwandeln
                parseJsonData(jsonString)
            }
        }  catch (e: Exception) {
            Log.i(TAG, "Error loading data ${e.message}$")
        }
    }

    /**
     * start Job
     * val job = startRepeatingJob()
     * cancels the job and waits for its completion
     * job.cancelAndJoin()
     * cancel the job
     * job.cancel()
     * Params
     * timeInterval: time milliSeconds
     */
    fun startRepeatingDataLoadJob(timeInterval: Long): Job {
        return viewModelScope.launch {
            while (isActive) {
                // add your task here
                getFitnessData()
                delay(timeInterval)
            }
        }
    }

    private fun parseJsonData(jsonString: String) {
        try {
            //response String zu einem JSON Objekt
            val obj = JSONObject(jsonString)
            _fitnessData.value =  FitnessData(
                // Einzelne Elemente des JSON Objects extrahieren
                fitness = obj.getDouble("fitness"),
                puls = obj.getInt("puls"),
                timestamp = obj.getString("isotimestamp")
            )
        } catch (e : JSONException) {
            e.printStackTrace()
            Log.i(TAG, "Error JSON Parsing ${e.message}")
        }
    }
}