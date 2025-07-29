package com.abhishek.jaronboarding.data.networkClient

import com.abhishek.jaronboarding.domain.dto.respose.OnBoardingResponse
import retrofit2.http.GET

interface ApiService {
    companion object {
        const val GET_ONBOARDING_DATA = "/796729cca6c55a7d089e"
    }

    @GET(GET_ONBOARDING_DATA)
    suspend fun getOnBoardingData(): OnBoardingResponse
}
