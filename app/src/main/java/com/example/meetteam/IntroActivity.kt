package com.example.meetteam

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.meetteam.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    private val kakaoAuthViewModel: KakaoAuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kakaoLoginButton.setOnClickListener {
            kakaoAuthViewModel.handleKakaoLogin()
            // 카카오 로그인 로직 추가
        }

        binding.idLoginButton.setOnClickListener {
            val intent = Intent(this@IntroActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
