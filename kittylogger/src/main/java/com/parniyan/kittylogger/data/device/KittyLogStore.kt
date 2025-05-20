package com.parniyan.kittylogger.data.device


/**
 ** Created by Parniyan on 5/20/2025.
 **
 */

object KittyMqttLogStore {
    private val logs = mutableListOf<String>()

    fun addLog(log: String) {
        logs.add(0, log) // newest first
    }

    fun getLogs(): List<String> = logs.toList()

    fun clear() {
        logs.clear()
    }
}