package com.estudos.geoquiz.Views

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.estudos.geoquiz.R
import com.estudos.geoquiz.ViewModels.CheatViewModel
import com.estudos.geoquiz.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER_IS_TRUE = "com.estudos.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.estudos.geoquiz.answer_shown"


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
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.buttonShowAnswer.setOnClickListener(this)

        if (cheatViewModel.isCheating)
            setAnswerShownResult(cheatViewModel.currentTextCheat)

    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_showAnswer) {
            cheatViewModel.currentTextCheat = when {
                answerIsTrue -> R.string.buttonTrue
                else -> R.string.buttonFalse
            }
            cheatViewModel.isCheating = true
            setAnswerShownResult(cheatViewModel.currentTextCheat)
        }
    }

    private fun setAnswerShownResult(@StringRes answerText: Int){
        binding.textCheat.setText(answerText)
        val intentShowResult = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, true)
        }
        setResult(Activity.RESULT_OK, intentShowResult)
    }
}