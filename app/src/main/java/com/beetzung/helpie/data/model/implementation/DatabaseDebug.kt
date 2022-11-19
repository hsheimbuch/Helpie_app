package com.beetzung.helpie.data.model.implementation

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.beetzung.helpie.data.Database
import com.beetzung.helpie.data.model.DaysData
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.data.model.Record

class DatabaseDebug(context: Context) : Database {
    companion object {
        private const val PREFS_KEY_DAYS_DATA = "days_data"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("database_debug", Context.MODE_PRIVATE)

    override suspend fun getDaysData(): DaysData {
        val daysData = mutableMapOf<Long, Record>()
        sharedPreferences.getStringSet(PREFS_KEY_DAYS_DATA, null)
            .orEmpty()
            .toList()
            .forEach { key ->
                sharedPreferences.getString(key, null)?.let {
                    daysData[key.toLong()] = toRecord(it)
                }
            }
        return daysData
    }

    override suspend fun updateRecord(timestamp: Long, record: Record): DaysData {
        val key = timestamp.toString()
        val keys = sharedPreferences.getStringSet(PREFS_KEY_DAYS_DATA, null)
            .orEmpty()
            .toMutableList()
            .apply {
                if (!contains(key)) {
                    add(key)
                }
            }
        assert(keys.contains(key))
        sharedPreferences.edit {
            putStringSet(PREFS_KEY_DAYS_DATA, keys.toSet())
            putString(key, record.jsonify())
        }
        return getDaysData()
    }


    private fun Record.jsonify(): String {
        return """
            {
                "timestamp": $timestamp,
                "emotion": $emotion,
                "cardNumber": $cardNumber,
                "text": $text
            }
        """.trimIndent()
    }

    private fun toRecord(json: String): Record {
        val timestamp = json.substringAfter("timestamp: ").substringBefore(",").toLong()
        val emotion =
            json.substringAfter("emotion: ").substringBefore(",").let { Emotion.valueOf(it) }
        val cardNumber = json.substringAfter("cardNumber: ").substringBefore(",").toInt()
        val text = json.substringAfter("text: ").substringBefore("}")
            .let { if (it == "null") null else it }
        return Record(timestamp, emotion, cardNumber, text)
    }
}