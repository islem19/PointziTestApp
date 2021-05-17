package com.pointzi.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pointzi.library.CustomPointzi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // build our pointzi custom model
        CustomPointzi(this).build()

        btn.setOnClickListener {
            startActivity(Intent(this,DetailsActivity::class.java))
        }
    }
}