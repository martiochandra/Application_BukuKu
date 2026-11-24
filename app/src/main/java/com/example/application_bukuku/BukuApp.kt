package com.example.application_bukuku

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun BukuApp() {
    val context = LocalContext.current

    // Inisialisasi Database, Repository, dan DataStore
    val database = remember { BookDatabase.getDatabase(context) }
    val bookRepository = remember { BookRepository(database.bookDao()) }
    val userPrefsRepository = remember { UserPreferencesRepository(context.dataStore) }

    val viewModel: BukuViewModel = viewModel(
        factory = BukuViewModel.Factory(bookRepository, userPrefsRepository)
    )

    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // 1. Layar Daftar Buku
        composable(Screen.Home.route) {
            HomeScreen(
                uiState = uiState,
                onBookClick = { bookId ->
                    navController.navigate(Screen.Detail.createRoute(bookId)) {
                        launchSingleTop = true
                    }
                },
                onAddClick = {
                    navController.navigate(Screen.Form.createRoute()) {
                        launchSingleTop = true
                    }
                },
                onToggleCompactMode = { isCompact ->
                    viewModel.toggleCompactMode(isCompact)
                }
            )
        }

        // 2. Layar Detail Buku
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: -1
            val selectedBook by viewModel.getBookById(bookId)
                .collectAsStateWithLifecycle(initialValue = null)

            DetailScreen(
                book = selectedBook,
                onNavigateUp = { navController.popBackStack() },
                onToggleReadStatus = { book -> viewModel.toggleReadStatus(book) },
                onEditClick = { id ->
                    navController.navigate(Screen.Form.createRoute(id)) {
                        launchSingleTop = true
                    }
                },
                onDeleteBook = { book -> viewModel.deleteBook(book) }
            )
        }

        // 3. Layar Form Tambah / Ubah Buku
        composable(
            route = Screen.Form.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: -1
            val existingBook by viewModel.getBookById(bookId)
                .collectAsStateWithLifecycle(initialValue = null)

            FormScreen(
                bookId = bookId,
                existingBook = existingBook,
                onNavigateUp = { navController.popBackStack() },
                onSaveBook = { judul, penulis, kategori ->
                    if (bookId == -1) {
                        viewModel.addBook(judul, penulis, kategori)
                    } else if (existingBook != null) {
                        viewModel.updateBook(
                            existingBook!!.copy(
                                judul = judul.trim(),
                                penulis = penulis.trim(),
                                kategori = kategori.trim()
                            )
                        )
                    }
                }
            )
        }
    }
}