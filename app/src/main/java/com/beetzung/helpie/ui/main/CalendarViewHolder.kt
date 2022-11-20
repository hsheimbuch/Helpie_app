package com.beetzung.helpie.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.beetzung.helpie.R
import com.beetzung.helpie.core.getEmotionColor
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.data.model.Record
import com.beetzung.helpie.databinding.RecyclerItemCalendarBinding
import com.beetzung.helpie.ui.main.CalendarAdapter.Companion.EmptyDay

class CalendarViewHolder(
    private val binding: RecyclerItemCalendarBinding,
    val onItemListener: (Record) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val clickListener = View.OnClickListener {
        onItemListener(record!!)
    }

    private var record: Record? = null

    fun bind(dayInfo: DayInfo, record: Record?) {
        this.record = record
        if (record != null) {
            binding.root.setOnClickListener(clickListener)
        } else {
            binding.root.setOnClickListener(null)
        }
        binding.updateView(dayInfo, record?.emotion)
    }

    private fun RecyclerItemCalendarBinding.updateView(dayInfo: DayInfo, emotion: Emotion?) {
        if (dayInfo != EmptyDay) {
            root.visibility = View.VISIBLE
            calendarText.text = dayInfo.second
            if (dayInfo.first) {
                calendarCircle.visibility = View.VISIBLE
                calendarText.setTextColor(itemView.context.getColor(R.color.black))
            } else {
                calendarCircle.visibility = View.INVISIBLE
                calendarText.setTextColor(itemView.context.getColor(R.color.white))
            }
            if (emotion != null) {
                calendarIndicator.background.setTint(getEmotionColor(emotion))
                calendarIndicator.visibility = View.VISIBLE
            } else {
                calendarIndicator.visibility = View.INVISIBLE
            }
        } else {
            root.visibility = View.INVISIBLE
        }

    }
}