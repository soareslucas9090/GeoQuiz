package com.estudos.geoquiz.Views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.estudos.geoquiz.ViewModels.GeoQuizViewModel
import com.estudos.geoquiz.R
import com.estudos.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: GeoQuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        result ->
        if (result.resultCode == RESULT_OK) {
            quizViewModel.isCheater = result.data!!.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        Log.d(TAG, "Create")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buildQuestionList()

        //Implementação com função OnClick
        binding.textQuestion.setOnClickListener(this)
        binding.buttonTrue.setOnClickListener(this)
        binding.buttonFalse.setOnClickListener(this)
        binding.buttonNext.setOnClickListener(this)
        binding.buttonPrev.setOnClickListener(this)
        binding.buttonReset.setOnClickListener(this)
        binding.buttonCheat.setOnClickListener(this)

        //Implementação com Lambda
        /*
        binding.buttonTrue.setOnClickListener {
            response(it)
        }

        binding.buttonFalse.setOnClickListener {
            response(it)
        }

        binding.buttonNext.setOnClickListener {
            update()
        }

        binding.buttonPrev.setOnClickListener {
            previous(it)
        }

        binding.buttonReset.setOnClickListener {
            resetGame(it)
        }

        binding.textQuestion.setOnClickListener {
            update()
        }
         */
    }

    //Função para implementação com lambda

    /*
    private fun response(v:View) {
        var answer = true
        if (v.id == R.id.button_False)
            answer = false

        if (quizViewModel.currentQuestionAnswer == answer) {
            quizViewModel.oneMoreHit()
            Snackbar.make(v, R.string.msgCorrect, Snackbar.LENGTH_SHORT).show()
            update()
        } else {
            Snackbar.make(v, R.string.msgWrong, Snackbar.LENGTH_LONG).show()
            update()
        }
    }

     */

    override fun onClick(v: View) {
        if ((v.id == R.id.button_True) || (v.id == R.id.button_False)) {

            var answer = true
            if (v.id == R.id.button_False)
                answer = false

            if (quizViewModel.currentQuestionAnswer == answer) {
                quizViewModel.oneMoreHit()
                if (quizViewModel.isCheater) {
                    Snackbar.make(v, R.string.judgment_toast, Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(v, R.string.msgCorrect, Snackbar.LENGTH_SHORT).show()
                }
                update()
            } else {
                Snackbar.make(v, R.string.msgWrong, Snackbar.LENGTH_LONG).show()
                update()
            }
        }

        if (v.id == R.id.button_Next) {
            update()
        }

        if (v.id == R.id.button_Prev) {
            previous(v)
        }

        if (v.id == R.id.text_question) {
            update()
        }

        if (v.id == R.id.button_Reset) {
            resetGame(v)
        }

        if (v.id == R.id.button_Cheat) {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }

    }

    private fun update() {
        if (quizViewModel.update()) {
            binding.textQuestion.setText(quizViewModel.currentQuestionText)
        } else
            finishGame()
    }

    private fun previous(v: View) {
        if (quizViewModel.previous())
            binding.textQuestion.setText(quizViewModel.currentQuestionText)
        else
            Snackbar.make(v, R.string.msgNotPrev, Snackbar.LENGTH_LONG).show()
    }

    private fun finishGame() {
        val congratulation = getString(
            R.string.msgCongratulations,
            quizViewModel.currentHits,
            quizViewModel.sizeQuestionBank
        )

        binding.textQuestion.text = congratulation
        binding.buttonPrev.isEnabled = false
        binding.buttonNext.isEnabled = false
        binding.buttonTrue.isEnabled = false
        binding.buttonFalse.isEnabled = false
    }

    private fun buildQuestionList() {
        quizViewModel.buildQuestionList()
        binding.textQuestion.setText(quizViewModel.currentQuestionText)
    }

    private fun resetGame(v: View) {
        quizViewModel.resetGame()
        buildQuestionList()
        Snackbar.make(v, R.string.msgReset, Snackbar.LENGTH_SHORT).show()
        binding.buttonPrev.isEnabled = true
        binding.buttonNext.isEnabled = true
        binding.buttonTrue.isEnabled = true
        binding.buttonFalse.isEnabled = true
    }

    override fun onDestroy() {
        Log.d(TAG, "Destroy")
        super.onDestroy()
    }
}