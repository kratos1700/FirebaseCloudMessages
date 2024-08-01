package com.example.basicnotification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.basicnotification.data.TopicsService


@Composable
fun PrincipalScreen(navController: NavHostController, viewModel: MainViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = {
            viewModel.subscribeToTopic(TopicsService.FOOTBALL_TOPIC)

        }) {
            Text(text = "Subscribe to football")

        }
        Spacer(modifier = Modifier.padding(8.dp))

        Button(onClick = {
            viewModel.subscribeToTopic(TopicsService.BASKETBALL_TOPIC)

        }) {
            Text(text = "Subscribe to basketball")

        }
        Spacer(modifier = Modifier.padding(8.dp))

        Button(onClick = {
            viewModel.subscribeToTopic(TopicsService.BASEBALL_TOPIC)

        }) {
            Text(text = "Subscribe to baseball")

        }



        Spacer(modifier = Modifier.padding(28.dp))

        OutlinedButton(onClick = {
            navController.navigate("firestore_screen")

        }) {
            Text(text = "Navegate to Firebase Firestore")

        }


    }

}