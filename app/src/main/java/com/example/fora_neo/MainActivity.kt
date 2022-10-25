package com.example.fora_neo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.red
import com.example.fora_neo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}