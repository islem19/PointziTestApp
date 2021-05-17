package com.pointzi.library

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pointzi.library.test.R

class TestActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buildCustomPointzi() {
        CustomPointzi(this).build()
    }
}