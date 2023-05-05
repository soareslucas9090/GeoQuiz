package com.estudos.goquiz.infrastructure

import android.content.Context
import android.content.SharedPreferences
import com.estudos.goquiz.R

class SharedPreferencesGoQuiz(context: Context) {

    private val preference: SharedPreferences =
        context.getSharedPreferences("${R.string.app_name}", Context.MODE_PRIVATE)

    fun storeString(key: String, isDifficultySet: Boolean){
        preference.edit().putBoolean(key, isDifficultySet).apply()
    }

    fun getBoolean(key:String): Boolean{
        return preference.getBoolean(key, false)
    }

}