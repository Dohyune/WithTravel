package com.example.withtravel

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.withtravel.databinding.Fragment4MyPageBinding

class MyPageFragment : Fragment() {

    private lateinit var binding: Fragment4MyPageBinding
    private lateinit var adapter: MypageMultiViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = Fragment4MyPageBinding.inflate(inflater, container, false)

        val items = listOf(
            MyPageButtonGroup(listOf("예정된 일정", "지난 일정", "일정 수정")),
            MyPageCalendarData("2024년 8월"),
            MyPageSchedule("신화와 예술 팀플", "3차 토의", "PM 04:00", "서울 광진구 어린이대공원역")
        )

        adapter = MypageMultiViewAdapter(items)

        val verticalSpaceHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 12f, resources.displayMetrics).toInt()

        val horizontalSpaceWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics).toInt()

        binding.recyclerViewSchedules.addItemDecoration(VerticalSpaceItemDecoration(verticalSpaceHeight, horizontalSpaceWidth))

        // RecyclerView 설정
        binding.recyclerViewSchedules.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MyPageFragment.adapter

            // 간격을 위한 패딩 설정
            setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.recycler_view_item_spacing))

            // 패딩도 포함하여 스크롤 가능하게 설정
            clipToPadding = false
        }

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
