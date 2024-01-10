package com.example.justicia

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {
    private val activity = this@DashboardActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dashboard) /*Menyatakan bahwa tampilan untuk aktivitas ini diambil dari file XML bernama "fragment_dashboard"*/

        val imgPeraturan = findViewById<ImageView>(R.id.imgPeraturan) as ImageView /*Mencari gambar dengan ID "imgPeraturan" dari tampilan dan menyimpannya dalam variabel "imgPeraturan".*/
        imgPeraturan.setOnClickListener{ /*Menetapkan reaksi saat gambar "imgPeraturan" diklik. Saat diklik, aplikasi akan beralih ke layar atau aktivitas lain yang disebut "PeraturanActivity".*/
            val listPeraturan = Intent(activity, PeraturanActivity::class.java)
            startActivity(listPeraturan)
        }

        val imgKonsultasi = findViewById<ImageView>(R.id.imgKonsultasi) as ImageView
        imgKonsultasi.setOnClickListener{
            val konsultasi = Intent(activity, ChatbotActivity::class.java)
            startActivity(konsultasi)
        }

        val back= findViewById<ImageView>(R.id.btnBack) as ImageView
        back.setOnClickListener {
            finish()
        }
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val dashboard = Intent(activity, DashboardActivity::class.java)
                    startActivity(dashboard)
                    true
                }
                R.id.bottom_grafik -> {
                    val grafik = Intent(activity, GrafikActivity::class.java)
                    startActivity(grafik)
                    true
                }
                else -> false
            }
        }
    }

}
/*Menetapkan reaksi saat gambar "imgPeraturan" diklik. Saat diklik, aplikasi akan beralih ke layar atau aktivitas lain yang disebut "PeraturanActivity".*/