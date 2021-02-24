package com.example.skysky

import android.content.Context
import com.example.skysky.domain.SearchInteractor
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock
import javax.inject.Singleton

// TODO: тут замокано только то, что инжектится, промежуточные можно не создавать,
//  У тебя инжектится только SearchInteractor во фрагмент, поэтому его можно замокать, остальные зависимости не нужны
@Module
class TestAppModule(@get:Provides val context: Context) {

    @Provides
    @Singleton
    fun provideCategoryRepo(): SearchInteractor = mock(SearchInteractor::class.java)

//    object : SearchInteractor {
//
//        override fun getSearchableWords(string: String): Observable<List<Word>> {
//            return Observable.never()
//        }
//    }
}
