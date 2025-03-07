package com.parniyan.kittylogger.data.model


/**
 ** Created by Parniyan on 3/7/2025.
 **
 */

data class KittyLog(
    val timestamp: Long, // Timestamp of the log
    val event: String,  // Log message (e.g., "Outgoing Topic: my/topic")
    val type: LogType   // Type of log (e.g., incoming or outgoing)
)

enum class LogType {
    INCOMING, OUTGOING, CONNECTION_LOST, DELIVERY_COMPLETE
}
