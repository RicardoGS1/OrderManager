package com.virtualworld.mipymeanabelmaster

import android.app.Application
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.firebase.FirebasePlatform
import com.virtualworld.mipymeanabelmaster.id.initKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize

fun main() = application {


    initKoin()

    FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {

        val storage = mutableMapOf<String, String>()

        override fun clear(key: String) {
            storage.remove(key)
        }

        override fun log(msg: String) = println("gms ggggggg")

        override fun retrieve(key: String): String? = storage[key]

        override fun store(key: String, value: String) = storage.set(key, value)

    })

    val options = FirebaseOptions(
        projectId = "mi-pyme-anabel",
        applicationId = "1:266211870190:web:8a80eb6f53a24596d07aff",
        apiKey = "AIzaSyCl8dFoSNcit6hNPp34NLElAFh4rlO_S7g",


        )

    Firebase.initialize(Application(), options)


    Window(
        onCloseRequest = ::exitApplication,
        title = "MiPymeAnabelMaster",
    ) {

        App()
    }
}