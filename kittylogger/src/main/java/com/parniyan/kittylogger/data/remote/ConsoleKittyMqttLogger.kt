package com.parniyan.kittylogger.data.remote

import com.parniyan.kittylogger.data.model.KittyMqttLogStore
import com.parniyan.kittylogger.data.model.KittyLogItem


/**
 ** Created by Parniyan on 5/20/2025.
 **
 */

class ConsoleKittyMqttLogger : KittyMqttLogger {
    private fun log(type: String, topic: String, payload: String = "") {
        val log = KittyLogItem(
            type = type,
            topic = topic,
            payload = payload
        )
        KittyMqttLogStore.addLog(log)
        println("[KittyLogger][$type] $topic ${if (payload.isNotBlank()) "-> $payload" else ""}")
    }

    override fun onConnectAttempt(brokerUrl: String) = log("connect-attempt", brokerUrl)
    override fun onConnectSuccess(brokerUrl: String) = log("connect-success", brokerUrl)
    override fun onConnectFailure(brokerUrl: String, reason: String) = log("connect-failure", brokerUrl, reason)
    override fun onMessagePublished(topic: String, payload: String, tag: String?) = log("publish", topic, payload)
    override fun onMessageReceived(topic: String, message: String) = log("message", topic, message)
    override fun onAcknowledgeDelivered(topic: String) = log("ack", topic)
    override fun onConnectionLost(reason: String) = log("disconnect", "connection-lost", reason)
    override fun onSubscribe(topic: String) = log("subscribe", topic)
    override fun onUnsubscribe(topic: String) = log("unsubscribe", topic)
    override fun onDisconnect() = log("disconnect", "manual")
}