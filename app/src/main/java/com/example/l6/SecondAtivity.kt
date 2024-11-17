package com.example.l6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var itemNameInput: TextInputLayout
    private lateinit var itemTextDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondactivity)

        db = AppDatabase.getDatabase(this)
        itemNameInput = findViewById(R.id.name)

        val itemId = intent.getStringExtra("ITEM_ID")
        if (itemId!= null) {
            GlobalScope.launch(Dispatchers.IO) {
                val item = db.itemDao().getItemById(itemId.toInt())
                launch(Dispatchers.Main) {
                    if (item!= null) {
                        itemNameInput.editText?.setText(item.name)
                        itemNameInput.isEnabled = false
                    }
                }
            }
        }

        // Example of how to set up a button to return data
        val button: Button = findViewById(R.id.saveButton)
        button.setOnClickListener {
            if (itemId == null) {
                val itemName = itemNameInput.editText?.text.toString()
                val intent = Intent()
                intent.putExtra("ITEM_NAME", itemName)
                intent.putExtra("ITEM_TEXT", itemName)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                // Если элемент уже выбран, можно добавить кнопку для редактирования
                // itemNameInput.isEnabled = true
                // button.text = "Save Changes"
            }
        }
    }
}