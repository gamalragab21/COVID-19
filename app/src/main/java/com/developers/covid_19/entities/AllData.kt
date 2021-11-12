package com.developers.covid_19.entities

data class AllData(
    val active: Int,
    val activePerOneMillion: Double,
    val affectedCountries: Int,
    val cases: Int,
    val casesPerOneMillion: Int,
    val critical: Int,
    val criticalPerOneMillion: Double,
    val deaths: Int,
    val deathsPerOneMillion: Double,
    val oneCasePerPeople: Int,
    val oneDeathPerPeople: Int,
    val oneTestPerPeople: Int,
    val population: Long,
    val recovered: Int,
    val recoveredPerOneMillion: Double,
    val tests: Long,
    val testsPerOneMillion: Double,
    val todayCases: Int,
    val todayDeaths: Int,
    val todayRecovered: Int,
    val updated: Long
)