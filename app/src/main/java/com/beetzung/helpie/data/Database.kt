package com.beetzung.helpie.data

import com.beetzung.helpie.data.model.DaysData
import com.beetzung.helpie.data.model.Record

interface Database {
    suspend fun getDaysData(): DaysData

    suspend fun updateRecord(timestamp: Long, record: Record): DaysData
}