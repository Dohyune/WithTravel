package com.example.meetteam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.meetteam.databinding.ActivityUserInfoBinding

class UserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 위치 설정 버튼 클릭 시 Fragment로 이동
        binding.btnLocationSettings.setOnClickListener {
            openFragment(LocationSettingsFragment())
        }

        // 개인 시간 설정 버튼 클릭 시 Fragment로 이동
        binding.btnTimeSettings.setOnClickListener {
            openFragment(TimeSettingsFragment())
        }
    }

    private fun openFragment(fragment: androidx.fragment.app.Fragment) {
        // Fragment를 전체 화면으로 열고 Activity의 UI를 가립니다.
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(android.R.id.content, fragment) // fragment_container 대신 android.R.id.content 사용
        transaction.addToBackStack(null) // 뒤로 가기 버튼을 위한 백스택 추가
        transaction.commit()
    }
}
