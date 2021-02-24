package com.example.skysky

import androidx.appcompat.app.AppCompatActivity

// TODO: находится в дебаг конфигурации. Когда тестируешь com.android.application,
//  то небходмо так делать, поскольку запускает основное приложение
//  Если это был бы модуль com.android.library, то это можно было делать в конфигурации androidTest

// TODO: Использовать из fragment-testing не получится,
//  т.к. там используется в качестве activity-родителя FragmentActivity, а не AppCompatActivity
class TestFragmentActivity : AppCompatActivity()