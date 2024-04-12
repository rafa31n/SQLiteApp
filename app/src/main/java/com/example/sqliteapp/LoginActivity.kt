package com.example.sqliteapp

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sqliteapp.db.HelperDB
import com.example.sqliteapp.model.Productos
import com.example.sqliteapp.model.Usuarios

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var managerUsuarios: Usuarios? = null
    private var btnLogin: Button? = null
    private var txtUsername: EditText? = null
    private var txtPassword: EditText? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.buttonLogin)
        txtUsername = findViewById(R.id.editTextUsername)
        txtPassword = findViewById(R.id.editTextPassword)

        btnLogin!!.setOnClickListener(this)
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase
    }

    @SuppressLint("Range")
    override fun onClick(v: View?) {
        managerUsuarios = Usuarios(this)
        val username: String = txtUsername!!.text.toString().trim()
        val password: String = txtPassword!!.text.toString().trim()
        if (v === btnLogin) {
            val cursor = managerUsuarios!!.loginUsuario(username, password)

            if (cursor != null && cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex(Usuarios.COL_ID))
                val username = cursor.getString(cursor.getColumnIndex(Usuarios.COL_USERNAME))

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(
                    this, "BIENVENIDO: " + username,
                    Toast.LENGTH_LONG
                ).show()

                cursor.close()
            } else {
                Toast.makeText(
                    this, "USUARIO NO ENCONTRADO",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}