package com.example.application_bukuku

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    bookId: Int,
    existingBook: Book?,
    onNavigateUp: () -> Unit,
    onSaveBook: (judul: String, penulis: String, kategori: String) -> Unit
) {
    val isEditMode = bookId != -1

    var judul by remember { mutableStateOf("") }
    var penulis by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var isJudulError by remember { mutableStateOf(false) }
    var isInitialized by remember { mutableStateOf(false) }

    // Mengisi form otomatis saat dalam mode Ubah (Edit)
    LaunchedEffect(existingBook) {
        if (isEditMode && existingBook != null && !isInitialized) {
            judul = existingBook.judul
            penulis = existingBook.penulis
            kategori = existingBook.kategori
            isInitialized = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Ubah Buku" else "Tambah Buku Baru") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Field 1: Judul Buku (Wajib diisi)
            OutlinedTextField(
                value = judul,
                onValueChange = {
                    judul = it
                    if (it.isNotBlank()) isJudulError = false
                },
                label = { Text("Judul Buku *") },
                placeholder = { Text("Contoh: Pemrograman Kotlin") },
                isError = isJudulError,
                supportingText = {
                    if (isJudulError) {
                        Text(
                            text = "Judul buku tidak boleh kosong!",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Field 2: Penulis
            OutlinedTextField(
                value = penulis,
                onValueChange = { penulis = it },
                label = { Text("Penulis") },
                placeholder = { Text("Contoh: Andi") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Field 3: Kategori
            OutlinedTextField(
                value = kategori,
                onValueChange = { kategori = it },
                label = { Text("Kategori") },
                placeholder = { Text("Contoh: Teknologi, Novel, Biografi") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (judul.isBlank()) {
                        isJudulError = true
                    } else {
                        onSaveBook(judul, penulis, kategori)
                        onNavigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditMode) "Simpan Perubahan" else "Simpan Buku")
            }
        }
    }
}