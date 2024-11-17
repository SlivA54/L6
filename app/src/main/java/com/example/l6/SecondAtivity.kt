package com.example.l6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondactivity)

        // Example of how to set up a button to return data
        val button: Button = findViewById(R.id.saveButton)
        val itemNameInput: TextInputLayout = findViewById(R.id.name)

        button.setOnClickListener {
            val itemName = itemNameInput.editText?.text.toString()
            val intent = Intent()
            intent.putExtra("ITEM_NAME", itemName)
            intent.putExtra("ITEM_TEXT", itemName) // Вы можете использовать одно и то же поле для обоих ключей
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}