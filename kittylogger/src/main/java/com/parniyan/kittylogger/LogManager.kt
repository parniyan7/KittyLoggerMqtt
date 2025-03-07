package com.parniyan.kittylogger

import androidx.compose.runtime.mutableStateListOf
import com.parniyan.kittylogger.data.model.KittyLog


/**
 ** Created by Parniyan on 3/7/2025.
 **
 */

object LogManager {
    private val _logs = mutableStateListOf<KittyLog>()
    val logs: List<KittyLog> get() = _logs

    fun addLog(log: KittyLog) {
        _logs.add(log)
    }

    fun clearLogs() {
        _logs.clear()
    }
}