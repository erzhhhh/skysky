package com.example.skysky.data

import com.example.skysky.domain.SearchApi
import com.example.skysky.domain.SearchInteractor
import com.example.skysky.domain.extensions.mapWordsLIst
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchInteractorImpl(private val searchApi: SearchApi) : SearchInteractor {

    override fun getSearchableWords(string: String): Observable<List<Word>> {
        return searchApi.searchWords(string)
            .map { it.mapWordsLIst() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
