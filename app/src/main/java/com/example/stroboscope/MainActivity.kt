package com.example.stroboscope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val FLASHLIGHT_REQUEST_CODE = 1
    private var flashlightBlinker: FlashlightBlinker? = null
    private lateinit var startButton: Button
    private var isBlinking = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashlightBlinker = FlashlightBlinker(this)
        startButton = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            toggleBlinking()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopBlinking()
    }

    private fun toggleBlinking() {
        if (isBlinking) {
            stopBlinking()
        } else {
            startBlinking()
        }
    }

    private fun startBlinking() {
        isBlinking = true
        startButton.text = "Stop Blinking"

        CoroutineScope(Dispatchers.Default).launch {
            flashlightBlinker?.startBlinking()
        }
    }

    private fun stopBlinking() {
        isBlinking = false
        startButton.text = "Start Blinking"
        flashlightBlinker?.stopBlinking()
    }
}