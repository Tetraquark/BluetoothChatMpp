package ru.tetraquark.bluetoothchatmpp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.tetraquark.bluetoothchatmpp.presentation.devicediscovery.DeviceDiscoveryActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, DeviceDiscoveryActivity::class.java))
    }
}
