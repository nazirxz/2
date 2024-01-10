package com.example.justicia.model

data class User(val id: Int = -1, val username: String, val email: String, val password: String)

/*properti id dengan tipe data Int yang diinisialisasi dengan nilai default -1. Nilai ini akan digunakan jika tidak ada nilai id yang diberikan saat membuat objek User.*/