package com.adilashraf.mvvmwithroomdb.repository

import androidx.lifecycle.LiveData
import com.adilashraf.mvvmwithroomdb.dao.StudentDao
import com.adilashraf.mvvmwithroomdb.model.StudentModel

class StudentRepository(private val studentDao: StudentDao) {

    suspend fun insertData(student: StudentModel){
        studentDao.insertData(student)
    }

    suspend fun deleteData(student: StudentModel){
        studentDao.deleteData(student)
    }

    suspend fun updateData(student: StudentModel){
        studentDao.updateData(student)
    }

    suspend fun deleteALlData() {
        studentDao.deleteAllData()
    }
}