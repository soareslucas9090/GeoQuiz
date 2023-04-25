package com.estudos.geoquiz.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.estudos.geoquiz.R

/** *Listagem de keys para uso do savedState*/
const val IS_CHEATING_KEY = "IS_CHEATING_KEY"
const val CURRENT_TEXT_CHEAT_KEY = "CURRENT_TEXT_CHEAT_KEY"

/** *Não possui lógica interna, apenas guarda e retorna os valores para CheatActivity
 * Com currentTextCheat em específico, caso o usuário ainda não tenha cheatado,
 * o retorno será R.string.warning_text*/
class CheatViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    var isCheating: Boolean
        get() = savedStateHandle[IS_CHEATING_KEY] ?: false
        set(value) = savedStateHandle.set(IS_CHEATING_KEY, value)

    var currentTextCheat : Int
        get() = savedStateHandle[CURRENT_TEXT_CHEAT_KEY] ?: R.string.warning_text
        set(value) = savedStateHandle.set(CURRENT_TEXT_CHEAT_KEY, value)
}