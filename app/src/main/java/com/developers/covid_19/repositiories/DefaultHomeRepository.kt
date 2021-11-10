package com.developers.covid_19.repositiories

import com.developers.covid_19.qualifiers.IOThread
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DefaultHomeRepository @Inject constructor(
//    private val apiJobService: ApiJobService,
//    private val jobDao: JobDao,
    @IOThread
    private val dispatcher: CoroutineDispatcher
) {

    // TODO: 11/8/2021 how to handle Dao or apiservice in repo  

//    suspend fun getListJob(currentJob: Int):Resource<ParentJob> = withContext(dispatcher){
//        safeCall {
//            val result=apiJobService.getListJobs(currentJob)
//            Resource.Success(result)
//        }
//    }
}