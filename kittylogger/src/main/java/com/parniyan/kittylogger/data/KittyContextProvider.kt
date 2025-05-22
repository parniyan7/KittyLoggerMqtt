package com.parniyan.kittylogger.data

import android.content.Context


/**
 ** Created by Parniyan on 5/22/2025.
 **
 */

object KittyContextProvider {
    private var appContext: Context? = null
    fun initialize(context: Context) {
        appContext = context.applicationContext
    }
    fun get(): Context? = appContext
}