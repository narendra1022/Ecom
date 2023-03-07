package com.example.ecom.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.TextView
import com.example.ecom.R
import com.example.ecom.data.SpecialProductDataClass

class Detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val food = intent.getParcelableExtra<SpecialProductDataClass>("food")
        if (food != null) {
            val Iv: ImageView = findViewById(R.id.iv)
            Iv.setImageResource(food.productImage)
        }
    }
}