package com.estudos.goquiz.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.estudos.goquiz.R
import com.estudos.goquiz.databinding.ActivityDifficultyBinding
import com.estudos.goquiz.databinding.ActivityMainBinding

class DifficultyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDifficultyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)

        binding = ActivityDifficultyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}