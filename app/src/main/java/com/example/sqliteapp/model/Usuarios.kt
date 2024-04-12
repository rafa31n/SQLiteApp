package com.example.sqliteapp.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.sqliteapp.db.HelperDB

class Usuarios(context: Context?) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA PRODUCTOS
        val TABLE_NAME_USUARIOS = "usuarios"

        //nombre de los campos de la tabla contactos
        val COL_ID = "idusuario"
        val COL_USERNAME = "username"
        val COL_PASSWORD = "password"

        //sentencia SQL para crear la tabla.
        val CREATE_TABLE_USUARIOS = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USUARIOS + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_USERNAME + " varchar(255) NOT NULL,"
                        + COL_PASSWORD + " varchar(255) NOT NULL);"
                )
    }

    // ContentValues
    fun generarContentValues(
        username: String?,
        password: String?,
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(COL_USERNAME, username)
        valores.put(COL_PASSWORD, password)
        return valores
    }

    fun addNewUsuario(
        username: String?,
        password: String?
    ) {
        db!!.insert(
            TABLE_NAME_USUARIOS,
            null,
            generarContentValues(username, password)
        )
    }

    fun loginUsuario(username: String, password: String): Cursor? {
        val columns = arrayOf(
            COL_ID,
            COL_USERNAME,
            COL_PASSWORD,
        )
        return db!!.query(
            TABLE_NAME_USUARIOS,
            columns,
            "${COL_USERNAME}=? AND ${COL_PASSWORD}=?",
            arrayOf(username.toString(), password.toString()),
            null,
            null,
            null
        )
    }
}