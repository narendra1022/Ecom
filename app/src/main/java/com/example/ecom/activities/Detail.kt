package com.example.ecom.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.example.ecom.R
import com.example.ecom.data.SpecialProductDataClass

class Detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView)

        windowInsetsController?.isAppearanceLightNavigationBars = true
        windowInsetsController?.isAppearanceLightStatusBars = true

        setContentView(R.layout.activity_detail)
        val food = intent.getParcelableExtra<SpecialProductDataClass>("food")
        if (food != null) {
            val Iv: ImageView = findViewById(R.id.iv)
            Iv.setImageResource(food.productImage)
        }
    }
}