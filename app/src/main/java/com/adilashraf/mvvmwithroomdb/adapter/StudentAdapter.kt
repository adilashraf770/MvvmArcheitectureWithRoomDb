package com.adilashraf.mvvmwithroomdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.adilashraf.mvvmwithroomdb.databinding.StudentDetailsLayoutBinding
import com.adilashraf.mvvmwithroomdb.model.StudentModel

class StudentAdapter(private val studentList:  List<StudentModel>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val sv =
            StudentDetailsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(sv)
    }

    override fun getItemCount(): Int = studentList.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val pos = studentList[position]
        holder.bind(pos)
    }

    inner class StudentViewHolder(val binding: StudentDetailsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: StudentModel) {
            binding.apply {
                studentName.text = pos.name
                studentRollNo.text = pos.rollNo.toString()
                studentAge.text = pos.age.toString()
            }
         }

    }
}