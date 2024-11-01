package com.example.l6

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Пример данных с текстом и картинкой
        val items = listOf(
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),
            Item("Item 1", R.drawable.image1),


            // Добавьте больше itens здесь
        )

        val adapter = CustomRecyclerAdapter(items)
        recyclerView.adapter = adapter
    }
}