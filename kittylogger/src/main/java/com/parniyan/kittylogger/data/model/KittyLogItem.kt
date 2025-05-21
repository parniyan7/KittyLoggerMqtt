package com.parniyan.kittylogger.data.model


/**
 ** Created by Parniyan on 5/21/2025.
 **
 */

data class KittyLogItem(
    val type: String, // e.g., "publish", "subscribe", "ack", "message"
    val topic: String,
    val payload: String,
    val timestamp: Long = System.currentTimeMillis()
)
