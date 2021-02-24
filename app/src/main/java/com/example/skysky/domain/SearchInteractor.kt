package com.example.skysky.domain

import com.example.skysky.data.Word
import io.reactivex.Observable

/**
 * Интерактор для получения слов
 */
interface SearchInteractor {

    /**
     * Запрос поиска слов
     *
     * @param string искомое слово
     */
    fun getSearchableWords(string: String): Observable<List<Word>>

}
