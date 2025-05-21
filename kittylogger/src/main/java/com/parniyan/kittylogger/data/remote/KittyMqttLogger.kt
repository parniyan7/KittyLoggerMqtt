package com.parniyan.kittylogger.data.remote


/**
 ** Created by Parniyan on 5/20/2025.
 **
 */

interface KittyMqttLogger {
    fun onConnectAttempt(brokerUrl: String)
    fun onConnectSuccess(brokerUrl: String)
    fun onConnectFailure(brokerUrl: String, reason: String)
    fun onMessagePublished(topic: String, payload: String, tag: String? = null)
    fun onMessageReceived(topic: String, message: String)
    fun onAcknowledgeDelivered(topic: String)
    fun onConnectionLost(reason: String)
    fun onSubscribe(topic: String)
    fun onUnsubscribe(topic: String)
    fun onDisconnect()
}