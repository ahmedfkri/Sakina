package com.example.sakina.feature_medicine.data.local

import androidx.room.TypeConverter


class Converters {

    @TypeConverter
    fun fromLongList(reminderTimes: List<Long>?): String? {
        return reminderTimes?.joinToString(",")
    }

    @TypeConverter
    fun toLongList(reminderTimesString: String?): List<Long>? {
        if (reminderTimesString.isNullOrEmpty()) {
            return null
        }
        return reminderTimesString.split(",").mapNotNull { it.toLongOrNull() }
    }
}