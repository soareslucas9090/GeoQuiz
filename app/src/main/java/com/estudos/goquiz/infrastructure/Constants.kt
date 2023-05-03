package com.estudos.goquiz.infrastructure

/** *Classe estática usada apenas para guardar valores de constantes*/
class Constants private constructor() {

    object KEY {
        /** *Listagem de keys para uso do savedState*/
        const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
        const val CURRENT_TYPE_INDEX_KEY = "CURRENT_TYPE_INDEX_KEY"
        const val USED_INDEX_KEY = "USED_INDEX_KEY"
        const val HITS_KEY = "HITS_KEY"
        const val USED_QUESTION_KEY = "USED_QUESTION_KEY"
        const val USED_TYPE_QUESTION_KEY = "USED_TYPE_QUESTION_KEY"
        const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
        const val IS_CHEATING_KEY = "IS_CHEATING_KEY"
        const val CURRENT_TEXT_CHEAT_KEY = "CURRENT_TEXT_CHEAT_KEY"
        const val DIFFICULT_KEY = "DIFFICULT_KEY"
    }

    object STATEINTENT {
        /** *Constantes usadas para transitar valores por meio das intents */
        const val NUM_CHEAT_TOKEN = "NUM_CHEAT_TOKEN"
        const val EXTRA_ANSWER_IS_TRUE = "com.estudos.goquiz.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.estudos.goquiz.answer_shown"
    }

}