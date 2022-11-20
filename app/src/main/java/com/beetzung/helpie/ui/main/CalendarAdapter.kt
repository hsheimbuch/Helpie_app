package com.beetzung.helpie.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beetzung.helpie.R
import com.beetzung.helpie.data.model.Record
import com.beetzung.helpie.databinding.RecyclerItemCalendarBinding

typealias DayInfo = Pair<Boolean, String>
typealias CalendarData = Map<DayInfo, Record?>


class CalendarAdapter(private val onItemListener: (Record) -> Unit) : RecyclerView.Adapter<CalendarViewHolder>() {
    companion object {
        val EmptyDay = DayInfo(false, "")
    }
    private val daysOfMonth = mutableMapOf<DayInfo, Record?>()

    fun setDaysOfMonth(daysOfMonth: CalendarData) {
        this.daysOfMonth.clear()
        this.daysOfMonth.putAll(daysOfMonth)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_item_calendar, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.166666666).toInt()
        val binding = RecyclerItemCalendarBinding.bind(view)
        return CalendarViewHolder(binding, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val dayInfo = daysOfMonth.keys.elementAt(position)
        val record = if (daysOfMonth.contains(dayInfo)) daysOfMonth[dayInfo] else null
        holder.bind(dayInfo, record)
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }
}