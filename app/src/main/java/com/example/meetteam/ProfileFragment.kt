package com.example.meetteam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.meetteam.databinding.Fragment4MyPageBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: Fragment4MyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment4MyPageBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingbutton.setOnClickListener {

            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)

        }
    }
}
