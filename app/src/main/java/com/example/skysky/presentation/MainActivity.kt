package com.example.skysky.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.example.skysky.R
import com.example.skysky.data.Word
import com.example.skysky.databinding.ActivityMainBinding
import com.example.skysky.presentation.worddetail.WordDetailsFragment
import com.example.skysky.presentation.wordslist.WordsListFragment

/**
 * Стартовая активити
 */
class MainActivity : AppCompatActivity(), OnItemClickListener<Word> {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                this.replace(R.id.fragmentContainer, WordsListFragment())
            }
        }
    }

    override fun onItemClick(item: Word) {
        if (binding.fragmentDetailsContainer != null) {
            val fragment = WordDetailsFragment.newInstance(item)
            supportFragmentManager.commit {
                this.replace(R.id.fragmentDetailsContainer, fragment)
            }
        } else {
            val fragment = WordDetailsFragment.newInstance(item)
            supportFragmentManager.commit {
                this.replace(R.id.fragmentContainer, fragment)
                this.addToBackStack(null)
            }
        }
    }
}

//        Формулировка тестового задания:
//
//        Реализовать приложение для поиска переводов слов в словаре, состоящее из двух экранов:
//        - Экран, содержащий форму поиска слова и таблицу отображения результатов
//        - Экран, отображающий подробную информацию о слове (текст, перевод и картинка, остальные поля по желанию), открывается по нажатию на ячейку в таблице с результатами поиска
//
//        Загрузку данных о словах производить при помощи публичного API, документация находится здесь: https://dictionary.skyeng.ru/doc/api/external
//
//        Требования:
//        - Для Android использовать Kotlin
//                - К коду приложить Readme, где коротко описывается как собрать и запустить код
//        - Результат выполнения загрузить на любой Git-хостинг и прислать ссылку, zip-файлы и прочие форматы не принимаются
//        - Использование сторонних библиотек разрешается, в этом случае в Readme нужно указать зачем она используется
//        - Приложение должно корректно обрабатывать повороты экрана
//                - К приложению должен быть написан хотя бы 1 юнит-тест
//        - Реализованная функциональность должна быть максимально близка к production-состоянию
//        - UI приложения должен соответствовать гайдлайнам платформы.