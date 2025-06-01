package com.example.practiceforexam


import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceforexam.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), AdapterSinhVien.OnItemClickListener,
    View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AdapterSinhVien
    private lateinit var recyclerViewListSv: RecyclerView
    private lateinit var dsSv: MutableList<DataSinhVien>
    private lateinit var sqlManager: SQLiteManager
    private lateinit var name: EditText
    private lateinit var id: EditText
    private lateinit var dateBirth: EditText
    private lateinit var eMail: EditText
    private lateinit var address: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqlManager = SQLiteManager(this)

        recyclerViewListSv = binding.recyclerView
        init()
        initOnClickListener()
        initAdater()
    }

    private fun init() {
        name = binding.editTextTenSv
        id = binding.editTextMaSo
        dateBirth = binding.editTextDateBirth
        eMail = binding.editTextEmail
        address = binding.editTextAddress
    }

    private fun initOnClickListener() {
        binding.addSV.setOnClickListener(this)
        binding.updateSV.setOnClickListener(this)
        binding.deleteSV.setOnClickListener(this)
    }

    //
    private fun initAdater() {
        getData()
        adapter = AdapterSinhVien(dsSv, this)
        recyclerViewListSv.layoutManager = LinearLayoutManager(this)
        recyclerViewListSv.adapter = adapter
    }

    private fun getData() {
        dsSv = sqlManager.getListSv()
    }

    override fun onItemClick(position: Int) {
        name.setText(dsSv[position].hoTenSv)
        id.setText(dsSv[position].maSoSv)
        dateBirth.setText(dsSv[position].ngaySinh)
        eMail.setText(dsSv[position].eMail)
        address.setText(dsSv[position].address)
    }

    override fun onClick(v: View?) {
        val sinhVien = DataSinhVien(
            name.text.toString(),
            id.text.toString(),
            dateBirth.text.toString(),
            eMail.text.toString(),
            address.text.toString()
        )
        if (sinhVien.maSoSv == "") {
            return
        }
        if (sinhVien.hoTenSv == "") {
            return
        }
        when (v?.id) {
            R.id.addSV -> {
                if(sqlManager.insertSVien(sinhVien)){
                    Toast.makeText(this, "Thêm thành công!!", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Đã tồn tại", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.updateSV -> {
                if (sqlManager.updateSVien(sinhVien)) {
                    Toast.makeText(this, "Cập nhật thành công!!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Không tồn tại sinh viên", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.deleteSV -> {
                if (sqlManager.deleteSVien(sinhVien.maSoSv)) {
                    Toast.makeText(this, "Xóa thành công!!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Không tồn tại sinh viên", Toast.LENGTH_SHORT).show()
                }
            }
        }
        refresh()
        initAdater()
    }

    fun refresh() {
        name.setText("")
        id.setText("")
        dateBirth.setText("")
        eMail.setText("")
        address.setText("")
        name.requestFocus()
    }
}