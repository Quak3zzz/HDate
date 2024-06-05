package com.q.hdate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.q.hdate.databinding.ActivityChoiceBinding

class ChoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Chuyển đến màn hình đăng nhập
        binding.btnLoginChoice.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Chuyển đến màn hình đăng ký
        binding.btnSignupChoice.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}
