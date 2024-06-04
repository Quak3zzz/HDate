package com.q.hdate // Thay thế bằng package name của bạn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.q.hdate.Cards.CardAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var flingContainer: SwipeFlingAdapterView
    private lateinit var users: MutableList<User>
    private lateinit var adapter: CardAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flingContainer = findViewById(R.id.frame)
        users = mutableListOf()
        val sampleUsers = listOf(
            User("1", "Alice", "25 tuổi, thích du lịch", "https://static.vecteezy.com/system/resources/previews/004/899/680/non_2x/beautiful-blonde-woman-with-makeup-avatar-for-a-beauty-salon-illustration-in-the-cartoon-style-vector.jpg"),
            User("2", "Bob", "30 tuổi, thích thể thao", "https://static.vecteezy.com/system/resources/previews/024/183/513/original/male-avatar-portrait-of-a-young-brunette-male-illustration-of-male-character-in-modern-color-style-vector.jpg"),
            // ... thêm người dùng mẫu khác
        )
        adapter = CardAdapter(this, R.layout.item, users)
        flingContainer.adapter = adapter

        database = FirebaseDatabase.getInstance().reference

        fetchUsersFromFirebase()
        setupSwipeListener()
    }

    private fun fetchUsersFromFirebase() {
        database.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        users.add(user)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity, "Lỗi khi lấy dữ liệu người dùng", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupSwipeListener() {
        flingContainer.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                users.removeAt(0)
                adapter.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any?) {
                val user = dataObject as User
                saveSwipe(user.id, false)
            }

            override fun onRightCardExit(dataObject: Any?) {
                val user = dataObject as User
                saveSwipe(user.id, true)
                checkMatch(user.id)
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                // Tải thêm dữ liệu nếu cần
            }

            override fun onScroll(scrollProgressPercent: Float) {
                // Xử lý khi cuộn thẻ (nếu cần)
            }
        })
    }

    private fun saveSwipe(userId: String, isLiked: Boolean) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        database.child("swipes/$currentUserId/$userId").setValue(isLiked)
    }

    private fun checkMatch(userId: String) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        database.child("swipes/$userId/$currentUserId").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getValue(Boolean::class.java) == true) {
                    Toast.makeText(this@MainActivity, "Match thành công!", Toast.LENGTH_SHORT).show()
                    // Xử lý logic khi match thành công (ví dụ: chuyển đến màn hình chat)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity, "Lỗi khi kiểm tra match", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

// CardAdapter.kt (đã được cải thiện như câu trả lời trước)
// ...
