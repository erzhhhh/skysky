package com.example.skysky.di

import android.content.Context
import com.example.skysky.presentation.MainActivity
import com.example.skysky.presentation.wordslist.WordsListFragment
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun exposeContext(): Context

    fun inject(target: MainActivity)

    fun inject(target: WordsListFragment)
}