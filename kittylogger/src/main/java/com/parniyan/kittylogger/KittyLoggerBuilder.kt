package com.parniyan.kittylogger

import org.eclipse.paho.client.mqttv3.IMqttClient


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

    fun build(mqttClient: IMqttClient): KittyMqttInterceptor {
        return KittyMqttInterceptor(mqttClient, logger)
    }
}