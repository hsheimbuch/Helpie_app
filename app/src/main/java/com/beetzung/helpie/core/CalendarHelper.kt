package com.beetzung.helpie.core

import java.time.LocalDate
import java.time.Year
import java.time.YearMonth

object CalendarHelper {
    fun getDaysInMonthArray(year: Int, month: Int): ArrayList<String?> {
        val date = LocalDate.of(year, month, 1)
        val daysInMonthArray: ArrayList<String?> = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(date)
        val daysInMonth: Int = yearMonth.lengthOfMonth()
        val firstOfMonth: LocalDate = date.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(null)
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    fun getDayFromTimeStamp(timestamp: Long): Int {
        val date = LocalDate.ofEpochDay(timestamp / 86400000)
        return date.dayOfMonth
    }

    fun checkIfCurrentMonth(timestamp: Long, year: Int, month: Int): Boolean {
        val date = LocalDate.ofEpochDay(timestamp / 86400000)
        return date.year == year && date.monthValue == month
    }

    fun getCurrentDay(): Int {
        val date = LocalDate.now()
        return date.dayOfMonth
    }

    fun isDayInFuture(day: Int, month: Int, year: Int): Boolean {
        val date = LocalDate.of(year, month, day)
        return date.isAfter(LocalDate.now())
    }

    fun getCurrentYear(): Int {
        return Year.now().value
    }

    fun getCurrentMonth(): Int {
        val date = LocalDate.now()
        return date.monthValue
    }
}