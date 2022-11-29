package ru.netology.nmedia.pusher

import android.os.Message
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import java.io.FileInputStream


fun main(){
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(FileInputStream("fcm.json")))
        .setDatabaseUrl(dbUrl)
        .build()
    FirebaseApp.initializeApp(options)
    val message= Message.builder()
        .putData("action", "LIKE")
        .putData("content", """{
        "userID": 1,
        "userName": "Evgeniya",
        "postId": 2,
        "postAuthor": "Netology"
        }""".trimIndent())
        .setToken(token)
        .build()
    FirebaseMessaging.getInstance().send(message)

}