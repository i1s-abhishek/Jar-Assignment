package com.abhishek.jaronboarding.data.di

import com.abhishek.jaronboarding.data.OnBoardingRepositoryIml
import com.abhishek.jaronboarding.data.networkClient.ApiService
import com.abhishek.jaronboarding.domain.OnBoardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideOnBoardingRepository(
        apiService: ApiService
    ): OnBoardingRepository {
        return OnBoardingRepositoryIml(apiService)
    }
}
