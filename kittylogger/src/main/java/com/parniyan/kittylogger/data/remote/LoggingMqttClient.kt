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
 ** Created by Parniyan on 5/21/2025.
 **
 */

class LoggingMqttClient(
    serverURI: String,
    clientId: String,
    private val logger: KittyMqttLogger
) : MqttAsyncClient(serverURI, clientId, null) {

    override fun connect(
        options: MqttConnectOptions?,
        userContext: Any?,
        callback: IMqttActionListener?
    ): IMqttToken {
        logger.onConnectAttempt(serverURI)

        return super.connect(options, userContext, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                logger.onConnectSuccess(serverURI)
                callback?.onSuccess(asyncActionToken)
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                logger.onConnectFailure(serverURI, exception?.message ?: "Unknown")
                callback?.onFailure(asyncActionToken, exception)
            }
        })
    }


    override fun publish(
        topic: String?,
        payload: ByteArray?,
        qos: Int,
        retained: Boolean,
        userContext: Any?,
        callback: IMqttActionListener?
    ): IMqttDeliveryToken {
        if (topic != null && payload != null) {
            logger.onMessagePublished(topic, String(payload))
        }

        return super.publish(topic, payload, qos, retained, userContext, callback)
    }


    override fun setCallback(callback: MqttCallback?) {
        super.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                logger.onConnectionLost(cause?.message ?: "Unknown")
                callback?.connectionLost(cause)
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                if (topic != null && message != null) {
                    logger.onMessageReceived(topic, message.toString())
                }
                callback?.messageArrived(topic, message)
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                val topic = token?.topics?.firstOrNull() ?: "Unknown"
                logger.onAcknowledgeDelivered(topic)
                callback?.deliveryComplete(token)
            }
        })
    }
}
