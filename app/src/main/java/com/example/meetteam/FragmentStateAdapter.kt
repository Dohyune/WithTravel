package com.example.meetteam

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingSlide1Fragment()
            1 -> OnboardingSlide2Fragment()
            2 -> OnboardingSlide3Fragment()
            else -> OnboardingSlide1Fragment()
        }
    }

    override fun getItemCount(): Int {
        return 3  // 슬라이드 개수
    }
}
