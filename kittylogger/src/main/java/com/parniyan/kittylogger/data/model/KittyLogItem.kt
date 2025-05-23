package com.parniyan.kittylogger.data.model

import java.util.UUID


/**
 ** Created by Parniyan on 5/21/2025.
 **
 */

data class KittyLogItem(
    val id: String = UUID.randomUUID().toString(),
    val type: String,
    val topic: String,
    val payload: String,
    val timestamp: Long = System.currentTimeMillis()
)
