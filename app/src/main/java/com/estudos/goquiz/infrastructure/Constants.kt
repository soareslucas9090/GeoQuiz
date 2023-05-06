package com.estudos.goquiz.infrastructure

/** *Classe estática usada apenas para guardar valores de constantes*/
class Constants private constructor() {

    /** *Listagem de keys para uso do savedState*/
    object KEY {

        const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
        const val CURRENT_TYPE_INDEX_KEY = "CURRENT_TYPE_INDEX_KEY"
        const val USED_INDEX_KEY = "USED_INDEX_KEY"
        const val HITS_KEY = "HITS_KEY"
        const val USED_QUESTION_KEY = "USED_QUESTION_KEY"
        const val USED_TYPE_QUESTION_KEY = "USED_TYPE_QUESTION_KEY"
        const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
        const val IS_CHEATING_KEY = "IS_CHEATING_KEY"
        const val CURRENT_TEXT_CHEAT_KEY = "CURRENT_TEXT_CHEAT_KEY"
        const val DIFFICULTY_KEY = "DIFFICULTY_KEY"
        const val NUM_OF_DIFFICULTY_KEY = "NUM_OF_DIFFICULTY_KEY"
    }

    /** *Constantes usadas para transitar valores por meio das intents */
    object STATEINTENT {
        const val NUM_CHEAT_TOKEN = "NUM_CHEAT_TOKEN"
        const val EXTRA_ANSWER_IS_TRUE = "com.estudos.goquiz.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.estudos.goquiz.answer_shown"
        const val NUM_OF_DIFFICULTY = "com.estudos.goquiz.num_of_difficulty"
        const val NUM_OF_DIFFICULTY_CHOSEN = "com.estudos.goquiz.num_of_difficulty_chosen"
        const val POSSIBILITY_OF_DIFFICULTY = "com.estudos.goquiz.possibility_of_difficulty"
    }

    /** *Constantes usadas TAGS de Log de depuração */
    object TAG {
        const val GEO_QUIZ_VIEW_MODEL_TAG = "GoQuizViewModel"
        const val MAIN_ACTIVITY_TAG = "MainActivity"
        const val DIFFICULTY_ACTIVITY_TAG = "DifficultyActivity"
    }
}