package com.cybershield.ui.awareness

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cybershield.databinding.ActivityAwarenessBinding

class AwarenessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAwarenessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAwarenessBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

