package com.example.withtravel

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
import com.example.withtravel.databinding.ActivityLoginBinding
import com.example.withtravel.network.ApiService
import com.example.withtravel.network.LoginRequest
import com.example.withtravel.network.LoginResponse
import com.example.withtravel.network.MemberSignupRequestDto
import com.example.withtravel.network.MemberSignupResponseDto
import com.example.withtravel.network.RetrofitClient
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
            //MemberSignupRequestDtoFun(username,password,"01011111111","LOCAL")

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

        // 회원가입 버튼
        binding.signupButton.setOnClickListener{
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
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
                    Log.d(TAG, "Response Code: ${response.code()}")
                    Log.d(TAG, "Response Body: ${response.body()}")

                } else {
                    Log.d(TAG, "Response Code: ${response.code()}")
                    Log.d(TAG, "Response Body: ${response.body()}")
                    Log.e(TAG, "사용자 정보 요청 실패1")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "사용자 정보 요청 실패2")
                Log.e(TAG, "password")
                // 네트워크 오류 등 처리
            }
        })
    }

    //api연결함수 형식

    private fun MemberSignupRequestDtoFun(username: String, password: String, phoneNumber:String,loginType:String) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)

        // Create the login request with the provided username and password
        val memberSignupRequest = MemberSignupRequestDto(
            email = username,
            password = password,
            phoneNumber = phoneNumber,
            loginType = loginType
        )
        Log.d(TAG, "로그인 요청 시작: username=$username, password=$password")
        // Make the login API call
        apiService.signup(memberSignupRequest).enqueue(object : Callback<MemberSignupResponseDto> {
            override fun onResponse(call: Call<MemberSignupResponseDto>, response: Response<MemberSignupResponseDto>) {
                Log.d(TAG, "Response Code: ${response.code()}")
                Log.d(TAG, "Response Body: ${response.body()}")

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (response.body()?.isSuccess == true) {  // boolean 비교로 변경
                        Log.d(TAG, "사용자 정보 요청 성공")
                    } else {
                        Log.e(TAG, "사용자 정보 요청 실패: isSuccess is false")
                        Log.e(TAG, "Error message: ${response.body()?.isSuccess}")  // 에러 메시지가 있다면 출력
                    }
                } else {
                    Log.e(TAG, "사용자 정보 요청 실패: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<MemberSignupResponseDto>, t: Throwable) {
                Log.e(TAG, "사용자 정보 요청 실패2")
                Log.e(TAG, "password")
                // 네트워크 오류 등 처리
            }
        })
    }
}
