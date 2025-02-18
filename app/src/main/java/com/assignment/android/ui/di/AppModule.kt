package com.assignment.android.ui.di

import com.assignment.android.api.ApiService
import com.assignment.android.data.ListRepositoryImpl
import com.assignment.android.domain.ListRepository
import com.assignment.android.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AssignmentAppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = RetrofitClient.apiService

    @Provides
    @Singleton
    fun provideListRepository(apiService: ApiService): ListRepository = ListRepositoryImpl(apiService)
}