package com.parniyan.kittylogger.data

import com.parniyan.kittylogger.data.device.KittyMqttLogStore


/**
 ** Created by Parniyan on 5/20/2025.
 **
 */

class ConsoleKittyMqttLogger : KittyMqttLogger {
    private fun save(log: String) {
        KittyMqttLogStore.addLog(log)
        println("[KittyLogger] $log")
    }

    override fun onConnectAttempt(brokerUrl: String) = save("Connecting to $brokerUrl")
    override fun onConnectSuccess(brokerUrl: String) = save("Connected to $brokerUrl")
    override fun onConnectFailure(brokerUrl: String, reason: String) = save("Failed to connect to $brokerUrl: $reason")
    override fun onMessagePublished(topic: String, payload: String, tag: String?) = save("Published to $topic: $payload${tag?.let { " [tag=$it]" } ?: ""}")
    override fun onMessageReceived(topic: String, message: String) = save("Received on $topic: $message")
    override fun onAcknowledgeDelivered(topic: String) = save("Acknowledge delivered for $topic")
    override fun onConnectionLost(reason: String) = save("Connection lost: $reason")
    override fun onSubscribe(topic: String) = save("Subscribed to $topic")
    override fun onUnsubscribe(topic: String) = save("Unsubscribed from $topic")
    override fun onDisconnect() = save("Disconnected from broker")
}