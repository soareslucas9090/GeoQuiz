package com.estudos.geoquiz

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Questions(R.string.question_australia, true),
        Questions(R.string.question_oceans, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_americas, true),
        Questions(R.string.question_asia, true)
    )

    private var currentIndex = 0
    private var usedIndex = 0

    private var hits = 0

    private var used = mutableListOf(currentIndex)

    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int get() = questionBank[currentIndex].textResId
    val sizeQuestionBank: Int get() = questionBank.size
    val currentUsedIndex: Int get() = usedIndex
    val currentHits: Int get() = hits

    fun oneMoreHit() = hits++
    fun oneMoreUsed() = usedIndex++
    fun oneLessUsed() = usedIndex--
    fun setCurrentIndex() { currentIndex = used[usedIndex] }

    fun buildQuestionList(){
        used.clear()
        while (used.size < questionBank.size) {
            val numTemp = Random.nextInt(0, (questionBank.size))
            if (!used.contains(numTemp)) {
                used.add(numTemp)
            }
        }
        setCurrentIndex()
    }

    fun resetGame() {
        currentIndex = 0
        usedIndex = 0
        used.clear()
        hits = 0
    }

}