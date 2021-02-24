package com.example.skysky

import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.skysky.domain.Meaning
import com.example.skysky.domain.Translation
import com.example.skysky.data.Word
import com.example.skysky.presentation.worddetail.WordDetailsFragment
import org.junit.Test
import org.junit.runner.RunWith

private const val WORD = "word"

@RunWith(AndroidJUnit4::class)
class WordDetailsFragmentTest {

    @Test
    fun testFragment() {
        val word = Word(
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
        val fragmentArgs = bundleOf(WORD to word)

        val scenario = ActivityScenario.launch(TestFragmentActivity::class.java)
        scenario.onActivity {
            it.supportFragmentManager.commit {
                replace(android.R.id.content, WordDetailsFragment::class.java, fragmentArgs)
            }
        }

        onView(withId(R.id.closeButton)).check(matches(isDisplayed()))
        onView(withId(R.id.imageCardView)).check(matches(isDisplayed()))
        onView(withId(R.id.text)).check(matches(withText("word")))
        onView(withId(R.id.meaning)).check(matches(withText("слово")))
        onView(withId(R.id.transcription)).check(matches(withText("wordTranscription")))
        onView(withId(R.id.playButton)).check(matches(isDisplayed()))
    }
}
