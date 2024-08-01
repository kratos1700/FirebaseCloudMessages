package com.example.basicnotification.firestore.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


// firestore basico
@Composable
fun FirestoreScreen(navController: NavHostController) {

    var firestore: FirebaseFirestore = Firebase.firestore
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            "Firestore Screen  BASIC",
            modifier = Modifier.padding(32.dp),
            fontSize = 24.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )

        // basicInsert(firestore)
        //  multipleInsert(firestore)
        // basicReadData(firestore)
        // basicReadDocument(firestore, coroutineScope)
        // basicReadDocumentWithParse(firestore, coroutineScope)
        //  basicReadDocumentFromCache(firestore, coroutineScope)
        //  subCollections(firestore, coroutineScope)
        // basicRealTime(firestore)
       // basicRealTimeCollection(firestore)
       // basicQuery(firestore)
        basicMediumQuery(firestore)


    }


}

fun basicInsert(firestore: FirebaseFirestore) {
    val user = hashMapOf(
        "name" to "Kratos1700",
        "edad" to "44",
        "born" to 1980,
        "happy" to true,
        "extraInfo" to null
    )

    // anñade un nuevo documento con un ID autogenerado
    //  firestore.collection("users").add(user)
    //firestore.collection("users").add(user).await() // este es el mismo que el anterior pero con el await para que sea sincrono y reciba la respuesta

    firestore.collection("users").add(user).addOnSuccessListener {
        Log.i("Firestore", "DocumentSnapshot added with ID: ${it.id}")
    }.addOnFailureListener {
        Log.e("Firestore", "Error adding document, ${it.message}")
    }

}

fun multipleInsert(firestore: FirebaseFirestore) {
    for (i in 0..50) { // Inserta 50 documentos en la colección "users"
        val user = hashMapOf(
            "name" to "Kratos $i",
            "edad" to 44 + i,
            "born" to 1980,
            "happy" to (i % 2 == 0), // true si i es par, false si i es impar
            "extraInfo" to null
        )

        firestore.collection("users").add(user).addOnSuccessListener {
            Log.i("Firestore", "DocumentSnapshot added with ID: ${it.id}")
        }.addOnFailureListener {
            Log.e("Firestore", "Error adding document, ${it.message}")
        }
    }
}

fun basicReadData(firestore: FirebaseFirestore) {
    firestore.collection("users").get().addOnSuccessListener { result ->
        for (document in result) {
            Log.d("Firestore", "${document.id} => ${document.data}")
        }
    }.addOnFailureListener { exception ->
        Log.e("Firestore", "Error getting documents.", exception)
    }
}

fun basicReadDocument(firestore: FirebaseFirestore, coroutineScope: CoroutineScope) {

    /*val result =  firestore.collection("users").document("lFHTwHExspPfAViekvVj").get()
        .addOnSuccessListener { document ->
          if (document != null) {
              Log.d("Firestore", "${document.id} => ${document.data}")
          } else {
              Log.d("Firestore", "No such document")
          }
      }.addOnFailureListener { exception ->
          Log.e("Firestore", "Error getting documents.", exception)
      }*/

    // amb la courutina es pot fer de forma asincrona i esperar el resultat amb el await que es bloquejant fins que es rep la resposta del servidor
    coroutineScope.launch {
        val result = firestore.collection("users").document("lFHTwHExspPfAViekvVj").get().await()


        Log.i("Firestore", "El resultado es: $result")
        Log.i("Firestore", "El resultado lectura : ${result.id}: , ${result.data} ")
    }


}

fun basicReadDocumentWithParse(firestore: FirebaseFirestore, coroutineScope: CoroutineScope) {


    // amb la courutina es pot fer de forma asincrona i esperar el resultat amb el await que es bloquejant fins que es rep la resposta del servidor
    coroutineScope.launch {
        val result = firestore.collection("users").document("lFHTwHExspPfAViekvVj").get().await()



        Log.i("Firestore", "El resultado es: $result")
        Log.i("Firestore", "El resultado lectura : ${result.id}: , ${result.data} ")


        // Parseamos el resultado a un objeto de tipo UserData
        result.toObject(UserData::class.java)?.let {
            Log.i("Firestore", "El resultado parseado es: $it")
        }

        val mapResult = result.toObject<UserData>()

        Log.i("Firestore", "El resultado lectura mapResult: ${result.id}: , $mapResult ")


    }


}


// lectura de datos en cache (offline)
fun basicReadDocumentFromCache(firestore: FirebaseFirestore, coroutineScope: CoroutineScope) {

    // amb la courutina es pot fer de forma asincrona i esperar el resultat amb el await que es bloquejant fins que es rep la resposta del servidor
    coroutineScope.launch {
        val ref = firestore.collection("users").document("lFHTwHExspPfAViekvVj")

        val source =
            Source.CACHE   // Source.CACHE, Source.SERVER, Source.DEFAULT (primero intenta con cache y si no lo encuentra va a servidor)

        val result = ref.get(source).await()

        Log.i("Firestore", "El resultado es: $result")
        Log.i("Firestore", "El resultado lectura : ${result.id}: , ${result.data} ")
    }


}

private fun subCollections(firestore: FirebaseFirestore, coroutineScope: CoroutineScope) {

    firestore.collection("users").document("dev").collection("fav")
        .document("ZQGhASA4KlGYHGyRHPO3").get().addOnSuccessListener { document ->
            Log.d("Firestore", "${document.id} => ${document.data}")
        }.addOnFailureListener { exception ->
            Log.e("Firestore", "Error getting documents.", exception)
        }


}

// Leyendo datos en tiempo real
private fun basicRealTime(firestore: FirebaseFirestore) {

    firestore.collection("users").document("012mpP4QSPAtMv6MKhRA")
        .addSnapshotListener { value, error ->
            Log.i("Firestore", "El valor es: ${value?.data}")

        }
}


// Leyendo datos en tiempo real to do los datos
private fun basicRealTimeCollection(firestore: FirebaseFirestore) {

    firestore.collection("users")
        .addSnapshotListener { value, error ->
            Log.i("Firestore", "El valor es: ${value?.size()}")

        }
}


// consulta de datos
private fun basicQuery(firestore: FirebaseFirestore) {

    val query = firestore.collection("users").orderBy("edad", Query.Direction.DESCENDING).limit(10)
    query.get().addOnSuccessListener { result ->
        for (document in result) {
            Log.d("Firestore", "${document.id} => ${document.data}")
        }
    }.addOnFailureListener { exception ->
        Log.e("Firestore", "Error getting documents.", exception)
    }


}

// consulta de datos mas compleja
private fun basicMediumQuery(firestore: FirebaseFirestore) {

    val query = firestore.collection("users").orderBy("edad", Query.Direction.DESCENDING)
        .whereEqualTo("happy", true)   // sirve para filtrar los datos por un campo en concreto
        .whereGreaterThan("edad", 50) // sirve para filtrar los datos por un campo en concreto y que sea mayor que un valor

    query.get().addOnSuccessListener { result ->
        for (document in result) {
            Log.d("Firestore", "${document.id} => ${document.data}")
        }
    }.addOnFailureListener { exception ->
        Log.e("Firestore", "Error getting documents.", exception)
    }


}



data class UserData(
    val name: String = "",
    val edad: Int = 0,
    val born: Int = 0,
    val happy: Boolean = false,

    )