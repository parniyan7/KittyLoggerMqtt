package com.parniyan.kittylogger.data.device

import android.content.Intent


/**
 ** Created by Parniyan on 3/31/2025.
 **
 */

interface NotificationHandler {
    fun getNotificationIntent(): Intent
}