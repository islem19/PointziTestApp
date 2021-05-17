package com.pointzi.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pointzi.library.CustomPointzi

private const val ICON_URL = "https://1000logos.net/wp-content/uploads/2018/11/GitHub-logo.jpg"
class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        // customize the icon
        CustomPointzi(this)
            .setIcon(ICON_URL)
            .build()

    }
}