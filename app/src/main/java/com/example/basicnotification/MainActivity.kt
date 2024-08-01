package com.example.basicnotification

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.basicnotification.core.AppNavigation
import com.example.basicnotification.data.TopicsService.Companion.BASEBALL_TOPIC
import com.example.basicnotification.data.TopicsService.Companion.BASKETBALL_TOPIC
import com.example.basicnotification.data.TopicsService.Companion.FOOTBALL_TOPIC
import com.example.basicnotification.ui.theme.BasicNotificationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Inicialitza el launcher per sol·licitar permís
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // El permís s'ha concedit
                Log.d("MainActivity", "Permís de notificació concedit")
            } else {
                // El permís s'ha denegat
                Log.d("MainActivity", "Permís de notificació denegat")
            }
        }

        // Sol·licita el permís de notificació
        requestNotificationPermission()

        val extras =
            intent.extras // extras es un Bundle que contiene los datos adicionales del intent que se ha lanzado para iniciar la actividad actual

        if (extras != null) {
            val message = extras.getString("example1")
            val message2 = extras.getString("example2")
            val message3 = extras.getString("example3")

            Log.d("MessagingService", "El valor de example1 es: $message")
            Log.d("MessagingService", "El valor de example2 es: $message2")
            Log.d("MessagingService", "El valor de example3 es: $message3")

        }


        enableEdgeToEdge()
        setContent {
            BasicNotificationTheme {
                val mainViewModel: MainViewModel = hiltViewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { //innerPadding ->
                  //  Greeting(modifier = Modifier.padding(innerPadding), mainViewModel)
                    AppNavigation()
                }
            }
        }

    }


    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // El permís ja està concedit
                }

                shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Explica a l'usuari per què es necessita el permís i torna a demanar-ho
                    Log.d("MainActivity", "S'ha de mostrar una explicació per al permís")
                }

                else -> {
                    // Directament demana el permís
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }


}


@Composable
fun Greeting(navController: NavHostController, viewModel: MainViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = {
            viewModel.subscribeToTopic(FOOTBALL_TOPIC)

        }) {
            Text(text = "Subscribe to football")

        }
        Spacer(modifier = Modifier.padding(8.dp))

        Button(onClick = {
            viewModel.subscribeToTopic(BASKETBALL_TOPIC)

        }) {
            Text(text = "Subscribe to basketball")

        }
        Spacer(modifier = Modifier.padding(8.dp))

        Button(onClick = {
            viewModel.subscribeToTopic(BASEBALL_TOPIC)

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

