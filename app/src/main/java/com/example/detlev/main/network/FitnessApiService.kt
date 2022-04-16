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

// Error Codes
object ErrorCodes {
    const val NO_ERROR = 0
    const val INTERNET_ERROR = 1
    const val JSON_ERROR = 2
}

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


data class FitnessData(val fitness: Double  = 0.0,
                       val puls: Int = 0,
                       val timestamp: String = "2000-01-01T00:00:00.000000+00:00",
                       val errorcode: Int = ErrorCodes.NO_ERROR
                       )

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
