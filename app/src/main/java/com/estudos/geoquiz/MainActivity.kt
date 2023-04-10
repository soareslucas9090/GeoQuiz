package com.estudos.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.estudos.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Questions(R.string.question_australia, true),
        Questions(R.string.question_oceans, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_americas, true),
        Questions(R.string.question_asia, true))
    private var currentIndex = 0
    private var currentUsedIndex = 0

    private var hits = 0

    private var used = mutableListOf(currentIndex)

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

            if (questionBank[currentIndex].answer == answer) {
                hits++
                Log.d(TAG, "Hits atualizado para ${hits}")
                Snackbar.make(v, R.string.msgCorrect, Snackbar.LENGTH_SHORT).show()
                update()
            }

            else{
                Snackbar.make(v, R.string.msgWrong, Snackbar.LENGTH_LONG).show()
                Log.d(TAG, "Errou")
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
        if(currentUsedIndex < (questionBank.size - 1)) {
            currentUsedIndex++
            Log.d(TAG, "${currentIndex} -- ${currentUsedIndex}")
            currentIndex = used[currentUsedIndex]
            binding.textQuestion.setText(questionBank[currentIndex].textResId)
        }
        else {
            finishGame()
        }
    }

    private fun previous(v: View){
        if (currentUsedIndex > 0){
            currentUsedIndex--
            currentIndex = used[currentUsedIndex]
            binding.textQuestion.setText(questionBank[currentIndex].textResId)
        } else {
            Snackbar.make(v, R.string.msgNotPrev, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun finishGame(){
        binding.textQuestion.text = "Parabens, voce acertou ${hits} de ${questionBank.size}!"
        binding.buttonPrev.isEnabled = false
        binding.buttonNext.isEnabled = false
        binding.buttonTrue.isEnabled = false
        binding.buttonFalse.isEnabled = false
    }

    private fun buildQuestionList(){
        used.clear()
        while (used.size < 6) {
            val numTemp = Random.nextInt(0, (questionBank.size))
            if (!used.contains(numTemp)) {
                used.add(numTemp)
            }
        }
        Log.d(TAG, "${used}")
        currentIndex = used[currentUsedIndex]
        binding.textQuestion.setText(questionBank[currentIndex].textResId)
    }

    private fun resetGame(v: View){
        currentIndex = 0
        currentUsedIndex = 0
        used.clear()
        hits = 0
        buildQuestionList()
        Snackbar.make(v, R.string.msgReset, Snackbar.LENGTH_SHORT).show()
        binding.buttonPrev.isEnabled = true
        binding.buttonNext.isEnabled = true
        binding.buttonTrue.isEnabled = true
        binding.buttonFalse.isEnabled = true
    }
}