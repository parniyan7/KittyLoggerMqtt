package com.parniyan.kittylogger.data.model

import com.parniyan.kittylogger.presentation.KittyLoggerUI


/**
 ** Created by Parniyan on 5/20/2025.
 **
 */

object KittyMqttLogStore {
    private val logs = mutableListOf<KittyLogItem>()

    fun addLog(log: KittyLogItem) {
        logs.add(0, log)
        KittyLoggerUI.showNotification()
    }

    fun getLogs(): List<KittyLogItem> = logs.toList()

    fun clear() {
        logs.clear()
    }
}