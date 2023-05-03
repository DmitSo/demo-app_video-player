package com.linkedin.dzmitrysalauyeu.demoappvideoplayer.videoscreen.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.linkedin.dzmitrysalauyeu.demoappvideoplayer.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * SPA or something like that. Should be easier to extend in the future with help
 * of some nav-lib or smth custom-written so no blame here
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}