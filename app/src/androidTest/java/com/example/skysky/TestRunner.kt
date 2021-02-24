package com.example.skysky

import android.app.Application
import android.content.Context

@Suppress("unused")
// TODO: этот класс используется в build.gradle в поле android.defaultConfig.testInstrumentationRunner
//  так мы можем заменить Application, а заодно и всё дерево зависимостей
class TestRunner : androidx.test.runner.AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return newApplication(TestApplication::class.java, context)
    }
}