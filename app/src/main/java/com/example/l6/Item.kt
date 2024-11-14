package com.example.l6

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val imageResource: Int
)