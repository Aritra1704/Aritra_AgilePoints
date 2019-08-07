package com.example.aritra_agilepoint.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aritra_agilepoint.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}
