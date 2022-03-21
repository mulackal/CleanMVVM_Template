package com.vip.cleantemplate.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle

import android.util.Log
import androidx.core.app.NotificationCompat
import com.vip.cleantemplate.R


/**
 * Created by vipin on 2/3/17.
 */
class ShowNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        Log.e("Reciever", "onCreate-notification")

        showNotification(intent.extras, context)
    }

    private fun showNotification(data: Bundle?, context: Context) {

        /**
         * here displaying that notification
         */
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val largIcon = BitmapFactory.decodeResource(context.resources,
                R.mipmap.ic_launcher)


        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel("notification",
                    "CustomNotification", NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.setShowBadge(true)
            notificationChannel.canShowBadge()
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

            mNotificationManager.createNotificationChannel(notificationChannel)


            val builder = NotificationCompat.Builder(context, "notification")

            builder.setLargeIcon(largIcon)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentTitle(data!!.getCharSequence("title"))
                    .setColor(context.resources.getColor(R.color.colorPrimary))
                    .setAutoCancel(true)
                    .setSound(soundUri)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(data.getCharSequence("message")))
                    .setContentText(data.getCharSequence("message"))

            val vibrate = longArrayOf(0, 100, 200, 300)
            builder.setVibrate(vibrate)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setSmallIcon(R.drawable.ic_launcher_background)
            } else {
                builder.setSmallIcon(R.drawable.ic_launcher_background)
            }

            val notification = builder.build()

            mNotificationManager.notify(1, notification)

            //  DeleteChannel(context); // delete channel after use

        } else {

            val mBuilder = NotificationCompat.Builder(context)
                    .setLargeIcon(largIcon)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentTitle(data!!.getCharSequence("title"))
                    .setAutoCancel(true)
                    .setSound(soundUri)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(data.getCharSequence("message")))
                    .setContentText(data.getCharSequence("message"))


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBuilder.setSmallIcon(R.drawable.ic_launcher_background)
            } else {
                mBuilder.setSmallIcon(R.drawable.ic_launcher_background)
            }


            val vibrate = longArrayOf(0, 100, 200, 300)
            mBuilder.setVibrate(vibrate)

            mNotificationManager.notify(1, mBuilder.build())

        }
    }

    internal fun DeleteChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            /*   NotificationChannel notificationChannel =
                    notificationManager.getNotificationChannel("notification");*/

            notificationManager.deleteNotificationChannel("notification")
        }
    }
}
