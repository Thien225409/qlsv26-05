package com.example.practiceforexam

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class SQLiteManager(private val context: Context) {
    private val dbPath = context.filesDir.path + "/mydb"
    private val db: SQLiteDatabase =
        SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY)

    init {
        createTableIfNeeded()
    }

    private fun createTableIfNeeded() {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS SinhVien (
            id TEXT PRIMARY KEY,
            name TEXT,
            datebirth TEXT,
            email TEXT,
            address TEXT
            )
        """.trimIndent()
        )
    }

    fun getListSv(): MutableList<DataSinhVien> {
        val dsSv = mutableListOf<DataSinhVien>()
        val cursor = db.rawQuery("SELECT * FROM SinhVien", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val datebirth = cursor.getString(cursor.getColumnIndexOrThrow("datebirth"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                val address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                dsSv.add(DataSinhVien(name, id, datebirth, email, address))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return dsSv
    }

    fun insertSVien(sv: DataSinhVien): Boolean {
        // Kiểm tra xem sinh viên đã tồn tại chưa
        val cursor = db.rawQuery("SELECT id FROM SinhVien WHERE id = ?", arrayOf(sv.maSoSv))
        val exists = cursor.moveToFirst()
        cursor.close()

        if (exists) return false

        // Thêm sinh viên mới
        val values = ContentValues().apply {
            put("id", sv.maSoSv)
            put("name", sv.hoTenSv)
            put("datebirth", sv.ngaySinh)
            put("email", sv.eMail)
            put("address", sv.address)
        }

        val result = db.insert("SinhVien", null, values)
        return result != -1L
    }

    fun deleteSVien(ms: String): Boolean {
        val rowDeleted = db.delete("SinhVien", "id=?", arrayOf(ms))
        return rowDeleted > 0
    }

    @SuppressLint("Recycle")
    fun updateSVien(sv: DataSinhVien): Boolean {
        val values = ContentValues().apply {
            put("id", sv.maSoSv)
            put("name", sv.hoTenSv)
            put("datebirth", sv.ngaySinh)
            put("email", sv.eMail)
            put("address", sv.address)
        }

        val cursor = db.rawQuery("SELECT id FROM SinhVien WHERE id = ?", arrayOf(sv.maSoSv))
        val exists = cursor.moveToFirst()
        cursor.close()
        if (!exists) {
            return false
        }
        val rowsUpdated = db.update("SinhVien", values, "id = ?", arrayOf(sv.maSoSv))
        return rowsUpdated > 0
    }
}