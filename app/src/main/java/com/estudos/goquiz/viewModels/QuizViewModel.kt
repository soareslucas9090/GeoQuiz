package com.estudos.goquiz.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.estudos.goquiz.data.Questions
import com.estudos.goquiz.infrastructure.Constants
import kotlin.random.Random

/** *Implementação de um dicionário savedStateHandle para salvar os dados do jogo
 * o funcionamento principal consiste em gerar uma lista (used) com a ordem das questões geradas aleatoriamente
 * esta ordem servem como indice para buscar a determinada questão dentro de questionBank e sua resposta
 * */
class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val questionBank = listOf(
        Questions.questions.goQuestions,
        Questions.questions.tecQuestions,
        Questions.questions.sciQuestions,
        Questions.questions.hisQuestions,
        Questions.questions.artQuestions,
        Questions.questions.spoQuestions
    )

    /** *currentIndex indica o index que é usado para iterar em questionBank */
    var currentIndex = 0
        get() = savedStateHandle[Constants.KEY.CURRENT_INDEX_KEY] ?: field
        set(value) = savedStateHandle.set(Constants.KEY.CURRENT_INDEX_KEY, value)

    var currentTypeIndex = 0
        get() = savedStateHandle[Constants.KEY.CURRENT_TYPE_INDEX_KEY] ?: field
        set(value) = savedStateHandle.set(Constants.KEY.CURRENT_TYPE_INDEX_KEY, value)

    /** *usedIndex indica o index que é usado para iterar dentro de used */
    private var usedIndex = 0
        get() = savedStateHandle[Constants.KEY.USED_INDEX_KEY] ?: field
        set(value) = savedStateHandle.set(Constants.KEY.USED_INDEX_KEY, value)

    private var hits = 0
        get() = savedStateHandle[Constants.KEY.HITS_KEY] ?: field
        set(value) = savedStateHandle.set(Constants.KEY.HITS_KEY, value)

    private val usedQuestions: MutableList<Int> = mutableListOf()
        get() = savedStateHandle[Constants.KEY.USED_QUESTION_KEY] ?: field

    private val usedTypeQuestions: MutableList<Int> = mutableListOf()
        get() = savedStateHandle[Constants.KEY.USED_TYPE_QUESTION_KEY] ?: field

    var isCheater: Boolean = false
        get() = savedStateHandle[Constants.KEY.IS_CHEATER_KEY] ?: field
        set(value) = savedStateHandle.set(Constants.KEY.IS_CHEATER_KEY, value)

    var nCheatTokens: Int
        get() = savedStateHandle[Constants.STATEINTENT.NUM_CHEAT_TOKEN] ?: nTotalTokens
        set(value) = savedStateHandle.set(Constants.STATEINTENT.NUM_CHEAT_TOKEN, value)

    private var difficult: Int
        get() = savedStateHandle[Constants.KEY.DIFFICULT_KEY] ?: 3
        set(value) = savedStateHandle.set(Constants.KEY.DIFFICULT_KEY, value)

    val nTotalTokens = 3

    val currentQuestionAnswer: Boolean get() = questionBank[currentTypeIndex][currentIndex].answer
    val currentQuestionText: Int get() = questionBank[currentTypeIndex][currentIndex].textResId
    val sizeQuestionBank: Int get() = questionBank.size
    val currentHits: Int get() = hits

    fun oneMoreHit() = hits++

    fun difficult(): Int {
        return if (difficult == 1) {
            6
        } else {
            if (difficult == 2) {
                8
            } else
                10
        }
    }

    private fun setCurrentIndex() {
        currentIndex = usedQuestions[usedIndex]
        currentTypeIndex = usedTypeQuestions[usedIndex]
    }

    /** *o isCheater = false é para a contagem se o usuário usou cheat naquela pergunta específica
     * Só faz o update se ainda houver questões não mostradas*/
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

    /** * Só faz o previous se houver questões anteriores*/
    fun previous(): Boolean {
        return if (usedIndex > 0) {
            usedIndex--
            setCurrentIndex()
            true
        } else {
            false
        }
    }

    /** * é utilizada a biblioteca random para gerar questionBank.size numeros, que não podem
     * se repetir, que significaram cada index de questionBank
     * Depois disso é feito o salvamento de used em savedStateHandle*/
    fun buildQuestionList() {
        if (usedTypeQuestions.isEmpty()||(usedTypeQuestions.size == 1)) {
            resetGame()
            while (usedTypeQuestions.size < questionBank.size) {
                val numTemp = Random.nextInt(0, (questionBank.size))
                if (!usedTypeQuestions.contains(numTemp)) {
                    usedTypeQuestions.add(numTemp)
                }
            }
            while (usedQuestions.size < difficult()) {
                val numTemp = Random.nextInt(0, (Questions.questions.nQuestionsPerCategory))
                if (!usedQuestions.contains(numTemp)) {
                    usedQuestions.add(numTemp)
                }
            }
            savedStateHandle[Constants.KEY.USED_QUESTION_KEY] = usedQuestions
            savedStateHandle[Constants.KEY.USED_TYPE_QUESTION_KEY] = usedTypeQuestions
            setCurrentIndex()
        }
    }

    fun resetGame() {
        currentIndex = 0
        currentTypeIndex = 0
        usedIndex = 0
        usedQuestions.clear()
        usedTypeQuestions.clear()
        hits = 0
        isCheater = false
        nCheatTokens = nTotalTokens
    }
}