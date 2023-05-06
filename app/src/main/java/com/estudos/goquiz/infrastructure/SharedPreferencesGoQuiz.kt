package com.estudos.goquiz.infrastructure

import android.content.Context
import android.content.SharedPreferences
import com.estudos.goquiz.R

/** *Classe usada para manipular a dificuldade escolhida pelo usuário em uma SharedPreference */
class SharedPreferencesGoQuiz(context: Context) {

    /** *MODE_PRIVATE para deixar este valor seguro de outros apps */
    private val preference: SharedPreferences =
        context.getSharedPreferences("${R.string.app_name}", Context.MODE_PRIVATE)

    fun storeInt(key: String, difficultySet: Int){
        preference.edit().putInt(key, difficultySet).apply()
    }

    /** *O valor default é 0 para a ActivityMain saber quando o usuário nunca escolheu uma dificuldade */
    fun getInt(key:String): Int{
        return preference.getInt(key, 0)
    }

}