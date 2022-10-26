package com.example.detlev.main.model

import android.util.Log
import androidx.lifecycle.*
import com.example.detlev.main.network.ErrorCodes
import com.example.detlev.main.network.FitnessApi
import com.example.detlev.main.network.FitnessData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel : ViewModel() {
    private val TAG = "MainViewModel"

    private var _fitnessData = MutableLiveData<String>()
    val fitnessData: LiveData<FitnessData>
        = Transformations.map(_fitnessData) { parseJsonData(it) }

    private var _pulsDataSet = LineDataSet(mutableListOf<Entry>(), "Pulswerte")
    val pulsDataSet: LineDataSet
        get() = _pulsDataSet


    val baseTimestamp = LocalDateTime.now()

    fun getFitnessData() {
        viewModelScope.launch {
            try {
                val jsonString = FitnessApi.retrofitService.getData()
                _fitnessData.value = jsonString
            } catch (e: Exception) {
                Log.i(TAG, "Error loading data ${e.message}")
                _fitnessData.value = ""
            }
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

    private fun parseJsonData(jsonString: String): FitnessData {
        if (jsonString.isEmpty()) return FitnessData(errorcode = ErrorCodes.INTERNET_ERROR)
        try {
            val obj = JSONObject(jsonString)

            val timestamp = LocalDateTime.parse(obj.getString("isotimestamp"), DateTimeFormatter.ISO_DATE_TIME)
            val timediff = Duration.between(baseTimestamp, timestamp).seconds
            _pulsDataSet.addEntry(Entry(timediff.toFloat(), obj.getDouble("puls").toFloat()))
            // Beschränke max. Anzahl angezeigter Werte
            if (_pulsDataSet.entryCount > 15)  _pulsDataSet.removeFirst()

            return FitnessData(
                fitness = obj.getDouble("fitness"),
                puls = obj.getInt("puls"),
                timestamp = obj.getString("isotimestamp")
            )
        } catch (e: Exception) {
            Log.i(TAG, "Error parsing JSON ${e.message}")
            return FitnessData(errorcode = ErrorCodes.JSON_ERROR)
        }
    }

}