package com.parniyan.kittylogger.data.model


/**
 ** Created by Parniyan on 5/21/2025.
 **
 */

data class KittyLogItem(
    val type: String,         // e.g., "publish", "subscribe", "ack"
    val topic: String,        // topic name
    val payload: String = "", // optional payload
    val timestamp: Long = System.currentTimeMillis() // for UI sorting
)
