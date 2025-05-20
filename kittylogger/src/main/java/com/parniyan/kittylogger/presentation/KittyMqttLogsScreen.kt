package com.parniyan.kittylogger.presentation

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.parniyan.kittylogger.data.device.KittyMqttLogStore
import java.lang.reflect.Modifier
import java.util.Locale


/**
 ** Created by Parniyan on 5/20/2025.
 **
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KittyMqttLogScreen() {
    val logs = remember { KittyMqttLogStore.getLogs() }

    Column(
        modifier = androidx.compose.ui.Modifier.padding(16.dp)
    ) {
        Text("KittyLogger MQTT Logs")
        Text(text = logs.toString())

    }
}
