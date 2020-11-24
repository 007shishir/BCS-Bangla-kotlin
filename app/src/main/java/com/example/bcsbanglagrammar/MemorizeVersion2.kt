package com.example.bcsbanglagrammar

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MemorizeVersion2 : AppCompatActivity() {
    var viewModel: Memorize_ViewMode2? = null
    var clicked = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memorize_version2)
    }
}