package com.example.justicia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val activity = this@MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*menentukan layout aktifitas dari file XML "Activity_Main*/

        val btnLogin = findViewById<Button>(R.id.btnLogin) as Button
        /*mencari tombol login dengan Id btnLogin*/
        btnLogin.setOnClickListener{
            val loginIntent = Intent(activity, LoginActivity::class.java)
            /*tombol login diklik > layar loginActivity*/
            startActivity(loginIntent)
        }

        val linkRegister = findViewById<TextView>(R.id.linkRegister) as TextView
        linkRegister.setOnClickListener{
            val registerIntent = Intent(activity, RegisterActivity::class.java)
            /*saat register diklik akan beralih ke layar pendaftaran "RegisterActivity"*/
            startActivity(registerIntent)
        }
    }
}

/*mengguna membuka aplikasi, mereka disajikan dengan tampilan ini, dan dari sini, mereka dapat melanjutkan ke layar login atau pendaftaran sesuai dengan tindakan yang mereka pilih (klik tombol atau tautan).*/