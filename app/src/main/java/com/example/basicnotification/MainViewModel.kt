package com.example.basicnotification

import androidx.lifecycle.ViewModel
import com.example.basicnotification.data.TopicsService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val topicsService: TopicsService) : ViewModel() {

    fun subscribeToTopic(topic: String) {
        topicsService.subscribeToTopic(topic)
    }

}