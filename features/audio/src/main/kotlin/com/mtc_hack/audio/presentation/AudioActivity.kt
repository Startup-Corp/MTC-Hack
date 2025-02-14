package com.mtc_hack.audio.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.mtc_hack.audio.data.AudioPlayerImpl
import com.mtc_hack.audio.data.AudioRecorderImpl
import com.mtc_hack.audio.databinding.ActivityAudioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AudioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudioBinding
    private val recorder by lazy { AudioRecorderImpl(applicationContext) }
    private val player by lazy { AudioPlayerImpl(applicationContext) }
    private var audioFile: File? = null
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.RECORD_AUDIO), 0
        )
        setOnClickListeners()
    }

    private suspend fun uploadFileToServer(file: File) = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(
            "file", file.name, file.asRequestBody("audio/mpeg".toMediaTypeOrNull())
        ).build()

        val request = Request.Builder()
            .url("https://your-server-url.com/upload") // Замените на URL вашего сервера
            .post(requestBody).build()

        client.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                Log.d("AudioActivity", "File uploaded successfully")
            } else {
                Log.e("AudioActivity", "Failed to upload file: ${response.message}")
            }
        }
    }

    private fun setOnClickListeners() {
        binding.startRecordingButton.setOnClickListener {
            File(cacheDir, "audio.mp3").also {
                recorder.start(it)
                audioFile = it
            }
        }

        binding.stopRecordingButton.setOnClickListener {
            recorder.stop()
        }

        binding.playButton.setOnClickListener {
            player.playFile(audioFile ?: return@setOnClickListener)
        }

        binding.stopPlayingButton.setOnClickListener {
            player.stop()
        }

        binding.uploadButton.setOnClickListener {
            audioFile?.let {
                uiScope.launch {
                    uploadFileToServer(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
