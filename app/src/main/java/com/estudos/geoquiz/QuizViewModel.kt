package com.estudos.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.random.Random

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val USED_INDEX_KEY = "USED_INDEX_KEY"
const val HITS_KEY = "HITS_KEY"
const val USED_KEY = "USED_KEY"
private const val TAG = "QuizViewModel"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val questionBank = listOf(
        Questions(R.string.question_australia, true),
        Questions(R.string.question_oceans, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_americas, true),
        Questions(R.string.question_asia, true)
    )

    private var currentIndex = 0
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
    }
}