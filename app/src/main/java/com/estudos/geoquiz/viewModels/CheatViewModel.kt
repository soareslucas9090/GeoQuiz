package com.estudos.geoquiz.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.estudos.geoquiz.infrastructure.Constants
import com.estudos.geoquiz.R

/** *Não possui lógica interna, apenas guarda e retorna os valores para CheatActivity
 * Com currentTextCheat em específico, caso o usuário ainda não tenha cheatado,
 * o retorno será R.string.warning_text*/
class CheatViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    var isCheating: Boolean
        get() = savedStateHandle[Constants.KEY.IS_CHEATING_KEY] ?: false
        set(value) = savedStateHandle.set(Constants.KEY.IS_CHEATING_KEY, value)

    var currentTextCheat : Int
        get() = savedStateHandle[Constants.KEY.CURRENT_TEXT_CHEAT_KEY] ?: R.string.warning_text
        set(value) = savedStateHandle.set(Constants.KEY.CURRENT_TEXT_CHEAT_KEY, value)
}