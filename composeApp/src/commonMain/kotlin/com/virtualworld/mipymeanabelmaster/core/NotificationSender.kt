package com.virtualworld.mipymeanabelmaster.core


import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.auth.oauth2.GoogleCredentials
import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.FileInputStream
import java.io.IOException
class NotificationSender {


    private val client =
        HttpClient(Java) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

    private val projectId = "mi-pyme-anabel" // Reemplaza con tu ID de proyecto
    private val credentialsFilePath =
        "mi-pyme-anabel-firebase-adminsdk-dsgae-88dca7cc89.json" // Reemplaza con la ruta a tu archivo JSON


    private fun getAccessToken(): String {
        try {
            val transport: HttpTransport = NetHttpTransport()
            val jsonFactory: JacksonFactory = JacksonFactory()
            val credentials = GoogleCredentials.fromStream(FileInputStream(credentialsFilePath))
                .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging"))

            credentials.refreshIfExpired()
            return credentials.accessToken.tokenValue
        } catch (e: IOException) {
            println("Error getting access token: ${e.message}")
            throw e
        }
    }


    fun sendNotification(token: String, title: String, body: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val accessToken = getAccessToken()
                println(accessToken)

                val response =
                    client.post("https://fcm.googleapis.com/v1/projects/$projectId/messages:send") {

                        headers {
                            append(HttpHeaders.Authorization, "Bearer $accessToken")

                        }.also { println("Headers: $it") }

                        contentType(ContentType.Application.Json)

                        setBody(buildJsonObject {
                            putJsonObject("message") { // Envuelve todvo dentro de "message"
                                put("token", token)

                                putJsonObject("notification") {
                                    put("title", title)
                                    put("body", body) // Cambiado "text" a "body"
                                }
                                putJsonObject("data") {
                                    put("priority", "high")
                                    put("customId", "02")
                                    put("badge", "1")
                                    put("sound", "")
                                    put("alert", "Alert")
                                }
                            }
                        }.also { println("Cuerpo de la Solicitud: $it") })


                    }
                println("Response: ${response.bodyAsText()}")
                println("Notification request sent successfully: ${response.status}")
            } catch (e: Exception) {
                println("Error sending notification request: ${e.message}")
            }
        }
    }

    private fun JsonObjectBuilder.putJsonObject(key: String, block: JsonObjectBuilder.() -> Unit) {
        put(key, buildJsonObject(block))
    }
}
