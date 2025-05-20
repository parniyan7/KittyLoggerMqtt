package com.parniyan.kittylogger.data.remote

import com.parniyan.kittylogger.data.KittyMqttLogger
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttAsyncClient
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage


/**
 ** Created by Parniyan on 5/20/2025.
 **
 */

class KittyMqttClient(
    private val client: MqttAsyncClient,
    private val logger: KittyMqttLogger
) {
    fun connectWithCallback(options: MqttConnectOptions, callback: IMqttActionListener?) {
        logger.onConnectAttempt(client.serverURI)
        client.connect(options, null, object : IMqttActionListener {
            override fun onSuccess(token: IMqttToken?) {
                logger.onConnectSuccess(client.serverURI)
                callback?.onSuccess(token)
            }

            override fun onFailure(token: IMqttToken?, exception: Throwable?) {
                logger.onConnectFailure(client.serverURI, exception?.message ?: "Unknown")
                callback?.onFailure(token, exception)
            }
        })
    }

    fun publishMessage(topic: String, payload: ByteArray, qos: Int, retained: Boolean, userContext: Any?, callback: IMqttActionListener?) {
        logger.onMessagePublished(topic, String(payload))
        client.publish(topic, payload, qos, retained, userContext, callback)
    }

    fun registerCallback(callback: MqttCallback) {
        client.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                topic?.let { logger.onMessageReceived(it, message?.toString() ?: "") }
                callback.messageArrived(topic, message)
            }

            override fun connectionLost(cause: Throwable?) {
                logger.onConnectionLost(cause?.message ?: "Unknown")
                callback.connectionLost(cause)
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                val topic = token?.topics?.firstOrNull() ?: "Unknown"
                logger.onAcknowledgeDelivered(topic)
                callback.deliveryComplete(token)
            }
        })
    }

    fun subscribeToTopic(topic: String, qos: Int, callback: IMqttActionListener? = null) {
        logger.onSubscribe(topic)
        client.subscribe(topic, qos, null, callback)
    }

    fun unsubscribeFromTopic(topic: String, callback: IMqttActionListener? = null) {
        logger.onUnsubscribe(topic)
        client.unsubscribe(topic, null, callback)
    }

    fun disconnectClient(callback: IMqttActionListener? = null) {
        logger.onDisconnect()
        client.disconnect(null, callback)
    }

    fun isClientConnected(): Boolean = client.isConnected

    fun getRawClient(): MqttAsyncClient = client
}

