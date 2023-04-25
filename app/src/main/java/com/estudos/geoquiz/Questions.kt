package com.estudos.geoquiz

import androidx.annotation.StringRes

/** *data class usada somente para armazenar o texto RES da questão, e sua resposta */
data class Questions(@StringRes val textResId: Int, val answer: Boolean) {



}