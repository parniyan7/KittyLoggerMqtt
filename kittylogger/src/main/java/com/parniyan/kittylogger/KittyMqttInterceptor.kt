package com.parniyan.kittylogger
import com.parniyan.kittylogger.data.model.KittyLog
import com.parniyan.kittylogger.data.model.LogType
import org.eclipse.paho.client.mqttv3.*


/**
 ** Created by Parniyan on 3/7/2025.
 **
 */


class KittyMqttInterceptor(
    private val mqttClient: IMqttClient,
    private val logger: KittyLogger = ConsoleLogger()
) : IMqttClient by mqttClient, MqttCallback {

    init {
        // Set the callback on the MQTT client
        mqttClient.setCallback(this)
    }

    override fun publish(topic: String, payload: ByteArray, qos: Int, retained: Boolean) {
        val log = KittyLog(
            timestamp = System.currentTimeMillis(),
            event = "Outgoing Topic: $topic, Payload: ${String(payload)}, QoS: $qos, Retained: $retained",
            type = LogType.OUTGOING
        )
        LogManager.addLog(log)
        logger.logEvent(log.event)
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
    }

    // Handle connection loss
    override fun connectionLost(cause: Throwable) {
        val log = KittyLog(
            timestamp = System.currentTimeMillis(),
            event = "Connection Lost: ${cause.message}",
            type = LogType.INCOMING
        )
        LogManager.addLog(log)
        logger.logEvent(log.event)
    }

    // Handle delivery completion (for QoS 1 and 2)
    override fun deliveryComplete(token: IMqttDeliveryToken) {
        val log = KittyLog(
            timestamp = System.currentTimeMillis(),
            event = "Delivery Complete: ${token.messageId}",
            type = LogType.OUTGOING
        )
        LogManager.addLog(log)
        logger.logEvent(log.event)
    }
}