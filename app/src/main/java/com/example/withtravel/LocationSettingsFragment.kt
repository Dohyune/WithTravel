package com.example.withtravel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.withtravel.databinding.FragmentLocationSettingsBinding

class LocationSettingsFragment : Fragment() {

    private var _binding: FragmentLocationSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기 버튼 클릭 시
        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack() // 현재 Fragment를 백스택에서 제거
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
