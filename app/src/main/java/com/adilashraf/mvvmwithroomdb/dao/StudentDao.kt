package com.adilashraf.mvvmwithroomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.adilashraf.mvvmwithroomdb.model.StudentModel

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(student: StudentModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateData(student: StudentModel)

    @Delete
    suspend fun deleteData(student: StudentModel)

    @Query("delete FROM student_details")
    suspend fun deleteAllData()
}