package com.example.justicia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class PeraturanActivity : AppCompatActivity() {
    private val activity = this@PeraturanActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_listperaturan)
        /*menentukan layout dari file xml "Fragment_Peraturan*/

        val imgPeraturan1 = findViewById<TextView>(R.id.cardPeraturan1) as TextView
        /*saat teks peraturan diklik akan membuka URL dibawah ini*/
        imgPeraturan1.setOnClickListener{
            val url = "https://www.regulasip.id/book/10659/read"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
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
/*ketika teks peraturan 1 diklik dalam aktivitas ini, aplikasi akan membuka browser atau aplikasi lain untuk menampilkan konten pada URL yang diberikan. Ini berguna ketika aplikasi ingin membuka halaman web atau tautan eksternal.*/