package com.estudos.goquiz.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.estudos.goquiz.data.Questions
import com.estudos.goquiz.infrastructure.Constants
import kotlin.random.Random

/** *Implementação de um dicionário savedStateHandle para salvar os dados do jogo
 * o funcionamento principal consiste em gerar uma lista de tipos de questÕes (usedTypeQuestions)
 * e uma lista dos indices de questões (usedQuestions) geradas aleatoriamente
 * esta ordem servem como indice para buscar a determinada questão dentro de questionBank e sua resposta
 * */
class GoQuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val questionBank = listOf(
        Questions.QuestionsBank.geoQuestions,
        Questions.QuestionsBank.tecQuestions,
        Questions.QuestionsBank.sciQuestions,
        Questions.QuestionsBank.hisQuestions,
        Questions.QuestionsBank.artQuestions,
        Questions.QuestionsBank.spoQuestions
    )

    /** *currentIndex indica o index que é usado para iterar em questionBank.typeQuestions */
    var currentIndex = 0
        get() = savedStateHandle[Constants.KEY.CURRENT_INDEX_KEY] ?: field
        set(value) = savedStateHandle.set(Constants.KEY.CURRENT_INDEX_KEY, value)

    /** *currentTypeIndex indica o index que é usado para iterar entre os typeQuestions de questionBank */
    var currentTypeIndex = 0
        get() = savedStateHandle[Constants.KEY.CURRENT_TYPE_INDEX_KEY] ?: field
        set(value) = savedStateHandle.set(Constants.KEY.CURRENT_TYPE_INDEX_KEY, value)

    /** *usedIndex indica o index que é usado para iterar dentro de usedQuestions e usedTypeQuestions */
    private var usedIndex = 0
        get() = savedStateHandle[Constants.KEY.USED_INDEX_KEY] ?: field
        set(value) = savedStateHandle.set(Constants.KEY.USED_INDEX_KEY, value)

    private var hits = 0
        get() = savedStateHandle[Constants.KEY.HITS_KEY] ?: field
        set(value) = savedStateHandle.set(Constants.KEY.HITS_KEY, value)

    /** *List gerada com os indices das questões */
    private val usedQuestions: MutableList<Int> = mutableListOf()
        get() = savedStateHandle[Constants.KEY.USED_QUESTION_KEY] ?: field

    /** *List gerada com os indices dos tipos das questões */
    private val usedTypeQuestions: MutableList<Int> = mutableListOf()
        get() = savedStateHandle[Constants.KEY.USED_TYPE_QUESTION_KEY] ?: field

    var isCheater: Boolean = false
        get() = savedStateHandle[Constants.KEY.IS_CHEATER_KEY] ?: field
        set(value) = savedStateHandle.set(Constants.KEY.IS_CHEATER_KEY, value)

    var numCheatTokens: Int
        get() = savedStateHandle[Constants.STATEINTENT.NUM_CHEAT_TOKEN] ?: numTotalTokens
        set(value) = savedStateHandle.set(Constants.STATEINTENT.NUM_CHEAT_TOKEN, value)

    var difficult: Int
        get() = savedStateHandle[Constants.KEY.DIFFICULT_KEY] ?: 1
        set(value) = savedStateHandle.set(Constants.KEY.DIFFICULT_KEY, value)

    /** *Variável responsável por armazenar a a quantidade de cheats permitidos */
    val numTotalTokens = 3

    /** *Variável responsável por armazenar a resposta da pergunta atual */
    val currentQuestionAnswer: Boolean get() = questionBank[currentTypeIndex][currentIndex].answer
    /** *Variável responsável por armazenar o texto da pergunta atual */
    val currentQuestionText: Int get() = questionBank[currentTypeIndex][currentIndex].textResId
    val currentHits: Int get() = hits

    fun oneMoreHit() = hits++

    /** *Apenas faz a atualização dos index */
    private fun setCurrentIndex() {
        currentIndex = usedQuestions[usedIndex]
        currentTypeIndex = usedTypeQuestions[usedIndex]
    }

    /** *Função que guarda a quantidade de questões de acordo com a dificuldade escolhida */
    fun difficult(): Int {
        if (difficult == 1) {
            return 6
        } else {
            if (difficult == 2) {
                return 10
            }
            return 14
        }
    }

    /** *o isCheater = false é zerar o resgistro se o usuário usou cheat naquela pergunta específica
     * Só faz o update se ainda houver questões não mostradas*/
    fun update(): Boolean {
        return if (usedIndex < (difficult() - 1)) {
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

    /** * é utilizada a biblioteca random para gerar difficult() numeros, onde a combinação
     * de usedTypeQuestions e usedQuestions não pode ser a mesma.
     * Depois disso é feito o armazenamento dos 'useds' em savedStateHandle*/
    fun buildQuestionList() {
        if (usedTypeQuestions.isEmpty() || (usedTypeQuestions.size == 1)) {
            resetGame()

            while (usedTypeQuestions.size < difficult()) {
                val numTypeTemp = Random.nextInt(0, (questionBank.size))
                val numQuestionsTemp = Random.nextInt(0, (Questions.QuestionsBank.countQuestionPerCategory))
                /** *Teste para impedir repetição de perguntas */
                for (i in 0 until usedTypeQuestions.size){
                    if (usedTypeQuestions.size != 0){
                        if ((usedTypeQuestions[i] == numTypeTemp) && (usedQuestions[i] == numQuestionsTemp)) {
                            continue
                        }
                    }
                }

                usedTypeQuestions.add(numTypeTemp)
                usedQuestions.add(numQuestionsTemp)
            }
            savedStateHandle[Constants.KEY.USED_QUESTION_KEY] = usedQuestions
            savedStateHandle[Constants.KEY.USED_TYPE_QUESTION_KEY] = usedTypeQuestions
            setCurrentIndex()
        }
    }

    /** *Zera e limpa as variáveis para recomeçar o game */
    fun resetGame() {
        currentIndex = 0
        currentTypeIndex = 0
        usedIndex = 0
        usedQuestions.clear()
        usedTypeQuestions.clear()
        hits = 0
        isCheater = false
        numCheatTokens = numTotalTokens
    }
}