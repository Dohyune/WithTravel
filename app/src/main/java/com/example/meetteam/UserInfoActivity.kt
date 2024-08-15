package com.example.meetteam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.meetteam.databinding.ActivityUserInfoBinding

class UserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기 버튼 클릭 이벤트
        binding.backButton.setOnClickListener {
            finish()
        }

        // 위치 설정 버튼 클릭 이벤트
        binding.btnLocationSettings.setOnClickListener {
            replaceFragment(LocationSettingsFragment())
        }

        // 개인 시간 설정 버튼 클릭 이벤트
        binding.btnTimeSettings.setOnClickListener {
            replaceFragment(TimeSettingsFragment())
        }

        // 저장하기 버튼 클릭 이벤트
        binding.btnSave.setOnClickListener {
            // 저장 로직
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
