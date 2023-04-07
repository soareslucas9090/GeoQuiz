package com.estudos.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.estudos.geoquiz.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

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

    private var hits = 0

    private var prev = mutableListOf(currentIndex)
    private var prevIndex = 0

    private var used = mutableListOf(currentIndex)

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTrue.setOnClickListener(this)
        binding.buttonFalse.setOnClickListener(this)
        binding.buttonNext.setOnClickListener(this)
        binding.buttonPrev.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        if ((v.id == R.id.button_True) || (v.id == R.id.button_False)){

            var answer = true
            if(v.id == R.id.button_False)
                answer = false

            if (questionBank[currentIndex].answer == answer) {
                Snackbar.make(v, R.string.msgCorrect, Snackbar.LENGTH_SHORT).show()
                hits++
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

        if (v.id == R.id.text_question){
            update()
        }

        if (v.id == R.id.button_Prev){
            previous(v)
        }

    }

    private fun update(){
        if(used.size != questionBank.size) {
            var numTemp = Random.nextInt(0, (questionBank.size-1))
            while (used.contains(numTemp)) {
                numTemp = Random.nextInt(0, (questionBank.size-1))
            }
            used.add(numTemp)
            currentIndex = numTemp
            binding.textQuestion.setText(questionBank[currentIndex].textResId)
            prevIndex+=1
            prev.add(prevIndex, currentIndex)
        }
        else {
            finishGame()
        }
    }

    private fun previous(v: View){
        if (prevIndex > 0){
            prevIndex-=1
            currentIndex = prev[prevIndex]
            binding.textQuestion.setText(questionBank[currentIndex].textResId)
            used.removeLast()
        } else {
            Snackbar.make(v, R.string.notPrev, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun finishGame(){
        currentIndex = 0
        prevIndex = 0
        prev.clear()
        used.clear()
        Toast.makeText(this,"Parabens, voce acertou ${hits} de ${questionBank.size}!",Toast.LENGTH_LONG)
    }
}