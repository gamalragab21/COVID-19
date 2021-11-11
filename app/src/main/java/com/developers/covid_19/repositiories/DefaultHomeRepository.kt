package com.developers.covid_19.repositiories

import com.developers.covid_19.data.network.ApiJobService
import com.developers.covid_19.entities.AllData
import com.developers.covid_19.entities.CovidModelItem
import com.developers.covid_19.entities.CovidParent
import com.developers.covid_19.helpers.safeCall
import com.developers.covid_19.qualifiers.IOThread
import com.example.architecturalskeleton.helpers.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultHomeRepository @Inject constructor(
    private val apiJobService: ApiJobService,
    @IOThread
    private val dispatcher: CoroutineDispatcher
) {

    // TODO: 11/8/2021 how to handle Dao or apiservice in repo  

    suspend fun getDataForSelectedCountry(countryName: String): Resource<CovidModelItem> = withContext(dispatcher){
        safeCall {
            val result=apiJobService.getListCovidInfoForSelectedCountry(countryName)
            Resource.Success(result)
        }
    }
    suspend fun getAllData(): Resource<AllData> = withContext(dispatcher){
        safeCall {
            val result=apiJobService.getAllDataWorld()
            Resource.Success(result)
        }
    }

   suspend fun getCountries(): Resource< ArrayList<CovidModelItem>> = withContext(dispatcher){
       safeCall {
           val result=apiJobService.getListCovidInfo()
           Resource.Success(result)
       }
   }
}