package com.example.sakina.core.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.util.Constant.TAG

class ReminderBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            // Extract medicine details from intent extras
            val medicineName = intent?.getStringExtra("medicine_name") ?: "Unknown Medicine"
            val medicineId = intent?.getLongExtra("medicineId", 0)
            val notificationId = intent?.getIntExtra("notification_id", 0)

            Log.d(TAG, "Received reminder for medicineId: $medicineId")

            // Prepare intent to open fragment
            val fragmentArgs = Bundle().apply {
                putLong("medicineId", medicineId!!)
            }
            val fragmentIntent = Intent(context, MainActivity::class.java).apply {
                putExtra("medicineId", fragmentArgs)
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                notificationId ?: 0,
                fragmentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            // Prepare notification
            val notificationManager =
                it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "reminder_channel"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "Reminder Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }

            val notification = NotificationCompat.Builder(it, channelId)
                .setContentTitle("Medicine Reminder")
                .setContentText("Don't forget to take $medicineName")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        it.resources,
                        R.drawable.sakina_med
                    )
                )
                .setAutoCancel(true)
                .build()

            // Show notification
            notificationManager.notify(notificationId ?: 0, notification)
        }
    }
}
