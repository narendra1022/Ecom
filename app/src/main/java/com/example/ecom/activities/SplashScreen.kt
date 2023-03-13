package com.example.ecom.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.example.ecom.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val backgroundImage: TextView = findViewById(R.id.intro_text)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        backgroundImage.startAnimation(slideAnimation)
        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }, 4500) // 3000 is the delayed time in milliseconds.

    }
}