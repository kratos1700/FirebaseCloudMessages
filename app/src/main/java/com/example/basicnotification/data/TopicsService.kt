package com.example.basicnotification.data

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class TopicsService @Inject constructor(val messaging :FirebaseMessaging) {

    companion object{
        const val FOOTBALL_TOPIC = "football"
        const val BASKETBALL_TOPIC = "basketball"
        const val BASEBALL_TOPIC = "baseball"
    }

    // esta funcion sirve para suscribirse a un tema en concreto
    fun subscribeToTopic(topic:String){

        messaging.subscribeToTopic(topic)  // suscribirse a un tema en concreto y se le pasa por parámetro el nombre del tema
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Subscription was successful
                    Log.i("TopicsService", "Subscribed to $topic")
                } else {
                    // Subscription failed
                    Log.e("TopicsService", "Failed to subscribe to $topic")
                }
            }
    }

    // esta funcion sirve para desuscribirse de un tema en concreto
    fun unsubscribeFromTopic(topic:String){

        messaging.unsubscribeFromTopic(topic)  // desuscribirse de un tema en concreto y se le pasa por parámetro el nombre del tema

    }

}