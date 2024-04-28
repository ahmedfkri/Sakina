/*
package com.example.sakina.feature_medicine.domain.use_case

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.sakina.feature_medicine.domain.model.Medicine
import java.util.Calendar

class SetMedicineRemindersUseCase(val context: Context, val medicine: Medicine) {

    operator fun invoke() {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderBroadcastReceiver::class.java)
        intent.putExtra("medicine_name", medicine.name)

        for (reminderTime in medicine.reminderTimes!!) {
            val timeParts = reminderTime.split(":")
            val hourOfDay = timeParts[0].toInt()
            val minute = timeParts[1].substring(0, 2).toInt()

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            // Create a PendingIntent for the alarm
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                medicine.id.toInt(), // Use medicine ID as request code to identify pending intents
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            // Schedule the alarm
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }
}*/
