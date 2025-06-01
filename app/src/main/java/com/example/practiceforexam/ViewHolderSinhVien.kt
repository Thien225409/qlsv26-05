package com.example.practiceforexam

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceforexam.AdapterSinhVien.OnItemClickListener

class ViewHolderSinhVien(itemView: View,private val listener:OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
    val hoTenSv: TextView = itemView.findViewById(R.id.hoTenSv)
    val maSoSv: TextView = itemView.findViewById(R.id.msSv)

    init {
        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
}