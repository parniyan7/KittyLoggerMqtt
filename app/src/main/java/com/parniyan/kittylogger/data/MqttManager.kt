package com.parniyan.kittylogger.data

import com.parniyan.kittylogger.ConsoleLogger
import com.parniyan.kittylogger.KittyLoggerBuilder
import com.parniyan.kittylogger.LogManager
import com.parniyan.kittylogger.data.model.KittyLog
import com.parniyan.kittylogger.data.model.LogType
import org.eclipse.paho.client.mqttv3.MqttAsyncClient


/**
 ** Created by Parniyan on 3/26/2025.
 **
 */

class MqttManager {
    private var mqttClient: MqttAsyncClient? = null

    fun initMqttClient(url: String, clientId: String) {
        mqttClient = MqttAsyncClient(url, clientId, null).also { client ->
            val kittyLogger = ConsoleLogger()
            val kittyMqttInterceptor = KittyLoggerBuilder()
                .setLogger(kittyLogger)
                .build(client) // Pass the client directly

            client.setCallback(kittyMqttInterceptor)
        }
    }

    fun publishMessage(topic: String, payload: String, qos: Int = 1, retained: Boolean = false) {
        mqttClient?.publish(topic, payload.toByteArray(), qos, retained)
    }

    fun subscribeToTopic(topic: String, qos: Int) {
        mqttClient?.subscribe(topic, qos) { _, message ->
            // Log incoming messages
            val log = KittyLog(
                timestamp = System.currentTimeMillis(),
                event = "Subscribed to topic: $topic, Message: ${String(message.payload)}",
                type = LogType.INCOMING
            )
            LogManager.addLog(log)
        }
    }

    fun clearLogs() {
        LogManager.clearLogs()
    }

    fun getLogs(): List<KittyLog> {
        return LogManager.logs
    }
}