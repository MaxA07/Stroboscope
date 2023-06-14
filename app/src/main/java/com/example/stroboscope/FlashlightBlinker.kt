package com.example.stroboscope

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class FlashlightBlinker(private val activity: AppCompatActivity) {
    private var isFlashlightOn = false
    private var blinkJob: Job? = null

    fun startBlinking() {
        blinkJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                toggleFlashlight()
                delay(BLINK_DELAY)
            }
        }
    }

    fun stopBlinking() {
        blinkJob?.cancel()
        turnOffFlashlight()
    }

    private fun toggleFlashlight() {
        if (isFlashlightOn) {
            turnOffFlashlight()
        } else {
            turnOnFlashlight()
        }
    }

    private fun turnOnFlashlight() {
        try {
            val cameraManager = activity.getSystemService(AppCompatActivity.CAMERA_SERVICE) as CameraManager
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, true)
            isFlashlightOn = true
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun turnOffFlashlight() {
        try {
            val cameraManager = activity.getSystemService(AppCompatActivity.CAMERA_SERVICE) as CameraManager
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, false)
            isFlashlightOn = false
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val BLINK_DELAY: Long = 500 // Задержка в миллисекундах между миганиями
    }

}