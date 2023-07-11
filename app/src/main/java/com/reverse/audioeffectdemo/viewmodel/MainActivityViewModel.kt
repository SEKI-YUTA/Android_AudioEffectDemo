package com.reverse.audioeffectdemo.viewmodel

import android.content.Context
import android.media.audiofx.Equalizer
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.reverse.audioeffectdemo.R

class MainActivityViewModel: ViewModel() {
    var exoPlayer: ExoPlayer? = null
    var equalizer: Equalizer? = null
    fun setUP(context: Context) {
        exoPlayer = SimpleExoPlayer.Builder(context)
            .build()
    }

    fun createEqualizer() {
        equalizer = Equalizer(0, exoPlayer!!.audioSessionId)
        equalizer!!.enabled = true
    }

    fun getEqualizerPreset(): MutableList<String> {
        val presetCount = equalizer!!.numberOfPresets
        val presetList = mutableListOf<String>()

        for(i in 0 until presetCount) {
            presetList.add(equalizer!!.getPresetName(i.toShort()))
        }

        return presetList
    }

    fun setAudio(packageName: String) {
        println("set audio")
        val uriStr = "android.resource://" + packageName + "/" + R.raw.demo
        exoPlayer?.setMediaItem(MediaItem.fromUri(uriStr))
    }

    fun playAudio() {
        println("play audio")
        exoPlayer?.prepare()
        exoPlayer?.play()
    }

    fun pauseAudio() {
        println("pause audio")
        exoPlayer?.pause()
    }

    fun applyEffect(idx: Short) {
        equalizer?.usePreset(idx)
    }

}