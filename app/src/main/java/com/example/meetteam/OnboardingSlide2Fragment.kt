package com.example.meetteam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class OnboardingSlide2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 파일을 inflate하여 View로 반환합니다.
        return inflater.inflate(R.layout.fragment_onboarding_slide2, container, false)
    }
}
