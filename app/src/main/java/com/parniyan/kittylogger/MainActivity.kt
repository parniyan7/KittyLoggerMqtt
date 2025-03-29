package com.parniyan.kittylogger

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.parniyan.kittylogger.data.device.MqttManager
import com.parniyan.kittylogger.data.model.KittyLog
import com.parniyan.kittylogger.presentation.LogItem
import com.parniyan.kittylogger.ui.theme.KittyLoggerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Create an instance of MqttManager
        val mqttManager = MqttManager()

        // Initialize the MQTT client
        mqttManager.initMqttClient("tcp://broker.hivemq.com:1883", "YourClientId")

        // Subscribe to a topic
        try {
            mqttManager.subscribeToTopic("my/topic", 1)
        } catch (e: Exception) {
            Log.e("MqttManager", "Subscription error: ${e.message}")
        }

        // Publish a message to the topic
        try {
            mqttManager.publishMessage("my/topic", "Hello, MQTT!")
        } catch (e: Exception){

        }

        setContent {
            KittyLoggerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Optionally, display logs in a UI component
                    LogViewer(mqttManager, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun LogViewer(mqttManager: MqttManager, modifier: Modifier = Modifier) {
    val logs: List<KittyLog> = mqttManager.getLogs()

    // Display logs using your LogItem composable
    Column {
        for (log in logs) {
            LogItem(log) // Assuming LogItem is a composable to display each log
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KittyLoggerTheme {
        Greeting("Android")
    }
}