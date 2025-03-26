package com.parniyan.kittylogger

import org.eclipse.paho.client.mqttv3.MqttAsyncClient


/**
 ** Created by Parniyan on 3/7/2025.
 **
 */

class KittyLoggerBuilder {
    private var logger: KittyLogger = ConsoleLogger()

    fun setLogger(logger: KittyLogger): KittyLoggerBuilder {
        this.logger = logger
        return this
    }

    fun build(mqttClient: MqttAsyncClient): KittyMqttInterceptor {
        return KittyMqttInterceptor(mqttClient, logger)
    }
}