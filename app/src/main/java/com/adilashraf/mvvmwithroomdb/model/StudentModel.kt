package com.adilashraf.mvvmwithroomdb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_details")
data class StudentModel(

    @ColumnInfo(name = "name")
    var name: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rollno")
    var rollNo: Int,
    @ColumnInfo(name = "age")
    var age: Int,
)