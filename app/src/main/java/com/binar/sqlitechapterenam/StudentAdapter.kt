package com.binar.sqlitechapterenam

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.student_item.view.*

class StudentAdapter(val listStudent : ArrayList<Student>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listStudent.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvID.text = listStudent[position].id.toString()
        holder.itemView.tvNama.text = listStudent[position].nama
        holder.itemView.tvEmail.text = listStudent[position].email

        holder.itemView.ivEdit.setOnClickListener {
            val intentKeEditActivity = Intent(it.context,EditActivity::class.java)
            intentKeEditActivity.putExtra("student",listStudent[position])

            it.context.startActivity(intentKeEditActivity)
        }

        holder.itemView.ivDelete.setOnClickListener {
            AlertDialog.Builder(it.context).setPositiveButton("Ya") { p0, p1 ->
                val dbHelper = DatabaseHandler(holder.itemView.context)
                if(dbHelper.deleteStudent(listStudent[position])!=0){
                    Toast.makeText(it.context,"Data ${listStudent[position].nama} berhasil dihapus",Toast.LENGTH_LONG).show()
                    (it.context as MainActivity).fetchData()
                }else{
                    Toast.makeText(it.context,"Data ${listStudent[position].nama} Gagal dihapus",Toast.LENGTH_LONG).show()
                }
            }.setNegativeButton("Tidak"
            ) { p0, p1 ->
                p0.dismiss()
            }
                .setMessage("Apakah Anda Yakin ingin menghapus data ${listStudent[position].nama}").setTitle("Konfirmasi Hapus").create().show()
        }
    }
}