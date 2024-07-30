package com.example.meetteam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.meetteam.databinding.ActivityIntroBinding
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.launch

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    private val kakaoAuthViewModel: KakaoAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //로그인 확인하기
        lifecycleScope.launch{
            kakaoAuthViewModel.isLoggedIn.collect{ isLoggedIn ->
                if(isLoggedIn) {
                    navigateToMainActivity()
                }
            }
        }

        binding.kakaoLoginButton.setOnClickListener {
            Log.i("KakaoLogin","Kakao Login button pressed")
            kakaoAuthViewModel.handleKakaoLogin()
        }

        binding.idLoginButton.setOnClickListener {
            val intent = Intent(this@IntroActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
