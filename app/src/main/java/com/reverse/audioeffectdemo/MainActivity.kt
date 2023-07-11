package com.reverse.audioeffectdemo

import android.media.audiofx.Equalizer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.reverse.audioeffectdemo.databinding.ActivityMainBinding
import com.reverse.audioeffectdemo.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val viewModel: MainActivityViewModel by lazy {
        MainActivityViewModel()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.setUP(this)
        viewModel.createEqualizer()
        val nameList = viewModel.getEqualizerPreset()
        println("nameList size ${nameList.size}")
//        binding.spinPreset.apply {
//            adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameList)
//
//        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinPreset.adapter = adapter

        val eq = viewModel.equalizer
        eq.let {
            val count = it!!.numberOfBands
            for(i in 0 until count) {
                val center = eq!!.getCenterFreq(i.toShort())
                Log.d("center Freq", center.toString())
                binding.seekbarBox.addView(createSeekBar(center / 1000))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.spinPreset.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                   viewModel.applyEffect(p2.toShort())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        binding.btnStart.setOnClickListener {
            viewModel.setAudio(packageName)
            viewModel.playAudio()
        }

        binding.btnPause.setOnClickListener {
            viewModel.pauseAudio()
        }
    }

    fun createSeekBar(freq: Int): View {
        val linearLayout = LinearLayout(this).apply {
            val layoutParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams = layoutParam
        }
        val seekBar = SeekBar(this).apply {
//            max = 10
//            min = 0
            val layoutParam = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams = layoutParam
        }
        val label = TextView(this).apply {
            text = freq.toString()
            val wdith = resources.displayMetrics.density * 200
            println("densityDpi ${resources.displayMetrics.densityDpi}")
            println("density ${resources.displayMetrics.density}")
            val layoutParam =  ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//            layoutParams = layoutParam
        }
        linearLayout.apply {
            addView(label)
            addView(seekBar)

        }
        return linearLayout
    }
}