package com.estudos.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.estudos.geoquiz.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER_IS_TRUE = "com.estudos.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.estudos.geoquiz.answer_shown"


class CheatActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.buttonShowAnswer.setOnClickListener(this)

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
            val answerText = when {
                answerIsTrue -> R.string.buttonTrue
                else -> R.string.buttonFalse
            }
            binding.textCheat.setText(answerText)
            setAnswerShowResult(true)
        }
    }

    private fun setAnswerShowResult(isAnswerShown: Boolean){
        val intentShowResult = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, intentShowResult)
    }

}