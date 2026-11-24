package com.example.application_bukuku
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{bookId}") {
        fun createRoute(bookId: Int) = "detail/$bookId"
    }
    object Form : Screen("form?bookId={bookId}") {
        fun createRoute(bookId: Int = -1) = "form?bookId=$bookId"
    }
}