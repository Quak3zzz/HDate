package com.q.hdate.Cards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.q.hdate.R
import com.q.hdate.User

class CardAdapter(context: Context, resourceId: Int, private val items: List<User>) : ArrayAdapter<User>(context, resourceId, items) {

    // Lớp ViewHolder để lưu trữ các view trong mỗi item
    private class ViewHolder {
        lateinit var imageView: ImageView
        lateinit var nameTextView: TextView
        lateinit var bioTextView: TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder

        // Tái sử dụng convertView nếu có thể, hoặc tạo mới
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
            viewHolder = ViewHolder()
            viewHolder.imageView = view.findViewById(R.id.ivProfileImage)
            viewHolder.nameTextView = view.findViewById(R.id.tvName)
            viewHolder.bioTextView = view.findViewById(R.id.tvBio)
            view.tag = viewHolder // Gán ViewHolder vào view
        } else {
            viewHolder = view.tag as ViewHolder // Lấy ViewHolder từ view đã được tái sử dụng
        }

        // Lấy thông tin người dùng tại vị trí hiện tại
        val user = items[position]

        // Sử dụng Glide để tải ảnh
        Glide.with(context)
            .load(user.imageUrl)
            .placeholder(R.drawable.placeholder_image) // Ảnh placeholder trong khi tải
            .error(R.drawable.error_image) // Ảnh lỗi nếu tải thất bại
            .into(viewHolder.imageView)

        // Hiển thị tên và tiểu sử
        viewHolder.nameTextView.text = user.name
        viewHolder.bioTextView.text = user.bio

        return view ?: View(context)
    }
}
