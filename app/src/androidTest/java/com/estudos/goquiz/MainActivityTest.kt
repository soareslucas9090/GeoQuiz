package com.estudos.goquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.estudos.goquiz.viewModels.GoQuizViewModel
import com.estudos.goquiz.views.MainActivity
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private val savedStateHandle = SavedStateHandle()
    private val quizViewModel = GoQuizViewModel(savedStateHandle)

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun showsFirstQuestion() {
        var viewModel: GoQuizViewModel? = null
        scenario.onActivity {
            viewModel = ViewModelProvider(it)[GoQuizViewModel::class.java]
        }
        onView(withId(R.id.text_question))
            .check(matches(withText(viewModel?.currentQuestionText!!)))
    }

    @Test
    fun showsSecondQuestionAfterNextPress() {
        var viewModel: GoQuizViewModel? = null
        scenario.onActivity {
            viewModel = ViewModelProvider(it)[GoQuizViewModel::class.java]
        }

        onView(withId(R.id.button_Next)).perform(click())
        onView(withId(R.id.text_question))
            .check(matches(withText(quizViewModel.questionBank[viewModel?.currentIndex!!].textResId)))
    }
}