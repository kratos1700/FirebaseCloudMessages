package com.example.basicnotification.data.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesFirebaseMessegin() = Firebase.messaging  // proporciona la instancia de Firebase Messaging


}