package com.developers.covid_19.data.network


import com.developers.covid_19.entities.AllData
import com.developers.covid_19.entities.CovidModelItem
import com.developers.covid_19.entities.CovidParent
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiJobService {

    @GET("countries")
    suspend fun getListCovidInfo(): ArrayList<CovidModelItem>

    @GET("countries/{countryName}")
    suspend fun getListCovidInfoForSelectedCountry(@Path("countryName")  countryName:String): CovidModelItem

    @GET("all")
    suspend fun getAllDataWorld(): AllData

}