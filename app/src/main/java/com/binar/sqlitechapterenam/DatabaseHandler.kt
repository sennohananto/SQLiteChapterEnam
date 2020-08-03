package com.binar.sqlitechapterenam

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler (context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION){
    companion object{
        val DB_NAME = "student_db"
        val DB_VERSION = 1
        val TABLE_NAME = "StudentTable"
        val COLUMN_ID = "id"
        val COLUMN_NAME = "name"
        val COLUMN_EMAIL = "email"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_STUDENTS_TABLE = ("CREATE TABLE $TABLE_NAME(" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_EMAIL TEXT)")
        p0?.execSQL(CREATE_STUDENTS_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(p0)
    }

    fun addStudent(student: Student): Long {
        //mendapatkan object database
        val db = this.writableDatabase

        //Data yang akan dimasukkan ke DB, dibungkus dulu pake ContentValues
        val contentValues = ContentValues()
//        contentValues.put(COLUMN_ID,student.id)
        contentValues.put(COLUMN_NAME,student.nama)
        contentValues.put(COLUMN_EMAIL,student.email)

        //Melakukan insert ke DB
        val isSuccess = db.insert(TABLE_NAME,null,contentValues)

        //Menutup DB
        db.close()

        return isSuccess
    }

    fun viewAllStudents(): ArrayList<Student>{
        //List penampung daftar Student
        val studentList = ArrayList<Student>()

        //Query Select
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val db = this.readableDatabase

        //Membuat Cursor --> Penunjuk record/Baris di sebuah tabel
        var cursor: Cursor? = null

        //mengisi cursor dengan data dari Tabel Student
        cursor = db.rawQuery(selectQuery, null, null)

        if(cursor.moveToFirst()){
            do{
                val idStudent = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val namaStudent = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val emailStudent = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
                //buat object student
                val objectStudent = Student(idStudent,namaStudent,emailStudent)
                //menambahkan object student ke List
                studentList.add(objectStudent)
            }while (cursor.moveToNext()) //Dilakukan sampai kursor di baris terakhir table
        }
        return studentList
    }

    fun updateStudent(student: Student): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, student.id)
        contentValues.put(COLUMN_NAME, student.nama) // Nama Student
        contentValues.put(COLUMN_EMAIL, student.email) // Email Student

        val isSuccess = db.update(
            TABLE_NAME,
            contentValues,
            "$COLUMN_ID = ${student.id}" ,
            null)

        db.close()
        return isSuccess
    }

    fun deleteStudent(student: Student): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, student.id) // Id Student
        // Menghapus Baris
        val success = db.delete(TABLE_NAME, "$COLUMN_ID = ${student.id}", null)

        //Argumen kedua adalah String yang berisi nullColumnHack
        db.close() // Menutup koneksi ke database
        return success
    }

}