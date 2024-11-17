package com.example.l6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(private val items: List<Item>, private val onClickListener: (Item) -> Unit) : RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemText: TextView = itemView.findViewById(R.id.item_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemText.text = item.text
        // You can also set the image here if needed
        // holder.itemImage.setImageResource(item.imageResource)

        // Добавляем слушатель кликов
        holder.itemView.setOnClickListener {
            onClickListener(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}