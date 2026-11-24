package com.example.application_bukuku

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val judul: String,
    val penulis: String,
    val kategori: String,
    val sudahDibaca: Boolean = false // false = Belum dibaca, true = Sudah dibaca
)