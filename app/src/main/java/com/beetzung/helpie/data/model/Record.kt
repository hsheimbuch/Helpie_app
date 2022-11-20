package com.beetzung.helpie.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

typealias DaysData = Map<Long, Record>

@Keep
@Parcelize
data class Record(
    val timestamp: Long,
    val emotion: Emotion,
    val cardNumber: Int,
    val text: String?
): Parcelable
