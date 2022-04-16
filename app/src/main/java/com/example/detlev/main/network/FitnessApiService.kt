package com.example.detlev.main.network

import android.util.Log
import org.json.JSONException
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Hier Basis URL der Webseite, von der Daten abgerufen werden sollen
private const val BASE_URL = "https://us-central1-si-hshl.cloudfunctions.net/"

private val TAG = "Network"

// Retrofit vorbereiten
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// Im Interface werden Funktionen definiert, mit denen auf einzelne Webseiten der
// BASE_URL Domain zugegriffen werden kann
// Das Keyword suspend erlaubt, dass diese Funktion parallel im Hintergrund ausgeführt wird
interface FitnessApiService {
    @GET("testdata_json")
    suspend fun getData(): String

}

// Über diese Api können wir in der App Retrofit nutzen
object FitnessApi {
    val retrofitService : FitnessApiService by lazy { retrofit.create(FitnessApiService::class.java) }
}


data class FitnessData(val fitness: Double, val puls: Int, val timestamp: String)
/*
{
    val time =
        try {
         LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        } catch (e : Exception) {
            Log.i(TAG, "Error Parsing timestamp ${e.message}")
            LocalDateTime.now()
        }
}*/
