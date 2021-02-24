package com.example.skysky.presentation.wordslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skysky.domain.SearchInteractor

/**
 * Фабрика для создания [WordsListViewModel]
 *
 * @param searchInteractor интерактор для получения слов
 */
class WordsListViewModelFactory(
    private val searchInteractor: SearchInteractor
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordsListViewModel::class.java)) {
            return WordsListViewModel(searchInteractor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}