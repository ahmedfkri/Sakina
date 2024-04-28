package com.example.sakina.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateTime {
    fun getCurrentDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }

    fun getCurrentDateTime(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("EEEE, hh:mm a", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }
}