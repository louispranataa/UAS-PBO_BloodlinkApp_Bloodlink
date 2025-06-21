# UAS PBO - Bloodlink

## Deskripsi Aplikasi
[Contoh: Bloodlink adalah aplikasi desktop berbasis JavaFX yang bertujuan untuk menjembatani antara pendonor darah dengan rumah sakit yang membutuhkan. Aplikasi ini memungkinkan manajemen jadwal donor dan pemantauan stok darah secara real-time.]

---

## Fitur Utama
Berikut adalah fitur-fitur utama yang ada di dalam aplikasi Bloodlink:
* **Registrasi & Login:** Pengguna dapat mendaftar dan masuk sebagai Donor atau Rumah Sakit.
* **Dashboard Donor:** Donor dapat melihat jadwal donasi, membuat jadwal baru, dan melihat riwayat donasi.
* **Dashboard Rumah Sakit:** Rumah sakit dapat melihat jadwal donor yang masuk, mengelola stok darah, dan melihat data pendonor.
* **Manajemen Jadwal:** Donor dapat mengajukan jadwal dan rumah sakit dapat melihatnya.
* **Manajemen Stok Darah:** Fitur khusus untuk rumah sakit dalam mengelola ketersediaan kantong darah.

---

## Cara Menjalankan Aplikasi

### 1. Kebutuhan (Dependencies)
* Java Development Kit (JDK) versi 17 atau lebih tinggi.
* Apache Maven.
* Database MySQL atau SQLite (sesuaikan dengan yang Anda gunakan).
* [Tambahkan dependensi lain jika ada, misal: JavaFX SDK].

### 2. Langkah Instalasi & Menjalankan
1.  **Clone Repositori:**
    ```bash
    git clone [https://github.com/NAMA_USER_ANDA/UAS-PBO_Bloodlink_Kelompok_Anda.git](https://github.com/NAMA_USER_ANDA/UAS-PBO_Bloodlink_Kelompok_Anda.git)
    ```
2.  **Setup Database:**
    * Buat database baru di sistem manajemen database Anda.
    * Impor skema tabel menggunakan file `database_schema.sql` yang ada di repositori.
    * (Opsional) Impor data awal menggunakan `seed_data.sql`.
    * Ubah konfigurasi koneksi database di file `src/main/java/com/bloodlink/DbUtil.java`.

3.  **Build & Run Proyek:**
    * Buka proyek ini di IDE (seperti VS Code atau IntelliJ).
    * Pastikan Maven telah mengunduh semua dependensi yang ada di `pom.xml`.
    * Jalankan aplikasi melalui `main class` yang ada di `src/main/java/com/bloodlink/App.java`.

---

## Video Presentasi
Berikut adalah link video presentasi proyek kami:
**[Link Video YouTube Anda di Sini]**

---

## Anggota Kelompok
* [Nama Lengkap] - [NIM] - [Peran, misal: Ketua/Fitur Donor]
* [Nama Lengkap] - [NIM] - [Peran, misal: Fitur Rumah Sakit]
* [Nama Lengkap] - [NIM] - [Peran, misal: Database & Backend]
* [Nama Lengkap] - [NIM] - [Peran, misal: UI/UX & FXML]
* [Nama Lengkap] - [NIM] - [Peran, misal: Dokumentasi & Testing]