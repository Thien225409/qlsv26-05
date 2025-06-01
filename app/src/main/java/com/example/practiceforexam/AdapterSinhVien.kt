package com.example.practiceforexam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterSinhVien(private val dsSVien: MutableList<DataSinhVien>,private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ViewHolderSinhVien>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderSinhVien {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemsv, parent, false)
        return ViewHolderSinhVien(view,listener)
    }

    override fun onBindViewHolder(
        holder: ViewHolderSinhVien,
        position: Int
    ) {
        holder.hoTenSv.text = dsSVien[position].hoTenSv
        holder.maSoSv.text = dsSVien[position].maSoSv
    }

    override fun getItemCount(): Int = dsSVien.size

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}