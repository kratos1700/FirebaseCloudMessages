package com.example.basicnotification.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.basicnotification.MainActivity
import com.example.basicnotification.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject

class MessagingService @Inject constructor() : FirebaseMessagingService() {


    // esta funcio es per si l'aplicació està en primer pla
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (message.data.isNotEmpty()) {
            val exemple1 = message.data
            message.data.forEach {
                Log.d("MessagingService", "Key: ${it.key} Value: ${it.value}")
            }
        }


        if (message.notification != null) {
            Log.d("MessagingService", "Title: ${message.notification?.title}")
            Log.d("MessagingService", "Body: ${message.notification?.body}")

        }

        message.notification?.let {
            val body = it.body.orEmpty()
            val title = it.title.orEmpty()
            createNotification(title, body)
        }


    }


    // Crear notificación  personalizada con el título y el cuerpo que se le pasa por parámetro a la función createNotification
    private fun createNotification(title: String, body: String) {
        // Crear notificación
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        }
        val pendingIntent = PendingIntent.getActivity(this, 999, intent, PendingIntent.FLAG_MUTABLE)

        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)  // sonido por defecto de la notificación
        val channelId = getString(R.string.default_channel)

        val notificationBuilder = NotificationCompat.Builder(this, "")
            .setSmallIcon(R.drawable.ic_android)
            .setContentTitle(title)
            .setContentText(body)
            .setChannelId(channelId)
            .setAutoCancel(true)  // para que se cierre la notificación al hacer click
            .setSound(sound)  // sonido de la notificación
            .setContentIntent(pendingIntent)


        val notificationManeger = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // cosas de chanel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                "Promociones",
                NotificationManager.IMPORTANCE_DEFAULT)

            notificationManeger.createNotificationChannel(channel)
        }



        notificationManeger.notify(0, notificationBuilder.build())
    }


    // esta funcio es per si l'aplicació està apagada
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        sendRegistrationToken(token)
    }

    private fun sendRegistrationToken(token: String) {
        Log.i(
            "MessagingService",
            "Token: $token"
        )  // Log para verificar que se obtiene el token y poder hacer pruebas
        //llamada al backend
    }

}