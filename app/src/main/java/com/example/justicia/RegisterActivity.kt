package com.example.justicia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.justicia.model.User
import com.example.justicia.util.DatabaseHelper

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private val activity = this@RegisterActivity
    private lateinit var buttonRegister: Button
    private lateinit var textViewLinkLogin: TextView
    private lateinit var usernameTextView: EditText
    private lateinit var emailTextView: EditText
    private lateinit var passwordTextView: EditText
/*variabel variabel yang ada*/
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_sign_up)
        // initializing the views
        initViews()
        // initializing the listeners
        initListeners()
        // initializing the objects
        initObjects()

    }
    /**
     * This method is to initialize views
     */
    private fun initViews() {
        usernameTextView = findViewById(R.id.username) as EditText
        emailTextView = findViewById(R.id.emailText) as EditText
        passwordTextView = findViewById(R.id.password) as EditText
        buttonRegister = findViewById(R.id.btnRegister) as Button
        textViewLinkLogin = findViewById(R.id.linkLogin) as TextView
    }
    /**
     * This method is to initialize listeners
     */
    private fun initListeners() {
        buttonRegister!!.setOnClickListener(this)
        textViewLinkLogin!!.setOnClickListener(this)
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
        when (v.id) {
            R.id.btnRegister -> postDataToSQLite()
            R.id.linkLogin -> {
                val intentLogin = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intentLogin)
            }
        }
    }
    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private fun postDataToSQLite() {
        if (!databaseHelper!!.checkUser(usernameTextView!!.text.toString().trim())) {
            var user = User(username = usernameTextView!!.text.toString().trim(),
                email = emailTextView!!.text.toString().trim(),
                password = passwordTextView!!.text.toString().trim())
            databaseHelper!!.addUser(user)
            // To show success message that record saved successfully
            Toast.makeText(this@RegisterActivity, "User added.", Toast.LENGTH_SHORT).show()
            emptyInputEditText()
            val loginIntent = Intent(activity, LoginActivity::class.java)
            startActivity(loginIntent)
        } else {
            // To show error message that record already exists
            Toast.makeText(this@RegisterActivity, "Failed operation.", Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        usernameTextView!!.text = null
        emailTextView!!.text = null
        passwordTextView!!.text = null
    }

}
/*bagian dari aplikasi pendaftaran pengguna di Android yang menggunakan database untuk menyimpan informasi pengguna. Ketika pengguna mengklik tombol pendaftaran, aplikasi akan menyimpan informasi pengguna ke dalam database*/