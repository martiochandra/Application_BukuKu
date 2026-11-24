# BukuKu — Aplikasi Pencatatan Buku Bacaan Pribadi (UTS Pemrograman Bergerak)

## Identitas Mahasiswa
* **Nama:** Martio Chandra
* **NIM:** 0102524723
* **Kelompok Topik:** Topik A — Buku Bacaan (Digit NIM terakhir: 3)
* **Mata Kuliah:** Pemrograman Bergerak (3 SKS)
* **Dosen Pengampu:** Dody

## Deskripsi Aplikasi
**BukuKu** adalah aplikasi Android berbasis Kotlin dan Jetpack Compose untuk mencatat daftar buku bacaan pribadi beserta status penyelesaian bacanya (`Belum dibaca` / `Sudah dibaca`). Aplikasi menerapkan arsitektur berlapis (*Compose UI → ViewModel + StateFlow → Repository → Room Database & Preferences DataStore*) sehingga daftar buku dan preferensi tampilan tetap tersimpan secara lokal meskipun aplikasi ditutup.

## Perangkat & Libraries
* **Bahasa & UI:** Kotlin, Jetpack Compose (Material 3), Navigation Compose
* **Arsitektur & State:** ViewModel, StateFlow, Lifecycle-aware State Collection
* **Local Persistence:** Room Database (Entity, DAO, Database, Repository) & Preferences DataStore
* **IDE & Target:** Android Studio, Min SDK 24, Compile SDK 37

## Daftar Fitur
1. **Tiga Layar Utama (Navigation Compose):** Layar Daftar (`HomeScreen`), Layar Detail (`DetailScreen`), dan Layar Form Tambah/Ubah (`FormScreen`).
2. **CRUD Lengkap & Status Baca:** Menambah buku baru, menampilkan daftar buku, melihat detail, mengubah informasi buku, menghapus buku dengan dialog konfirmasi, serta mengganti status bacaan (`Belum dibaca` / `Sudah dibaca`).
3. **Validasi Input:** Mencegah penyimpanan apabila kolom *Judul Buku* kosong dan menampilkan pesan kesalahan yang jelas.
4. **Persistensi Data Lokal (Room):** Seluruh record buku tersimpan dalam database SQLite lokal dan bertahan setelah aplikasi ditutup maupun dibuka ulang.
5. **Preferensi Tampilan (DataStore):** Pengaturan *Mode tampilan ringkas* (ON/OFF) yang mengatur kepadatan daftar buku dan tetap aktif setelah restart aplikasi.

## Cara Menjalankan Aplikasi
1. Clone repositori Git ini atau ekstrak file ZIP source code.
2. Buka proyek menggunakan **Android Studio**.
3. Tunggu proses **Gradle Sync** selesai hingga seluruh dependensi terpasang.
4. Jalankan proyek pada Emulator Android atau perangkat fisik (minimal Android 7.0 / API 24) dengan menekan tombol **Run**.

## Tautan Video Penjelasan & Demonstrasi
* **Link Video (Google Drive):** [Tempelkan tautan file video MP4 Anda setelah diunggah ke Google Drive]

## Deklarasi Penggunaan AI
* **Menggunakan AI:** Ya
* **Alat yang Digunakan:** Gemini AI
* **Tujuan & Bagian Kode yang Dibantu:** Digunakan sebagai asisten belajar dan *debugging* untuk konfigurasi *plugin* `kotlin.kapt` dan dependensi ikon Material pada Gradle, penyusunan struktur *Data Layer* (`BookDao`, `BookDatabase`, `BookRepository`, `UserPreferencesRepository`), penggabungan aliran data `Flow` di `BukuViewModel`, serta perancangan layar Jetpack Compose dan navigasi. Seluruh alur bisnis, struktur data, pengujian, dan kode telah dipahami serta diverifikasi secara mandiri.