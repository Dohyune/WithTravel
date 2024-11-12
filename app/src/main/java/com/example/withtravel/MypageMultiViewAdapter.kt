package com.example.withtravel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withtravel.databinding.ItemMypageButtonGroupBinding
import com.example.withtravel.databinding.ItemMypageCalendarBinding
import com.example.withtravel.databinding.ItemMypageScheduleBinding

class MypageMultiViewAdapter(
    private val items: List<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_BUTTON_GROUP = 1
        const val VIEW_TYPE_CALENDAR = 2
        const val VIEW_TYPE_SCHEDULE = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is MyPageButtonGroup -> VIEW_TYPE_BUTTON_GROUP
            is MyPageCalendarData -> VIEW_TYPE_CALENDAR
            is MyPageSchedule -> VIEW_TYPE_SCHEDULE
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_BUTTON_GROUP -> {
                val binding = ItemMypageButtonGroupBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ButtonGroupViewHolder(binding)
            }
            VIEW_TYPE_CALENDAR -> {
                val binding = ItemMypageCalendarBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                CalendarViewHolder(binding)
            }
            VIEW_TYPE_SCHEDULE -> {
                val binding = ItemMypageScheduleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ScheduleViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ButtonGroupViewHolder -> holder.bind(items[position] as MyPageButtonGroup)
            is CalendarViewHolder -> holder.bind(items[position] as MyPageCalendarData)
            is ScheduleViewHolder -> holder.bind(items[position] as MyPageSchedule)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ButtonGroupViewHolder(private val binding: ItemMypageButtonGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyPageButtonGroup) {
            // Bind button group data
        }
    }

    inner class CalendarViewHolder(private val binding: ItemMypageCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyPageCalendarData) {
            // Bind calendar data
        }
    }

    inner class ScheduleViewHolder(private val binding: ItemMypageScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyPageSchedule) {
            binding.title.text = item.title
            binding.session.text = item.session
            binding.time.text = item.time
            binding.location.text = item.location
        }
    }

}
