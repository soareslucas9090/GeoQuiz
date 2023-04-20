package com.estudos.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*
import org.junit.Test

class GeoQuizViewModelTest{

    @Test
    fun providesExpectedQuestionText(){
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 4))
        val quizViewModel = GeoQuizViewModel(savedStateHandle)

        assertEquals(R.string.question_americas, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
    }


}