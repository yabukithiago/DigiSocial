package com.examples.digisocial.utils

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface CountriesApi {
    @GET("all?fields=name")
    suspend fun getCountries(): List<CountryResponse>
}

data class CountryResponse(
    @SerializedName("name") val name: Name
)

data class Name(
    @SerializedName("common") val common: String
)

object ApiService {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/v3.1/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val countriesApi: CountriesApi = retrofit.create(CountriesApi::class.java)
}

suspend fun getCountryNames(): List<String> {
    return try {
        val countries = ApiService.countriesApi.getCountries()
        countries.map { it.name.common }
    } catch (e: Exception) {
        println("Erro na chamada da API: ${e.message}")
        emptyList()
    }
}