package com.parniyan.kittylogger


/**
 ** Created by Parniyan on 3/7/2025.
 **
 */

class ConsoleLogger : KittyLogger {
    override fun logEvent(event: String) {
        println("[KittyLogger] $event")
    }
}