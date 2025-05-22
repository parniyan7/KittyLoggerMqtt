package com.parniyan.kittylogger.presentation

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.parniyan.kittylogger.R
import com.parniyan.kittylogger.data.KittyContextProvider


/**
 ** Created by Parniyan on 5/20/2025.
 **
 */



object KittyLoggerUI {
    fun showNotification() {
        val context = KittyContextProvider.get() ?: return
        val channelId = "kitty_logger_channel"
        val notificationId = 1001

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "KittyLogger", NotificationManager.IMPORTANCE_LOW)
            manager.createNotificationChannel(channel)
        }

        val intent = Intent(context, KittyMqttLogActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.kitty_logo)
            .setContentTitle("KittyLogger")
            .setContentText("Tap to view MQTT logs")
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setOngoing(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }
}
