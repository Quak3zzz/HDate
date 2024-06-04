package com.q.hdate.Cards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.q.hdate.R

class CardAdapter(context: Context, resourceId: Int, items: List<Int>) : ArrayAdapter<Int>(context, resourceId, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val imageResId = getItem(position) // imageResId bây giờ là Int

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        }

        val imageView = convertView!!.findViewById<ImageView>(R.id.ivProfileImage)
        imageView.setImageResource(imageResId!!) // Sử dụng imageResId để set ảnh

        return convertView
    }
}
