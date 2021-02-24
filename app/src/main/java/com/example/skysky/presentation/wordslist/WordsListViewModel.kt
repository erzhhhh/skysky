package com.example.skysky.presentation.wordslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skysky.data.ScreenState
import com.example.skysky.data.Word
import com.example.skysky.domain.SearchInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DEBOUNCE_DELAY = 700L

/**
 * Вьюмодель списка значений для слова
 *
 * @param searchInteractor интерактор для получения слов
 */
class WordsListViewModel @Inject constructor(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    private lateinit var queryDisposable: Disposable
    private val userInputSubject = PublishSubject.create<String>()

    private var _childModels = MutableLiveData<List<Word>>()
    val childModels: LiveData<List<Word>> = _childModels

    private val _screenState: MutableLiveData<ScreenState> = MutableLiveData()
    val screenState: LiveData<ScreenState> = _screenState

    init {
        initialize()
    }

    private fun initialize() {
        queryDisposable = userInputSubject
            .distinctUntilChanged()
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                _childModels.value = emptyList()
                if (it.isNotEmpty()) {
                    _screenState.value = ScreenState.LOADING
                } else {
                    _screenState.value = ScreenState.NOT_SEARCH
                }
            }
            .debounce(DEBOUNCE_DELAY, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { if (it.isNotEmpty()) searchInteractor.getSearchableWords(it) else Observable.never() }
            .doOnError {
                _screenState.value = ScreenState.error(it.message)
            }
            .retry()
            .subscribe(
                {
                    _childModels.value = it
                    if (it.isEmpty()) {
                        _screenState.value = ScreenState.EMPTY_SEARCH
                    } else {
                        _screenState.value = ScreenState.LOADED
                    }
                },
                {
                    // error ui
                }
            )
    }

    fun search(string: String) {
        userInputSubject.onNext(string)
    }

    fun expand(item: Word) {
        val list = _childModels.value?.toMutableList() ?: return
        val index = list.indexOf(item)
        if (index == -1) {
            return
        }
        if (item.isExpanded) {
            val count = item.meanings?.size ?: 0
            for (i in 0 until count) {
                list.removeAt(index + 1)
            }
        } else {
            list.addAll(index + 1, item.meanings?.map {
                Word(
                    isExpandable = false,
                    createdFromMeaning = true,
                    word = it.text,
                    isExpanded = false,
                    meaning = it
                )
            } ?: emptyList())
        }

        list[index] = item.copy(isExpanded = !item.isExpanded)
        _childModels.value = list
    }

    fun retry() {
        initialize()
    }
}