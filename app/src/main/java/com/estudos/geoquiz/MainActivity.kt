package com.estudos.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.estudos.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buildQuestionList()

        binding.buttonTrue.setOnClickListener(this)
        binding.buttonFalse.setOnClickListener(this)
        binding.buttonNext.setOnClickListener(this)
        binding.buttonPrev.setOnClickListener(this)
        binding.buttonReset.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        if ((v.id == R.id.button_True) || (v.id == R.id.button_False)){

            var answer = true
            if(v.id == R.id.button_False)
                answer = false

            if (quizViewModel.currentQuestionAnswer == answer) {
                quizViewModel.oneMoreHit()
                Snackbar.make(v, R.string.msgCorrect, Snackbar.LENGTH_SHORT).show()
                update()
            }

            else{
                Snackbar.make(v, R.string.msgWrong, Snackbar.LENGTH_LONG).show()
                update()
            }

        }

        if (v.id == R.id.button_Next){
            update()
        }

        if (v.id == R.id.button_Prev){
            previous(v)
        }

        if (v.id == R.id.text_question){
            update()
        }

        if (v.id == R.id.button_Reset){
            resetGame(v)
        }

    }

    private fun update(){
        if(quizViewModel.currentUsedIndex < (quizViewModel.sizeQuestionBank - 1)) {
            quizViewModel.oneMoreUsed()
            quizViewModel.setCurrentIndex()
            binding.textQuestion.setText(quizViewModel.currentQuestionText)
        }
        else {
            finishGame()
        }
    }

    private fun previous(v: View){
        if (quizViewModel.currentUsedIndex > 0){
            quizViewModel.oneLessUsed()
            quizViewModel.setCurrentIndex()
            binding.textQuestion.setText(quizViewModel.currentQuestionText)
        } else {
            Snackbar.make(v, R.string.msgNotPrev, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun finishGame(){
        val congratulation = getString(R.string.msgCongratulations, quizViewModel.currentHits, quizViewModel.sizeQuestionBank)
        binding.textQuestion.text = congratulation
        binding.buttonPrev.isEnabled = false
        binding.buttonNext.isEnabled = false
        binding.buttonTrue.isEnabled = false
        binding.buttonFalse.isEnabled = false
    }

    private fun buildQuestionList(){
        quizViewModel.buildQuestionList()
        binding.textQuestion.setText(quizViewModel.currentQuestionText)
    }

    private fun resetGame(v: View){
        quizViewModel.resetGame()
        buildQuestionList()
        Snackbar.make(v, R.string.msgReset, Snackbar.LENGTH_SHORT).show()
        binding.buttonPrev.isEnabled = true
        binding.buttonNext.isEnabled = true
        binding.buttonTrue.isEnabled = true
        binding.buttonFalse.isEnabled = true
    }
}