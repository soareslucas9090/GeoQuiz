package com.estudos.goquiz.data

import androidx.annotation.StringRes
import com.estudos.goquiz.R

/** *data class usada somente para armazenar o texto RES da questão, e sua resposta */
class Questions private constructor(@StringRes val textResId: Int, val answer: Boolean) {

    object QuestionsBank {

        const val countQuestionPerCategory = 6

        val geoQuestions = listOf(
            Questions(R.string.question_geo1, true),
            Questions(R.string.question_geo2, true),
            Questions(R.string.question_geo3, false),
            Questions(R.string.question_geo4, false),
            Questions(R.string.question_geo5, true),
            Questions(R.string.question_geo6, true)
        )

        val tecQuestions = listOf(
            Questions(R.string.question_tec1, true),
            Questions(R.string.question_tec2, true),
            Questions(R.string.question_tec3, true),
            Questions(R.string.question_tec4, false),
            Questions(R.string.question_tec5, true),
            Questions(R.string.question_tec6, true)
        )

        val sciQuestions = listOf(
            Questions(R.string.question_sci1, true),
            Questions(R.string.question_sci2, false),
            Questions(R.string.question_sci3, true),
            Questions(R.string.question_sci4, true),
            Questions(R.string.question_sci5, false),
            Questions(R.string.question_sci6, false)
        )

        val hisQuestions = listOf(
            Questions(R.string.question_his1, true),
            Questions(R.string.question_his2, true),
            Questions(R.string.question_his3, true),
            Questions(R.string.question_his4, true),
            Questions(R.string.question_his5, false),
            Questions(R.string.question_his6, true)
        )

        val artQuestions = listOf(
            Questions(R.string.question_art1, true),
            Questions(R.string.question_art2, false),
            Questions(R.string.question_art3, true),
            Questions(R.string.question_art4, true),
            Questions(R.string.question_art5, false),
            Questions(R.string.question_art6, true)
        )

        val spoQuestions = listOf(
            Questions(R.string.question_spo1, true),
            Questions(R.string.question_spo2, false),
            Questions(R.string.question_spo3, true),
            Questions(R.string.question_spo4, true),
            Questions(R.string.question_spo5, true),
            Questions(R.string.question_spo6, false)
        )
    }

}