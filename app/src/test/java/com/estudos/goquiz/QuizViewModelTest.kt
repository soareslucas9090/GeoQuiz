package com.estudos.goquiz

import androidx.lifecycle.SavedStateHandle
import com.estudos.goquiz.infrastructure.Constants.KEY.CURRENT_INDEX_KEY
import com.estudos.goquiz.viewModels.QuizViewModel
import org.junit.Assert.*
import org.junit.Test

class QuizViewModelTest{

    @Test
    fun providesExpectedQuestionText(){
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 4))
        val quizViewModel = QuizViewModel(savedStateHandle)

        assertEquals(R.string.question_americas, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
    }


}