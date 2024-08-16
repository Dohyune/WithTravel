package com.example.meetteam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.meetteam.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val kakaoAuthViewModel: KakaoAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameEditText: EditText = binding.username
        val passwordEditText: EditText = binding.password
        val loginButton: Button = binding.loginButton
        val backButton: Button = binding.backButton
        val kakaoLoginButton: Button = binding.kakaoLoginButton

        // 카카오 로그인 상태 체크 및 처리
        lifecycleScope.launch {
            kakaoAuthViewModel.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    navigateToMainActivity()
                }
            }
        }

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username == "user" && password == "password") {
                val sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // 비밀번호가 틀린 경우
                binding.wrongAlert.visibility = View.VISIBLE
            }
        }

        usernameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.wrongAlert.visibility = View.GONE
            }
        }
        passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.wrongAlert.visibility = View.GONE
            }
        }

        // 카카오 로그인 버튼 클릭 리스너
        kakaoLoginButton.setOnClickListener {
            kakaoAuthViewModel.handleKakaoLogin()
        }

        // 뒤로가기 버튼 클릭 리스너
        backButton.setOnClickListener {
            finish()  // 현재 액티비티를 종료하고 이전 액티비티로 돌아감
        }
    }
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
