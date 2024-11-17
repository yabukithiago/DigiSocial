package com.examples.digisocial.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CountriesApi {
    @GET("all")
    suspend fun getCountries(): List<CountryResponse>
}

data class CountryResponse(
    val name: Name
)

data class Name(
    val common: String
)

object ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/v3.1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val countriesApi: CountriesApi = retrofit.create(CountriesApi::class.java)
}

suspend fun getCountryNames(): List<String> {
    return ApiService.countriesApi.getCountries().map { it.name.common }
}