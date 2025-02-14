package com.mtc_hack.audio

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class PermissionManager(
    private val onPermissionGranted: () -> Unit,
    private val onShowRationaleDialog: () -> Unit,
    private val onShowSettingsDialog: () -> Unit,
    private val context: Context,
) {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    fun initialize(activityResultCaller: ActivityResultCaller) {
        requestPermissionLauncher = activityResultCaller.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val isGranted = permissions.entries.any { it.value }
            if (isGranted) {
                onPermissionGranted()
            } else {
                onShowSettingsDialog()
            }
        }
    }

    fun checkAndRequestLocationPermission() {
        when {
            hasLocationPermission() -> {
                onPermissionGranted()
            }

            shouldShowRequestPermissionRationale() -> {
                onShowRationaleDialog()
            }

            else -> requestPermission()
        }
    }

    fun requestPermission() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.RECORD_AUDIO
            )
        )
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRequestPermissionRationale(): Boolean {
        val tmp = ActivityCompat.shouldShowRequestPermissionRationale(
            context as FragmentActivity, Manifest.permission.RECORD_AUDIO
        )
        return tmp
    }
}
