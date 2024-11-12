package com.example.withtravel

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.withtravel.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기 버튼 클릭 이벤트
        binding.backButton.setOnClickListener {
            finish()  // 현재 액티비티를 종료하고 이전 액티비티로 돌아감
        }

        // 중복확인 버튼 클릭 이벤트
        binding.duplicateCheckButton.setOnClickListener {
            // 아이디 중복 확인 로직 구현.
            Toast.makeText(this, "아이디 중복 확인", Toast.LENGTH_SHORT).show()
        }

        // 인증번호 받기 버튼 클릭 이벤트
        binding.verifyButton.setOnClickListener {
            // 휴대폰 번호 인증 로직 구현.
            Toast.makeText(this, "인증번호 발송", Toast.LENGTH_SHORT).show()
        }

        // 가입하기 버튼 클릭 이벤트
        binding.signupButton.setOnClickListener {
            // UserInfoActivity로 이동
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }

        // 이메일 도메인 스피너 설정
        val domains = arrayOf("선택","gmail.com", "naver.com")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, domains)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.domainSelect.adapter = adapter
    }
}
