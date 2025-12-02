package uk.ac.tees.mad.travelr.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission


// to receive the update by alarm manager when reminder time is reached
class TripReminderReceiver : BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Travel Reminder"
        val message = intent.getStringExtra("message") ?: "Don't forget your trip!"
        val notificationId = intent.getIntExtra("notification_id", 0)

        NotificationHelper.showNotification(
            context = context,
            title = title,
            message = message,
            notificationId = notificationId
        )
    }


}