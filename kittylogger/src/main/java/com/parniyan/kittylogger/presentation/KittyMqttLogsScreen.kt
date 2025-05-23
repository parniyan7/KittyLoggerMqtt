package com.parniyan.kittylogger.presentation

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.parniyan.kittylogger.R
import com.parniyan.kittylogger.data.model.KittyLogItem
import com.parniyan.kittylogger.data.model.KittyMqttLogStore
import java.util.Date
import java.util.Locale


/**
 ** Created by Parniyan on 5/20/2025.
 **
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KittyMqttLogScreen(modifier: Modifier) {
    val logs = remember { mutableStateListOf<KittyLogItem>() }

    LaunchedEffect(Unit) {
        logs.clear()
        logs.addAll(KittyMqttLogStore.getLogs())
    }


    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.padding(top = 16.dp),
            title = {
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.kitty_logo),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Kitty Mqtt Interceptor",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFFC1ADE6)
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    KittyMqttLogStore.clear()
                    logs.clear()
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Clear Logs")
                }
            })
    }) {
        LazyColumn(
            modifier = modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            items(items = logs.reversed()) { log ->
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
            Text(
                "Type: ${log.type}",
                style = MaterialTheme.typography.titleSmall,
                color = Color(0xFF1E88E5)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Topic: ${log.topic}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF43A047)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Payload:",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFFF4511E)
            )
            Text(
                log.payload,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Time: $timeFormatted",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }
    }
}

