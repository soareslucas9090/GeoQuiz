package com.estudos.goquiz.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.annotation.StringRes
import com.estudos.goquiz.R
import com.estudos.goquiz.databinding.ActivityDifficultyBinding
import com.estudos.goquiz.databinding.ActivityMainBinding
import com.estudos.goquiz.infrastructure.Constants
import com.estudos.goquiz.viewModels.GoQuizViewModel

class DifficultyActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityDifficultyBinding
    private var numDifficulty = 1
    private var possibilityOfDifficulty = intArrayOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)

        binding = ActivityDifficultyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        numDifficulty = intent.getIntExtra(Constants.STATEINTENT.NUM_OF_DIFFICULTY, 1)
        possibilityOfDifficulty = intent.getIntArrayExtra(Constants.STATEINTENT.POSSIBILITY_OF_DIFFICULTY)!!

        binding.buttonSave.setOnClickListener(this)
        binding.radioButtonEasy.setOnClickListener(this)
        binding.radioButtonMedium.setOnClickListener(this)
        binding.radioButtonHard.setOnClickListener(this)

        if (numDifficulty == 1) {
            binding.radioGroupChoiceDifficulty.check(R.id.radioButton_easy)
            setMsgDifficult(numDifficulty)
        } else {
            if (numDifficulty == 2) {
                binding.radioGroupChoiceDifficulty.check(R.id.radioButton_medium)
                setMsgDifficult(numDifficulty)
            } else {
                binding.radioGroupChoiceDifficulty.check(R.id.radioButton_hard)
                setMsgDifficult(numDifficulty)
            }
        }

    }

    companion object {
        fun newIntent(packageContext: Context, numDifficulty: Int, possibilityOfDifficulty: IntArray): Intent {
            return Intent(packageContext, DifficultyActivity::class.java).apply {
                putExtra(Constants.STATEINTENT.NUM_OF_DIFFICULTY, numDifficulty)
                putExtra(Constants.STATEINTENT.POSSIBILITY_OF_DIFFICULTY, possibilityOfDifficulty)
            }
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            val choice = if (binding.radioGroupChoiceDifficulty.checkedRadioButtonId == R.id.radioButton_easy)
                1
            else {
                if (binding.radioGroupChoiceDifficulty.checkedRadioButtonId == R.id.radioButton_medium)
                    2
                else
                    3
            }
            val intentShowResult = Intent().apply {
                putExtra(Constants.STATEINTENT.NUM_OF_DIFFICULTY, choice)
            }
            setResult(Activity.RESULT_OK, intentShowResult)
            Log.d(Constants.TAG.DIFFICULTY_ACTIVITY_TAG,"Cheguei")
            finish()
        }
        if (v.id == R.id.radioButton_easy){
            setMsgDifficult(1)
        }
        if (v.id == R.id.radioButton_medium){
            setMsgDifficult(2)
        }
        if (v.id == R.id.radioButton_hard){
            setMsgDifficult(3)
        }
    }

    private fun setMsgDifficult(difficulty: Int){
        if (difficulty == 1) {
            val temp = getString(R.string.msgWithoutCheatDifficulty, possibilityOfDifficulty[0])
            binding.textMsgDifficulty.text = temp
        } else {
            if (difficulty == 2) {
                val temp = getString(R.string.msgWithoutCheatDifficulty, possibilityOfDifficulty[1])
                binding.textMsgDifficulty.text = temp
            } else {
                val temp = getString(R.string.msgWithCheatDifficulty, possibilityOfDifficulty[2])
                binding.textMsgDifficulty.text = temp
            }
        }
    }

}