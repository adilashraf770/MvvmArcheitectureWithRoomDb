package com.adilashraf.mvvmwithroomdb.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adilashraf.mvvmwithroomdb.dao.StudentDao
import com.adilashraf.mvvmwithroomdb.model.StudentModel

@Database(entities = [StudentModel::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {

        @Volatile
        private var INSTANCE: StudentDatabase? = null
        const val DATABASE_NAME: String = "student_db"

        fun getDatabaseInstance(context: Context): StudentDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}