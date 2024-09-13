package com.example.meetteam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.meetteam.databinding.ActivityLoginBinding
import com.example.meetteam.network.ApiService
import com.example.meetteam.network.LoginRequest
import com.example.meetteam.network.LoginResponse
import com.example.meetteam.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val kakaoAuthViewModel: KakaoAuthViewModel by viewModels()

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameEditText: EditText = binding.username
        val passwordEditText: EditText = binding.password
        val loginButton: Button = binding.loginButton
        val backButton: Button = binding.backButton
        val kakaoLoginButton: Button = binding.kakaoLoginButton2

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
            //로그인 서버 통신

            loginFun(username, password)

            if (username == "test@test.com" && password == "123") {
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
            kakaoAuthViewModel.handleKakaoLogin(this@LoginActivity)
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

    private fun loginFun(username: String, password: String) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)

        // Create the login request with the provided username and password
        val loginRequest = LoginRequest(
            email = username,
            password = password
        )
        Log.d(TAG, "로그인 요청 시작: username=$username, password=$password")
        // Make the login API call
        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    Log.e(TAG, "사용자 정보 요청 성공")

                    //Toast.makeText(requireContext(), "로그인 성공!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "사용자 정보 요청 실패1")
                    //Toast.makeText(requireContext(), "로그인 실패: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "사용자 정보 요청 실패2")
                Log.e(TAG, "password")
                //Toast.makeText(requireContext(), "로그인 중 오류 발생: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
