package com.example.meetteam

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
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
            kakaoAuthViewModel.handleKakaoLogin(this@IntroActivity)
        }

        binding.idLoginButton.setOnClickListener {
            val intent = Intent(this@IntroActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signupButton.setOnClickListener{
            val intent = Intent(this@IntroActivity, SignUpActivity::class.java)
            startActivity(intent)
        }


        // 테스트용
        binding.forgotId.setOnClickListener {
            val intent = Intent(this@IntroActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateIndicators(position: Int) {
        // 모든 인디케이터의 배경색을 기본 색상으로 설정
        setIndicatorColor(binding.indicator1, R.color.introblue)
        setIndicatorColor(binding.indicator2, R.color.introblue)
        setIndicatorColor(binding.indicator3, R.color.introblue)

        // 현재 페이지에 해당하는 인디케이터만 하얀색으로 변경
        when (position) {
            0 -> setIndicatorColor(binding.indicator1, R.color.white)
            1 -> setIndicatorColor(binding.indicator2, R.color.white)
            2 -> setIndicatorColor(binding.indicator3, R.color.white)
        }
    }

    private fun setIndicatorColor(view: View, colorResId: Int) {
        val drawable = view.background as GradientDrawable
        drawable.setColor(getColor(colorResId))
    }

    private fun navigateToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
