package com.parniyan.kittylogger.presentation

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parniyan.kittylogger.data.model.KittyMqttLogStore
import com.parniyan.kittylogger.data.model.KittyLogItem
import java.util.Date
import java.util.Locale


/**
 ** Created by Parniyan on 5/20/2025.
 **
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KittyMqttLogScreen() {
    val logs = remember { KittyMqttLogStore.getLogs() }

    Scaffold(topBar = {
        TopAppBar(title = { Text("KittyLogger MQTT Logs") })
    }) {
        LazyColumn(modifier = Modifier.padding(it).padding(16.dp)) {
            items(items = logs, key = { it.timestamp }) { log ->
                KittyLogCard(log)
            }
        }

    }
}

@Composable
fun KittyLogCard(log: KittyLogItem) {
    val timeFormatted = remember(log.timestamp) {
        SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault()).format(Date(log.timestamp))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Type: ${log.type}", style = MaterialTheme.typography.titleSmall)
            Text("Topic: ${log.topic}", style = MaterialTheme.typography.bodyMedium)
            Text("Payload: ${log.payload}", style = MaterialTheme.typography.bodySmall)
            Text("Time: $timeFormatted", style = MaterialTheme.typography.labelSmall)
        }
    }
}
