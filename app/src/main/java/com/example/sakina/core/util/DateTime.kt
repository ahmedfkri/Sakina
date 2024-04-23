package com.example.sakina.core.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateTime {
    fun getCurrentDateTime(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }
}