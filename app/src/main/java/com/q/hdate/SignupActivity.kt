package com.q.hdate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val buttonSignup = findViewById<Button>(R.id.buttonSignup)

        buttonSignup.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val name = editTextName.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Đăng ký thành công, lưu thông tin người dùng vào Realtime Database
                            val user = User(auth.currentUser!!.uid, name, "", 0)
                            database.reference.child("users").child(user.uid).setValue(user)

                            // Chuyển đến MainActivity
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish() // Đóng SignupActivity
                        } else {
                            // Đăng ký thất bại
                            Toast.makeText(
                                this,
                                "Đăng ký thất bại: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
