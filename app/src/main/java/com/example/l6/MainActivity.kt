package com.example.l6

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomRecyclerAdapter
    private var items = mutableListOf<Item>()
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getDatabase(this)
        loadItems()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Создаем адаптер с слушателями кликов
        adapter = CustomRecyclerAdapter(items,
            { item -> // onItemClick
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("ITEM_ID", item.id)
                intent.putExtra("ITEM_NAME", item.name)
                startActivity(intent)
            },
            { item -> // onDeleteClick
                deleteItem(item)
            }
        )
        recyclerView.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button: ImageButton = findViewById(R.id.imageButton4)

        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val itemName = data?.getStringExtra("ITEM_NAME")
            if (itemName!= null) {
                val item = Item(null, itemName, itemName)
                saveItem(item)
            }
        }
    }

    private fun saveItem(item: Item) {
        lifecycleScope.launch {
            db.itemDao().insertItem(item)
            loadItems()
            adapter.notifyDataSetChanged()
        }
    }

    private fun loadItems() {
        lifecycleScope.launch {
            items.clear()
            items.addAll(db.itemDao().getAllItems())
            adapter.notifyDataSetChanged()
        }
    }

    private fun deleteItem(item: Item) {
        lifecycleScope.launch {
            db.itemDao().deleteItem(item)
            loadItems()
            adapter.notifyDataSetChanged()
        }
    }
}