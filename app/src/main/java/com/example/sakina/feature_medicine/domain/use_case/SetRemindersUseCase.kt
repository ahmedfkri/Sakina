package com.example.sakina.feature_medicine.domain.use_case

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.core.util.ReminderBroadcastReceiver
import com.example.sakina.feature_medicine.domain.model.Medicine
import java.util.Calendar

class SetRemindersUseCase(val context: Context) {

    operator fun invoke(medicine: Medicine) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val notificationId = System.currentTimeMillis().toInt()

        for ((index, reminderTime) in medicine.reminderTimes!!.withIndex()) {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = reminderTime
                set(Calendar.SECOND, 0)
            }

            val alarmIntent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
                putExtra("medicine_name", medicine.name)
                putExtra("medicineId", medicine.id)
                Log.d(TAG, "alarm intent: "+ medicine.id)
                putExtra("notification_id", notificationId + index)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                (reminderTime + index).toInt(), // Use a unique request code for each alarm (convert to int)
                alarmIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            // Schedule the alarm to repeat daily at the specified time
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }


        Log.d(TAG, "invoke: Reminder set")

    }

}