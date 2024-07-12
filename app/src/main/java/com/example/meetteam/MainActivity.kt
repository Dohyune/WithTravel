package com.example.meetteam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.meetteam.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    true
                }
                R.id.nav_chat -> {
                    true
                }
                R.id.nav_shop -> {
                    true
                }
                R.id.nav_profile -> {
                    true
                }
                else -> false
            }
        }

        // 로그아웃 버튼 클릭 처리
        binding.logoutButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this@MainActivity, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
