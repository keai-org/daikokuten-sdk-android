package com.daikokuten.sdk

import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

public class Daikokuten {
    companion object {
        // Static method to send context data via POST using HttpURLConnection
        fun context(userId: String, eventId: String, action: String) {
            thread {
                try {
                    val url = URL("https://daikokuten-7c6ffc95ca37.herokuapp.com/context")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.setRequestProperty("Content-Type", "application/json")
                    connection.doOutput = true

                    val json = """{"user_id": "$userId", "event_id": "$eventId", "action": "$action"}"""
                    val writer = OutputStreamWriter(connection.outputStream)
                    writer.write(json)
                    writer.flush()
                    writer.close()

                    val responseCode = connection.responseCode
                    if (responseCode in 200..299) {
                        println("Context data sent successfully")
                    } else {
                        println("Unexpected response code: $responseCode")
                    }
                    connection.disconnect()
                } catch (e: Exception) {
                    println("POST request failed: ${e.message}")
                }
            }
        }
    }
}