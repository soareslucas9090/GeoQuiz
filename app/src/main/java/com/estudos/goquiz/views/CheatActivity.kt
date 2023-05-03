package com.estudos.goquiz.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.estudos.goquiz.R
import com.estudos.goquiz.viewModels.CheatViewModel
import com.estudos.goquiz.databinding.ActivityCheatBinding
import com.estudos.goquiz.infrastructure.Constants

/** *Implementação com OnClickListener novamente, como na Main Activity */
class CheatActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false
    private val cheatViewModel: CheatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //é usando intent. e não Intent, pois com o "i" minusculo, se refere a uma instância específica
        //e com "I" é um método estático.
        answerIsTrue = intent.getBooleanExtra(Constants.STATEINTENT.EXTRA_ANSWER_IS_TRUE, false)

        binding.buttonShowAnswer.setOnClickListener(this)

        /** *Caso o viewModel acuse cheat, é chamado direto a função setAnswerShownResult
         * Serve para recuperar valores em caso de onDestroy da activity*/
        if (cheatViewModel.isCheating)
            setAnswerShownResult(cheatViewModel.currentTextCheat)

    }

    /** *Objeto de classe para guardar a função de criação de uma nova intent com um putExtra
     * No caso, EXTRA_ANSWER_IS_TRUE, e o valor de answerIsTrue passado*/
    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(Constants.STATEINTENT.EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    override fun onClick(v: View) {
    /** *Sempre que o usuário ver uma resposta, é atualizado este dado na viewModel e chamado a função setAnswerShownResult*/
        if (v.id == R.id.button_showAnswer) {
            cheatViewModel.currentTextCheat = when {
                answerIsTrue -> R.string.buttonTrue
                else -> R.string.buttonFalse
            }
            cheatViewModel.isCheating = true
            setAnswerShownResult(cheatViewModel.currentTextCheat)
        }
    }

    /** *Função usada para retornar o resultado do cheat de usuário
     * Sempre que for chamado, retornará um Extra true, pois a condição em MainActivity analisa se o resultado
     * é Activity.RESULT_OK. O retorno automático, caso esta função não seja chamada, é Activity.RESULT_CANCELED*/
    private fun setAnswerShownResult(@StringRes answerText: Int){
        binding.textCheat.setText(answerText)
        val intentShowResult = Intent().apply {
            putExtra(Constants.STATEINTENT.EXTRA_ANSWER_SHOWN, true)
        }
        setResult(Activity.RESULT_OK, intentShowResult)
    }
}