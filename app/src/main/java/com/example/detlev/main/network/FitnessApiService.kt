package com.example.detlev.main.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

// Hier Basis URL der Webseite, von der Daten abgerufen werden sollen
private const val BASE_URL = "https://run.mocky.io/"

// Retrofit vorbereiten
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// im Interface werden Funktionen definiert, mit denen auf Unterseiten der
// BASE_URL Webseite zugegriffen werden kann
// Das Keyword suspend erlaubt, dass diese Funktion parallel im Hintergrund ausgeführt wird
interface FitnessApiService {
    @GET("v3/e815e607-a1ba-4083-ae67-895c6078d4bb")
    suspend fun getData(): String

}

// Über diese Api können wir in der App Retrofit nutzen
object FitnessApi {
    val retrofitService : FitnessApiService by lazy { retrofit.create(FitnessApiService::class.java) }
}