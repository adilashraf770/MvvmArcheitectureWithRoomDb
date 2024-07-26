package com.adilashraf.mvvmwithroomdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adilashraf.mvvmwithroomdb.model.StudentModel
import com.adilashraf.mvvmwithroomdb.repository.StudentRepository
import kotlinx.coroutines.launch

class StudentViewModel(private val studentRep: StudentRepository) : ViewModel() {


    fun deleteAllData(){
        viewModelScope.launch {
            studentRep.deleteALlData()
        }
    }


    fun insertData(student: StudentModel) {
        viewModelScope.launch {
            studentRep.insertData(student)
        }
    }

    fun deleteData(student: StudentModel) {
        viewModelScope.launch {
            studentRep.deleteData(student)
        }
    }

    fun updateData(student: StudentModel) {
        viewModelScope.launch {
            studentRep.updateData(student)
        }
    }


}