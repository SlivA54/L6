package com.example.l6

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class SecondActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var itemNameInput: TextInputLayout
    private lateinit var itemTextDisplay: TextView
    private lateinit var imageView: ImageView
    private lateinit var cameraButton: Button
    private val REQUEST_IMAGE_CAPTURE = 1
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondactivity)

        db = AppDatabase.getDatabase(this)
        itemNameInput = findViewById(R.id.name)
        imageView = findViewById(R.id.imageView)
        cameraButton = findViewById(R.id.cameraButton)

        val itemId = intent.getStringExtra("ITEM_ID")
        val itemName = intent.getStringExtra("ITEM_NAME")

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
        } else if (itemName!= null) {
            itemNameInput.editText?.setText(itemName)
        }

        cameraButton.setOnClickListener {
            dispatchTakePictureIntent()
        }

        // Example of how to set up a button to return data
        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
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

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    imageUri = FileProvider.getUriForFile(
                        this,
                        "com.example.l6.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${System.currentTimeMillis()}_",
            ".jpg",
            storageDir
        ).apply {
            imageUri = Uri.fromFile(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (imageUri!= null) {
                imageView.setImageURI(imageUri)
            } else if (data!= null && data.extras!= null) {
                val thumbnail = data.extras?.get("data") as Bitmap
                imageView.setImageBitmap(thumbnail)
            }
        }
    }
}