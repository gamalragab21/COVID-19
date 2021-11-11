package com.developers.covid_19.entities

import com.google.gson.annotations.Expose

data class CovidModelItem(
    val active: Double,
    val activePerOneMillion: Double,
    val cases: Double,
    val casesPerOneMillion: Double,
    val continent: String,
    val country: String,
    val countryInfo: CountryInfo,
    val critical: Double,
    val criticalPerOneMillion: Double,
    val deaths: Double,
    val deathsPerOneMillion: Double,
    val oneCasePerPeople: Double,
    val oneDeathPerPeople: Double,
    val oneTestPerPeople: Double,
    val population: Double,
    val recovered: Double,
    val recoveredPerOneMillion: Double,
    val tests: Double,
    val testsPerOneMillion: Double,
    val todayCases: Double,
    val todayDeaths: Double,
    val todayRecovered: Double,
    val updated: Long
)