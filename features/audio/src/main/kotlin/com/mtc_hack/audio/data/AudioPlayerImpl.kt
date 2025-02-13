package com.mtc_hack.audio.data

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AudioPlayerImpl(private val context: Context) {

    private var player: MediaPlayer? = null

    fun playFile(filePath: File) {
        MediaPlayer.create(context, filePath.toUri()).apply {
            start()
            player = this
        }
    }

    fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}