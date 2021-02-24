package com.example.skysky.domain

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

private const val SEARCH_WORDS = "words/search"

interface SearchApi {

    /**
     * Запрос поиска слов
     *
     * @param search искомое слово
     */
    @GET(SEARCH_WORDS)
    fun searchWords(@Query("search") search: String): Observable<List<WordData>>
}