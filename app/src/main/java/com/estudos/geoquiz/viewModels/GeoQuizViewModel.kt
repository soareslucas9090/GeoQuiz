package com.estudos.geoquiz.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.estudos.geoquiz.Questions
import com.estudos.geoquiz.R
import kotlin.random.Random

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val USED_INDEX_KEY = "USED_INDEX_KEY"
const val HITS_KEY = "HITS_KEY"
const val USED_KEY = "USED_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
const val NUM_CHEAT_TOKEN = "NUM_CHEAT_TOKEN"

class GeoQuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val questionBank = listOf(
        Questions(R.string.question_australia, true),
        Questions(R.string.question_oceans, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_americas, true),
        Questions(R.string.question_asia, true)
    )

    var currentIndex = 0
        get() = savedStateHandle[CURRENT_INDEX_KEY] ?: field
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)


    private var usedIndex = 0
        get() = savedStateHandle[USED_INDEX_KEY] ?: field
        set(value) = savedStateHandle.set(USED_INDEX_KEY, value)

    private var hits = 0
        get() = savedStateHandle[HITS_KEY] ?: field
        set(value) = savedStateHandle.set(HITS_KEY, value)

    private val used: MutableList<Int> = mutableListOf()
        get() = savedStateHandle[USED_KEY] ?: field

    var isCheater: Boolean
        get() = savedStateHandle[IS_CHEATER_KEY] ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    var nCheatTokens: Int
        get() = savedStateHandle[NUM_CHEAT_TOKEN] ?: nTotalTokens
        set(value) = savedStateHandle.set(NUM_CHEAT_TOKEN, value)

    val nTotalTokens = 3

    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int get() = questionBank[currentIndex].textResId
    val sizeQuestionBank: Int get() = questionBank.size
    val currentHits: Int get() = hits

    fun oneMoreHit() = hits++
    private fun setCurrentIndex() {
        currentIndex = used[usedIndex]
    }

    fun update(): Boolean {
        return if (usedIndex < (questionBank.size - 1)) {
            usedIndex++
            setCurrentIndex()
            isCheater = false
            true
        } else {
            false
        }
    }

    fun previous(): Boolean {
        return if (usedIndex > 0) {
            usedIndex--
            setCurrentIndex()
            true
        } else {
            false
        }
    }

    fun buildQuestionList() {
        if (used.isEmpty()||(used.size == 1)) {
            resetGame()
            while (used.size < questionBank.size) {
                val numTemp = Random.nextInt(0, (questionBank.size))
                if (!used.contains(numTemp)) {
                    used.add(numTemp)
                }
            }
            savedStateHandle[USED_KEY] = used
            setCurrentIndex()
        }
    }

    fun resetGame() {
        currentIndex = 0
        usedIndex = 0
        used.clear()
        hits = 0
        isCheater = false
        nCheatTokens = nTotalTokens
        buildQuestionList()
    }
}