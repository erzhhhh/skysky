package com.example.skysky.di

import com.example.skysky.domain.SearchApi
import com.example.skysky.domain.SearchInteractor
import com.example.skysky.data.SearchInteractorImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class WorkManagerModule {

    @Singleton
    @Provides
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Provides
    fun provideSearchInteractor(service: SearchApi): SearchInteractor =
        SearchInteractorImpl(service)
}