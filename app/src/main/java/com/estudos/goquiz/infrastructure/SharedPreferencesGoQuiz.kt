package com.estudos.goquiz.infrastructure

import android.content.Context
import android.content.SharedPreferences
import com.estudos.goquiz.R

class SharedPreferencesGoQuiz(context: Context) {

    private val preference: SharedPreferences =
        context.getSharedPreferences("${R.string.app_name}", Context.MODE_PRIVATE)

    fun storeInt(key: String, difficultySet: Int){
        preference.edit().putInt(key, difficultySet).apply()
    }

    fun getInt(key:String): Int{
        return preference.getInt(key, 0)
    }

}