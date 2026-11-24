package com.example.application_bukuku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class BukuViewModel(
    private val bookRepository: BookRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BukuUiState())
    val uiState: StateFlow<BukuUiState> = _uiState.asStateFlow()

    init {
        // Menggabungkan Flow daftar buku dari Room dan preferensi mode ringkas dari DataStore
        viewModelScope.launch {
            combine(
                bookRepository.getAllBooksStream(),
                userPreferencesRepository.isCompactMode
            ) { books, isCompact ->
                BukuUiState(
                    bookList = books,
                    isCompactMode = isCompact
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    // Mengambil detail satu buku secara reaktif berdasarkan ID
    fun getBookById(id: Int): Flow<Book?> = bookRepository.getBookStream(id)

    // CREATE: Menambah buku baru (default status: Belum dibaca)
    fun addBook(judul: String, penulis: String, kategori: String) {
        viewModelScope.launch {
            val newBook = Book(
                judul = judul.trim(),
                penulis = penulis.trim(),
                kategori = kategori.trim(),
                sudahDibaca = false
            )
            bookRepository.insertBook(newBook)
        }
    }

    // UPDATE: Mengubah data buku yang sudah ada
    fun updateBook(book: Book) {
        viewModelScope.launch {
            bookRepository.updateBook(book)
        }
    }

    // UPDATE STATUS: Mengubah status Sudah dibaca / Belum dibaca
    fun toggleReadStatus(book: Book) {
        viewModelScope.launch {
            bookRepository.updateBook(book.copy(sudahDibaca = !book.sudahDibaca))
        }
    }

    // DELETE: Menghapus buku dari database
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            bookRepository.deleteBook(book)
        }
    }

    // DATASTORE: Menyimpan pengaturan Mode tampilan ringkas (ON/OFF)
    fun toggleCompactMode(isCompact: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveCompactMode(isCompact)
        }
    }

    // Factory untuk menyuntikkan kedua Repository ke dalam BukuViewModel
    class Factory(
        private val bookRepository: BookRepository,
        private val userPreferencesRepository: UserPreferencesRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BukuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BukuViewModel(bookRepository, userPreferencesRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}