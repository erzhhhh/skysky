package com.example.skysky

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.skysky.domain.Meaning
import com.example.skysky.domain.SearchInteractor
import com.example.skysky.domain.Translation
import com.example.skysky.data.Word
import com.example.skysky.presentation.wordslist.WordsListViewModel
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(CustomRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class WordsListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WordsListViewModel

    @Mock
    private lateinit var mockObserver: Observer<List<Word>>

    @Mock
    private lateinit var interactor: SearchInteractor

    private val word = Word(
        "1",
        "word",
        isExpandable = false,
        isExpanded = false,
        createdFromMeaning = false,
        meaning = Meaning(
            "p",
            "1",
            Translation("слово"),
            "",
            "",
            "wordTranscription",
            ""
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = WordsListViewModel(interactor)
    }

    @Test
    fun assertListSize() {
        `when`(interactor.getSearchableWords("p"))
            .thenReturn(Observable.just(listOf(word)))

        viewModel.search("p")
        viewModel.childModels.observeForever(mockObserver)

        val value = viewModel.childModels.getOrAwaitValue()
        assertThat(value.size.toString(), `is`("1"))
    }


    @Test
    fun assertListContents() {
        `when`(interactor.getSearchableWords("p"))
            .thenReturn(Observable.just(listOf(word)))

        viewModel.search("p")
        viewModel.childModels.observeForever(mockObserver)

        val value = viewModel.childModels.getOrAwaitValue()
        assertThat(value.first().meaning?.translation?.text, `is`("слово"))
    }

    @Test
    fun assertError() {
        `when`(interactor.getSearchableWords("p"))
            .thenReturn(Observable.error(Exception()))

        viewModel.search("p")
        viewModel.childModels.observeForever(mockObserver)

        val value = viewModel.childModels.getOrAwaitValue()

        assertThat(value.isEmpty(), `is`(true))
    }
}