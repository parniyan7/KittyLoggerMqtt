# 🐾 KittyLoggerMqtt

KittyLoggerMqtt is a lightweight, plug-and-play MQTT event logger for Android. It wraps your existing `MqttAsyncClient`, logs events like connect, publish, subscribe, and displays them in a beautiful Jetpack Compose UI — just like Chucker, but for MQTT!

---

## ✨ Features

- 🔌 Plug-and-play logging via `LoggingMqttClient`
- 📋 Logs MQTT events with topic, type, payload, and timestamp
- 🐱 Built-in Compose screen to inspect logs (`KittyMqttLogScreen`)
- 🛎️ Optional in-app notification launcher
- 📤 Supports console logging + in-app viewer
- ⚙️ Works seamlessly with existing `MqttAsyncClient`-based projects

---

## 📦 Installation

Add this to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```
And in your build.gradle.kts:
```kotlin
dependencies {
    implementation("com.github.parniyan7:KittyLoggerMqtt:1.0.14")
}
```
🚀 Quick Setup
1. Replace your MQTT client setup:
```kotlin
val mqttClient = LoggingMqttClient(
    serverURI = brokerUrl,
    clientId = clientId,
    logger = ConsoleKittyMqttLogger()
)
mqttClient.setCallback(...)
mqttClient.connect(...)
```
2. Show logs from a notification (optional):

```kotlin
if (BuildConfig.DEBUG) {
    KittyLoggerUI.showNotification(context)
}
```
3. Add log screen activity in AndroidManifest.xml:

```xml
<activity android:name="com.parniyan.kittylogger.ui.KittyMqttLogActivity" />
```

🧪 Example Output
Each log entry shows:

Type: publish, message, subscribe, ack, etc.
Topic: MQTT topic
Payload: Published or received message
Time: Formatted timestamp
