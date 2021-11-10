package com.developers.covid_19.data.network


import com.developers.covid_19.entities.CovidModelItem
import com.developers.covid_19.entities.CovidParent
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiJobService {

    @GET("countries")
    suspend fun getListCovidInfo(@Query("page")  page:Int): CovidParent

    @GET("countries/{countryName}")
    suspend fun getListCovidInfoForSelectedCountry(@Path("countryName")  countryName:String): CovidModelItem


}