package com.example.basicnotification

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.basicnotification.ui.theme.BasicNotificationTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

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

        val extras = intent.extras // extras es un Bundle que contiene los datos adicionales del intent que se ha lanzado para iniciar la actividad actual

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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
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
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicNotificationTheme {
        Greeting("Android")
    }
}