package com.beetzung.helpie.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

typealias DaysData = Map<Long, Record>

@Parcelize
data class Record(
    val timestamp: Long,
    val emotion: Emotion,
    val cardNumber: Int,
    val text: String?
): Parcelable
