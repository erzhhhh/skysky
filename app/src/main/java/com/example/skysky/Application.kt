package com.example.skysky

import androidx.multidex.MultiDexApplication
import com.example.skysky.di.AppComponent
import com.example.skysky.di.AppModule
import com.example.skysky.di.DaggerAppComponent

private const val BASE_URL = "https://dictionary.skyeng.ru/api/public/v1/"

/**
 * Переопределение Application для создания дерева зависимостей
 */
open class Application : MultiDexApplication() {

    lateinit var appComponent: AppComponent

    open fun getComponent(): AppComponent = appComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = createComponent()
    }

    open fun createComponent(): AppComponent =
        DaggerAppComponent
            .builder()
            .appModule(
                AppModule(
                    this,
                    BASE_URL
                )
            )
            .build()
}