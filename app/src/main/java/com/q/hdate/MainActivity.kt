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
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var flingContainer: SwipeFlingAdapterView
    private val al = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flingContainer = findViewById(R.id.frame)

        // Thêm các URL ảnh mẫu vào danh sách
        al.add(R.drawable.error_image)
        al.add(R.drawable.placeholder_image)
        al.add(R.drawable.error_image)

        val arrayAdapter = CardAdapter(this, 0, al) // Sử dụng CardAdapter đã sửa
        flingContainer.adapter = arrayAdapter

        // Xử lý sự kiện vuốt (like/dislike)
        flingContainer.setFlingListener(object : SwipeFlingAdapterView.onFlingListener {
            override fun removeFirstObjectInAdapter() {
                al.removeAt(0)
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any?) {
                // Xử lý vuốt trái (dislike)
                Toast.makeText(this@MainActivity, "Left!", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(dataObject: Any?) {
                // Xử lý vuốt phải (like)
                Toast.makeText(this@MainActivity, "Right!", Toast.LENGTH_SHORT).show()
            }
            override fun onScroll(scrollProgressPercent: Float) {
                val card = flingContainer.selectedView // Lấy view của thẻ hiện tại
                card?.alpha = 1 - abs(scrollProgressPercent) // Điều chỉnh độ trong suốt của thẻ
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                // Kiểm tra xem còn bao nhiêu item trong adapter
                if (itemsInAdapter == 0) {
                    Toast.makeText(this@MainActivity, "Hết người dùng rồi!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // Tải thêm dữ liệu hoặc thực hiện các hành động khác khi sắp hết item
                    // Ví dụ: fetchUsersFromFirebase()
                }
            }
            // ... các phương thức khác (onAdapterAboutToEmpty, onScroll)
        })
    }
}
