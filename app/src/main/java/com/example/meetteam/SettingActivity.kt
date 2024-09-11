package com.example.meetteam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meetteam.databinding.ActivitySettingBinding
import com.example.meetteam.databinding.ActivitySignupBinding

class SettingActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}