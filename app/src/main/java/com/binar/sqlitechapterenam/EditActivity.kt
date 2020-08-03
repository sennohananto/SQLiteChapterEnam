package com.binar.sqlitechapterenam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)


        //Buat object DatabaseHandler
        val dbHandler = DatabaseHandler(this)

        //Menerima Object Student dari yang dilempar pakai Intent daru Adapter
        val student = intent.getParcelableExtra<Student>("student")

        supportActionBar?.title = "Ubah data ${student.nama}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Set Edittext berdasarkan data yang ada
        etNamaStudent.setText(student?.nama)
        etEmailStudent.setText(student?.email)

        btnSave.setOnClickListener {
            //mengubah Nama dan Email yang ada pada Object Student yang diterima oleh intent
            student?.nama = etNamaStudent.text.toString()
            student?.email = etEmailStudent.text.toString()

            student?.let {
                //Melakukan update ke SQLIte

                //updateStudent() mengembalikan nilai jumlah baris yang berhasil diubah,
                if(dbHandler.updateStudent(it)!=0){
                    //Jika return nya bernilai TIDAK 0, berarti berhasil diubah
                    Toast.makeText(this,"Berhasil Diubah",Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this,"Gagal Diubah",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}