package com.q.hdate

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, ChoiceActivity::class.java)) // Chuyển đến ChoiceActivity hoặc MainActivity
            finish()
        }, 2000) // Thời gian chờ 2 giây
    }
}
