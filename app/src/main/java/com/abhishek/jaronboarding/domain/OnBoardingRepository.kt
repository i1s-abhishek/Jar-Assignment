package com.abhishek.jaronboarding.domain

import com.abhishek.jaronboarding.domain.common.Resource
import com.abhishek.jaronboarding.domain.dto.respose.OnBoardingResponse

interface OnBoardingRepository {
    suspend fun getOnBoardingData(): Resource<OnBoardingResponse>
}