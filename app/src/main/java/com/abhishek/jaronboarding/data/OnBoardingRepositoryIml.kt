package com.abhishek.jaronboarding.data

import com.abhishek.jaronboarding.data.networkClient.ApiService
import com.abhishek.jaronboarding.domain.OnBoardingRepository
import com.abhishek.jaronboarding.domain.common.Resource
import com.abhishek.jaronboarding.domain.dto.respose.OnBoardingResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OnBoardingRepositoryIml @Inject constructor(val apiService: ApiService) :
    OnBoardingRepository {
    private suspend inline fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        crossinline apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                Resource.Success(apiCall())
            } catch (e: Exception) {
                ensureActive()
                Resource.Error(e)
            }
        }
    }

    override suspend fun getOnBoardingData(): Resource<OnBoardingResponse> {
        return safeApiCall {
            apiService.getOnBoardingData()
        }
    }

}
