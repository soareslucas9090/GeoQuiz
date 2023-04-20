package com.estudos.geoquiz

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.estudos.geoquiz.databinding.ActivityCheatBinding
import com.estudos.geoquiz.databinding.ActivityMainBinding

class CheatActivity : AppCompatActivity() {

    lateinit var binding: ActivityCheatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}