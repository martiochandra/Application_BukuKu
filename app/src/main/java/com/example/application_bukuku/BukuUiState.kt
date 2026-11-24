package com.example.application_bukuku

data class BukuUiState(
    val bookList: List<Book> = emptyList(),
    val isCompactMode: Boolean = false
)