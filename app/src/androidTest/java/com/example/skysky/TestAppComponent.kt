package com.example.skysky

import com.example.skysky.di.AppComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        TestAppModule::class
    ]
)
interface TestAppComponent: AppComponent {

    fun inject(target: WordsListFragmentTest)

    fun inject(target: WordDetailsFragmentTest)
}