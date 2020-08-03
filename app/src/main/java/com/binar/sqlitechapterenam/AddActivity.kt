package com.binar.sqlitechapterenam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val dbHandler = DatabaseHandler(this)

        btnSave.setOnClickListener {
            val student = Student(null,etNamaStudent.text.toString(),etEmailStudent.text.toString())

            if (dbHandler.addStudent(student)!=0.toLong()){
                Toast.makeText(this,"Sukses menambahkan",Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this,"Gagal menambahkan",Toast.LENGTH_LONG).show()
            }
        }
    }
}