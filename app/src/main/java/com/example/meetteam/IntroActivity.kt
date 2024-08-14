package com.example.meetteam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
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

        //viewpager2 adapter 설정
        val onboardingAdapter = OnboardingAdapter(this)
        binding.viewPager.adapter = onboardingAdapter

        // 페이지 인디케이터 초기화
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
            }
        })

        kakaoAuthViewModel.setActivityContext(this)  // Activity 컨텍스트 전달

        lifecycleScope.launch {
            kakaoAuthViewModel.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    navigateToMainActivity()
                }
            }
        }

        binding.kakaoLoginButton.setOnClickListener {
            Log.i("KakaoLogin", "Kakao Login button pressed")
            kakaoAuthViewModel.handleKakaoLogin()
        }

        binding.signupButton.setOnClickListener{
            val intent = Intent(this@IntroActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.idLoginButton.setOnClickListener {
            // 테스트할 때 로그인 바로 할 수 있게 함
            val intent = Intent(this@IntroActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateIndicators(position: Int) {
        // 모든 인디케이터의 배경색을 회색으로 설정
        binding.indicator1.setBackgroundColor(getColor(R.color.gray))
        binding.indicator2.setBackgroundColor(getColor(R.color.gray))
        binding.indicator3.setBackgroundColor(getColor(R.color.gray))

        // 현재 페이지에 해당하는 인디케이터만 하얀색으로 변경
        when (position) {
            0 -> binding.indicator1.setBackgroundColor(getColor(R.color.white))
            1 -> binding.indicator2.setBackgroundColor(getColor(R.color.white))
            2 -> binding.indicator3.setBackgroundColor(getColor(R.color.white))
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
