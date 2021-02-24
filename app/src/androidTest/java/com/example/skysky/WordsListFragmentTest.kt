package com.example.skysky

import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.skysky.domain.Meaning
import com.example.skysky.domain.SearchInteractor
import com.example.skysky.domain.Translation
import com.example.skysky.data.Word
import com.example.skysky.presentation.wordslist.WordsListFragment
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class WordsListFragmentTest {

    @Inject
    lateinit var interactor: SearchInteractor

    @Before
    fun setUp() {
        ApplicationProvider.getApplicationContext<TestApplication>().getComponent().inject(this)
    }

    @After
    fun clean() {
        Mockito.reset(interactor)
    }

    @Test
    fun testNoSearchFragment() {
        val scenario = ActivityScenario.launch(TestFragmentActivity::class.java)
        scenario.onActivity {
            it.supportFragmentManager.commit {
                replace(android.R.id.content, WordsListFragment::class.java, null)
            }
        }

        onView(withId(R.id.editText)).check(matches(withHint(R.string.search_hint)))
        onView(withId(R.id.letsSearchContainer)).check(matches(isDisplayed()))
    }

    @Test
    fun testEmptySearchFragment() {
        `when`(interactor.getSearchableWords("abz")).thenReturn(
            Observable.just(listOf())
        )

        val scenario = ActivityScenario.launch(TestFragmentActivity::class.java)
        scenario.onActivity {
            it.supportFragmentManager.commit {
                replace(android.R.id.content, WordsListFragment::class.java, null)
            }
        }

        onView(withId(R.id.editText)).check(matches(withHint(R.string.search_hint)))
        onView(withId(R.id.editText)).perform(ViewActions.typeText("abz"))
        Thread.sleep(2000)
        onView(withId(R.id.emptyResultContainer)).check(matches(isDisplayed()))
    }

    @Test
    fun testCompletedSearchFragment() {
        val result = listOf(
            Word(
                "1",
                "p",
                isExpandable = false,
                isExpanded = false,
                createdFromMeaning = false,
                meaning = Meaning(
                    "p",
                    "1",
                    Translation("п"),
                    "",
                    "",
                    "p",
                    ""
                )
            ),
            Word(
                "1",
                "po",
                isExpandable = false,
                isExpanded = false,
                createdFromMeaning = false,
                meaning = Meaning(
                    "po",
                    "1",
                    Translation("по"),
                    "",
                    "",
                    "po",
                    ""
                )
            )
        )
        `when`(interactor.getSearchableWords("p")).thenReturn(
            Observable.just(result)
        )

        val scenario = ActivityScenario.launch(TestFragmentActivity::class.java)
        scenario.onActivity {
            it.supportFragmentManager.commit {
                replace(android.R.id.content, WordsListFragment::class.java, null)
            }
        }

        onView(withId(R.id.editText)).check(matches(withHint(R.string.search_hint)))
        onView(withId(R.id.editText)).perform(ViewActions.typeText("p"))
        Thread.sleep(2000)
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withText("по")).check(matches(isDisplayed()))
    }

    @Test
    fun testFailedSearchFragment() {
        `when`(interactor.getSearchableWords("p")).thenReturn(
            Observable.error(RuntimeException("Some exception!"))
        )

        val scenario = ActivityScenario.launch(TestFragmentActivity::class.java)
        scenario.onActivity {
            it.supportFragmentManager.commit {
                replace(android.R.id.content, WordsListFragment::class.java, null)
            }
        }

        onView(withId(R.id.editText)).check(matches(withHint(R.string.search_hint)))
        onView(withId(R.id.editText)).perform(ViewActions.typeText("p"))
        Thread.sleep(1000)
        onView(withText("Some exception!")).check(matches(isDisplayed()))
    }

    @Test
    fun testProgressSearchFragment() {
        `when`(interactor.getSearchableWords("p")).thenReturn(
            Observable.just(listOf())
        )

        val scenario = ActivityScenario.launch(TestFragmentActivity::class.java)
        scenario.onActivity {
            it.supportFragmentManager.commit {
                replace(android.R.id.content, WordsListFragment::class.java, null)
            }
        }

        onView(withId(R.id.editText)).check(matches(withHint(R.string.search_hint)))
        onView(withId(R.id.editText)).perform(ViewActions.typeText("p"))
        onView(withId(R.id.progressContainer)).check(matches(isDisplayed()))
    }

    @Test
    fun testFailedSearchFragment1() {
        `when`(interactor.getSearchableWords("")).thenReturn(
            Observable.error(RuntimeException())
        )

        val fragmentArgs = bundleOf("selectedListItem" to 0)

        val scenario = ActivityScenario.launch(TestFragmentActivity::class.java)
        scenario.onActivity {
            it.supportFragmentManager.commit {
                replace(android.R.id.content, WordsListFragment::class.java, fragmentArgs)
            }
        }

        onView(withId(R.id.editText)).check(matches(withHint(R.string.search_hint)))
        onView(withId(R.id.editText)).perform(ViewActions.typeText("p"))
        Thread.sleep(2000)
    }

//    @get:Rule
//    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
//
//    @Test
//    fun useAppContext() {
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.pokepedia", appContext.packageName)
//    }
//
//    @Test
//    fun isRecyclerVisible() {
//        onView(withId(R.id.pokemonListRecycler))
//            .check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun onItemClick() {
//        onView(withId(R.id.pokemonListRecycler))
//            .check(matches(isDisplayed()))
//
//        onView(withId(R.id.pokemonListRecycler))
//            .perform(
//                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                    0,
//                    click()
//                )
//            )
//
//        onView(withId(R.id.constraintLayout))
//            .check(matches(isDisplayed()))
//
//        onView(withId(R.id.nameTextView))
//            .check(matches(isDisplayed()))
//
//        onView(withId(R.id.heightTextView))
//            .check(matches(isDisplayed()))
//
//        onView(withId(R.id.weightTextView))
//            .check(matches(isDisplayed()))
//
//        onView(withId(R.id.expTextView))
//            .check(matches(isDisplayed()))
//
//        onView(withId(R.id.progressBar))
//            .check(matches(not(isDisplayed())))
//    }
}
