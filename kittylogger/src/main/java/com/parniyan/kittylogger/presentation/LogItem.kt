package com.parniyan.kittylogger.presentation

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parniyan.kittylogger.data.model.KittyLog
import com.parniyan.kittylogger.data.model.LogType
import java.util.Locale


/**
 ** Created by Parniyan on 3/7/2025.
 **
 */

@Composable
fun LogItem(log: KittyLog) {
    /*val backgroundColor = when (log.type) {
        LogType.INCOMING -> Color.YELLOW
        LogType.OUTGOING -> Color.YELLOW
        LogType.CONNECTION_LOST -> Color.RED
        LogType.DELIVERY_COMPLETE -> Color.GREEN
    }*/
    Column {
    }
   /* Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = log.event)
        Text(
            text = "Timestamp: ${
                SimpleDateFormat(
                    "HH:mm:ss",
                    Locale.getDefault()
                ).format(log.timestamp)
            }"
        )
    }*/
}