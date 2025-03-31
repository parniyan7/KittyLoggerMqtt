package com.parniyan.kittylogger
import com.parniyan.kittylogger.data.device.NotificationHelper
import com.parniyan.kittylogger.data.model.KittyLog
import com.parniyan.kittylogger.data.model.LogType
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttAsyncClient
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage


/**
 ** Created by Parniyan on 3/7/2025.
 **
 */




class KittyMqttInterceptor(
    private val mqttClient: MqttAsyncClient,
    private val logger: KittyLogger = ConsoleLogger(),
    private val notificationHelper: NotificationHelper
) : MqttCallback {

    init {
        // Set the callback on the MQTT client
        mqttClient.setCallback(this)
    }

    fun publish(topic: String, payload: ByteArray, qos: Int, retained: Boolean) {
        val log = KittyLog(
            timestamp = System.currentTimeMillis(),
            event = "Outgoing Topic: $topic, Payload: ${String(payload)}, QoS: $qos, Retained: $retained",
            type = LogType.OUTGOING
        )
        LogManager.addLog(log)
        logger.logEvent(log.event)

        // Send notification for the outgoing message
        notificationHelper.sendNotification("Message Sent", "Topic: $topic, Payload: ${String(payload)}")
        mqttClient.publish(topic, payload, qos, retained)
    }

    // Handle incoming messages
    override fun messageArrived(topic: String, message: MqttMessage) {
        val log = KittyLog(
            timestamp = System.currentTimeMillis(),
            event = "Incoming Topic: $topic, Payload: ${String(message.payload)}, QoS: ${message.qos}",
            type = LogType.INCOMING
        )
        LogManager.addLog(log)
        logger.logEvent(log.event)
        // Send notification for the incoming message
        notificationHelper.sendNotification("New Message", "Topic: $topic, Message: ${String(message.payload)}")
    }

    // Handle connection loss
    override fun connectionLost(cause: Throwable) {
        val log = KittyLog(
            timestamp = System.currentTimeMillis(),
            event = "Connection Lost: ${cause.message}",
            type = LogType.CONNECTION_LOST
        )
        LogManager.addLog(log)
        logger.logEvent(log.event)
    }

    // Handle delivery completion (for QoS 1 and 2)
    override fun deliveryComplete(token: IMqttDeliveryToken) {
        val log = KittyLog(
            timestamp = System.currentTimeMillis(),
            event = "Delivery Complete: ${token.messageId}",
            type = LogType.DELIVERY_COMPLETE
        )
        LogManager.addLog(log)
        logger.logEvent(log.event)
    }
}