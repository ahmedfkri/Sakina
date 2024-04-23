package com.example.sakina.feature_medicine.data.local

import android.util.Log
import androidx.room.TypeConverter

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {
    private val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())

    @TypeConverter
    fun fromDateList(dateList: List<Date>?): String? {
        val formattedDates = dateList?.joinToString(separator = ",") { sdf.format(it) }
        Log.d("DateListConverter", "Converted dates to string: $formattedDates")
        return formattedDates
    }

    @TypeConverter
    fun toDateList(dateString: String?): List<Date>? {
        val dates = dateString?.split(",")?.map { sdf.parse(it.trim()) ?: Date() }
        Log.d("DateListConverter", "Converted string to dates: $dates")
        return dates
    }
}