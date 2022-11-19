package com.beetzung.helpie.data.model

typealias DaysData = Map<Long, Record>

data class Record(
    val timestamp: Long,
    val emotion: Emotion,
    val cardNumber: Int,
    val text: String?
)
