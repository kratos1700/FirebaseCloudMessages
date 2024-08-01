package com.example.basicnotification.core

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.basicnotification.Greeting
import com.example.basicnotification.MainViewModel
import com.example.basicnotification.firestore.ui.FirestoreScreen

@Composable
fun AppNavigation(

) {
    val navController = rememberNavController()
    val  mainViewModel: MainViewModel = hiltViewModel()

    NavHost(navController = navController,
        startDestination = AppScreens.Greeting.ruta){
        composable(AppScreens.Greeting.ruta){
            Greeting(navController,mainViewModel)
        }
        composable(AppScreens.FirestoreScreen.ruta){
         FirestoreScreen(navController =navController)
        }

        //añadimos parametros para pasar info en la navegacion

    }
}




sealed class AppScreens(val ruta:String){
    object Greeting: AppScreens("greeting_screen")
    object FirestoreScreen: AppScreens("firestore_screen")

}