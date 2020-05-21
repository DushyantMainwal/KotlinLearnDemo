package com.example.kotlindemo.room_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.kotlindemo.R
import kotlinx.android.synthetic.main.activity_sleep.*

class SleepActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sleep)

        setSupportActionBar(toolbar)

        NavigationUI.setupActionBarWithNavController(
            this,
            findNavController(R.id.main_nav_fragment)
        )
    }
}
