package com.adilashraf.mvvmwithroomdb.viewModelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adilashraf.mvvmwithroomdb.repository.StudentRepository
import com.adilashraf.mvvmwithroomdb.viewmodel.StudentViewModel

class StudentFactory(private val studentRepository: StudentRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            return StudentViewModel(studentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}