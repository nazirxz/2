package com.example.justicia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.example.justicia.util.DatabaseHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private val activity = this@LoginActivity
    private lateinit var buttonLogin: Button
    private lateinit var textViewLinkRegister: TextView
    private lateinit var usernameTextView: EditText
    private lateinit var passwordTextView: EditText
    /*variabel variabel nya */

    private lateinit var databaseHelper: DatabaseHelper
    /*databaseHelper = variabel untuk berinteraksi dengan database SQLite.*/
    override fun onCreate(savedInstanceState: Bundle?) {
        /*oncreate = metode yg dipanggil saat aplikasi baru dimulai, didalam ini semua elemen dan objek diatur*/
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)
        // initializing the views
        initViews()
        /*tombol, teks, dan bidang teks.*/
        // initializing the listeners
        initListeners()
        /*menangani klik pada elemen*/
        // initializing the objects
        initObjects()
        /*inisiasi dengan objek yg diperlukan sprti DatabaseHelper untuk berinteraksi dengan database.*/

    }
    /**
     * This method is to initialize views
     */
    private fun initViews() {
        usernameTextView = findViewById(R.id.username) as EditText
        passwordTextView = findViewById(R.id.password) as EditText
        buttonLogin = findViewById(R.id.btnLogin) as Button
        textViewLinkRegister = findViewById(R.id.linkRegister) as TextView
    }
    /**
     * This method is to initialize listeners
     */
    private fun initListeners() {
        buttonLogin!!.setOnClickListener(this)
        textViewLinkRegister!!.setOnClickListener(this)
    }
    /**
     * This method is to initialize objects to be used
     */
    private fun initObjects() {
        databaseHelper = DatabaseHelper(activity)
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    override fun onClick(v: View) {
        /*metode yang menangani klik pada elemen interface*/
        when (v.id) {
            R.id.btnLogin -> verifyFromSQLite()
            R.id.linkRegister -> {
                /*tombol login diklik, maka akan memanggil metode verifyFromSQLite untuk memeriksa kredensial login. Jika tautan register diklik, aplikasi akan pindah ke layar pendaftaran.*/
                // Navigate to RegisterActivity
                val intentRegister = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }
    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private fun verifyFromSQLite() {
        /*metode verifyFromSQLite untuk memeriksa username dan password sesuai dengan database*/

        if (databaseHelper!!.checkUser(usernameTextView!!.text.toString().trim { it <= ' ' }, passwordTextView!!.text.toString().trim { it <= ' ' })) {
            val dashboardIntent = Intent(activity, DashboardActivity::class.java)
            /*jika valid akan diarahkan ke dashboardActivity */
//            Toast.makeText(this@LoginActivity, "Account is valid.", Toast.LENGTH_SHORT).show()
            emptyInputEditText()
            startActivity(dashboardIntent)
        } else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(this@LoginActivity, "Wrong username or password.", Toast.LENGTH_SHORT).show()
            /*jika tidak, akan muncul pesan salah*/

        }
    }
    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        /*metode utk mengosongkan semua bidang input teks setelah proses login selesai*/
        usernameTextView!!.text = null
        passwordTextView!!.text = null
    }

    /*mengontrol bagaimana pengguna dapat login ke dalam aplikasi dan berinteraksi dengan database SQLite untuk memeriksa kevalidan kredensial login.*/

}