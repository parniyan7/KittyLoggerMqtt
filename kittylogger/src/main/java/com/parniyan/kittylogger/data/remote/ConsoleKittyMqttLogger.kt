package com.parniyan.kittylogger.data.remote

import com.parniyan.kittylogger.data.model.KittyMqttLogStore
import com.parniyan.kittylogger.data.model.KittyLogItem


/**
 ** Created by Parniyan on 5/20/2025.
 **
 */

class ConsoleKittyMqttLogger : KittyMqttLogger {

    private fun saveStructured(type: String, topic: String, payload: String = "") {
        val log = KittyLogItem(
            type = type,
            topic = topic,
            payload = payload
        )
        KittyMqttLogStore.addLog(log)

        // Still log to console for real-time feedback
        println("[KittyLogger][$type] $topic ${if (payload.isNotBlank()) "-> $payload" else ""}")
    }

    override fun onConnectAttempt(brokerUrl: String) = saveStructured("connect-attempt", brokerUrl)
    override fun onConnectSuccess(brokerUrl: String) = saveStructured("connect-success", brokerUrl)
    override fun onConnectFailure(brokerUrl: String, reason: String) = saveStructured("connect-failure", brokerUrl, reason)
    override fun onMessagePublished(topic: String, payload: String, tag: String?) = saveStructured("publish", topic, payload)
    override fun onMessageReceived(topic: String, message: String) = saveStructured("message", topic, message)
    override fun onAcknowledgeDelivered(topic: String) = saveStructured("ack", topic)
    override fun onConnectionLost(reason: String) = saveStructured("disconnect", "connection-lost", reason)
    override fun onSubscribe(topic: String) = saveStructured("subscribe", topic)
    override fun onUnsubscribe(topic: String) = saveStructured("unsubscribe", topic)
    override fun onDisconnect() = saveStructured("disconnect", "manual")
}
