package com.example.l6

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SecondActivity : AppCompatActivity() {

    private lateinit var itemNameEditText: EditText
    private lateinit var itemImageImageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var cameraButton: Button
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondactivity)

        itemNameEditText = findViewById(R.id.text)
        itemImageImageView = findViewById(R.id.itemImageImageView)
        saveButton = findViewById(R.id.saveButton)
        cameraButton = findViewById(R.id.cameraButton)

        // Example: Setting an image, you can replace this with your actual image selection logic
        itemImageImageView.setImageResource(R.drawable.image2)

        saveButton.setOnClickListener {
            val itemName = itemNameEditText.text.toString()
            val itemImage = if (imageUri!= null) imageUri else R.drawable.image2

            if (itemName.isNotEmpty() && itemImage!= null) {
                val intent = Intent()
                intent.putExtra("ITEM_NAME", itemName)
                if (imageUri!= null) {
                    intent.putExtra("ITEM_IMAGE_URI", imageUri.toString())
                } else {
                    intent.putExtra("ITEM_IMAGE", R.drawable.image2)
                }
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Please enter item name and select an image", Toast.LENGTH_SHORT).show()
            }
        }

        cameraButton.setOnClickListener {
            if (checkAndRequestPermissions()) {
                openCamera()
            }
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val writeStoragePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readStoragePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (cameraPermission!= PackageManager.PERMISSION_GRANTED ||
            writeStoragePermission!= PackageManager.PERMISSION_GRANTED ||
            readStoragePermission!= PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                101
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            openCamera()
        } else {
            Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager)!= null) {
            startActivityForResult(intent, 102)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as? Bitmap
            itemImageImageView.setImageBitmap(bitmap)
            // If you need the Uri, you can save the bitmap to a file and get the Uri
            // For simplicity, we are not doing this here.
        }
    }
}