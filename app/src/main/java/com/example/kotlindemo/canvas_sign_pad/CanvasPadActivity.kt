package com.example.kotlindemo.canvas_sign_pad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlindemo.R
import kotlinx.android.synthetic.main.activity_canvas_pad.*

class CanvasPadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas_pad)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportActionBar?.title = "Drawing Pad"

        toolbar.setNavigationOnClickListener { onBackPressed() }

        sign_in_pad.addView(SignaturePadView(this))
    }
}
