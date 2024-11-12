package com.example.withtravel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.withtravel.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // location_setting 버튼 클릭 시 페이지 이동
        binding.locationSetting.setOnClickListener {
            val intent = Intent(this, LocationSettingActivity::class.java)
            startActivity(intent)
        }
    }


}