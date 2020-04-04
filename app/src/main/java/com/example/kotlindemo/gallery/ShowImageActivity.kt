package com.example.kotlindemo.gallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlindemo.R
import kotlinx.android.synthetic.main.activity_show_image.*
import java.io.File

class ShowImageActivity : AppCompatActivity() {

    private val TAG = ShowImageActivity::class.simpleName as String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        back_iv.setOnClickListener { onBackPressed() }

        val imagePath = intent.getStringExtra("imagePath") ?: return
        name.text = intent.getStringExtra("imageName")

        val file = File(imagePath)
        if (file.exists()) {
            val bitmap: Bitmap = BitmapFactory.decodeFile(file.absolutePath)
            imageView?.setImageBitmap(bitmap)
        }
    }
}
