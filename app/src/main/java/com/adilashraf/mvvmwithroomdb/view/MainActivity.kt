package com.adilashraf.mvvmwithroomdb.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adilashraf.mvvmwithroomdb.databinding.ActivityMainBinding
import com.adilashraf.mvvmwithroomdb.model.StudentModel
import com.adilashraf.mvvmwithroomdb.repository.StudentRepository
import com.adilashraf.mvvmwithroomdb.room.StudentDatabase
import com.adilashraf.mvvmwithroomdb.viewModelfactory.StudentFactory
import com.adilashraf.mvvmwithroomdb.viewmodel.StudentViewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: StudentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val repository =
            StudentRepository(StudentDatabase.getDatabaseInstance(applicationContext).studentDao())
        val factory = StudentFactory(repository)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]


        binding.apply {
            btnAdd.setOnClickListener {
                insert()
            }
            btnUpdate.setOnClickListener {
                update()
            }

            btnDelete.setOnClickListener {
                delete()
            }

            btnDeleteTable.setOnClickListener {
                viewModel.deleteAllData()
            }

            btnDeleteDb.setOnClickListener {
                Thread {
                    StudentDatabase.getDatabaseInstance(this@MainActivity).clearAllTables()
                }.start()

            }
        }

    }


    private fun insert() {
        val name = binding.editName.text.toString().trim()
        val age = binding.editAge.text.toString().trim()
        val rollNo = binding.editRollNo.text.toString().trim()

        if (name.isNotBlank() || age.isNotBlank() || rollNo.isNotBlank()) {
            val student =
                StudentModel(
                    name = name,
                    rollNo = rollNo.toInt(),
                    age = age.toInt()
                )
            viewModel.insertData(student)
            Toast.makeText(this, "Student is Added", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "fill all details", Toast.LENGTH_SHORT).show()
        }
        clearEditText()

    }

    private fun update() {
        val name = binding.editName.text.toString().trim()
        val age = binding.editAge.text.toString().trim()
        val rollNo = binding.editRollNo.text.toString().trim()

        if (name.isNotBlank() || age.isNotBlank() || rollNo.isNotBlank()) {
            val student =
                StudentModel(
                    name = name,
                    rollNo = rollNo.toInt(),
                    age = age.toInt()
                )
            viewModel.updateData(student)
            Toast.makeText(this, "Student is Updated", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "fill all details", Toast.LENGTH_SHORT).show()
        }
        clearEditText()
    }


    private fun delete() {
        val name = binding.editName.text.toString().trim()
        val age = binding.editAge.text.toString().trim()
        val rollNo = binding.editRollNo.text.toString().trim()

        if (name.isNotBlank() || age.isNotBlank() || rollNo.isNotBlank()) {
            val student =
                StudentModel(
                    name = name,
                    rollNo = rollNo.toInt(),
                    age = age.toInt()
                )
            viewModel.deleteData(student)
            Toast.makeText(this, "Student is deleted", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "fill all details", Toast.LENGTH_SHORT).show()
        }

        clearEditText()

    }

    private fun clearEditText() {
        binding.editName.text.clear()
        binding.editAge.text.clear()
        binding.editRollNo.text.clear()
    }


//    private fun setAdapter(studentList: List<StudentModel>) {
//        val adapter = StudentAdapter(studentList)
//        binding.recyclerView.adapter = adapter
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//    }

}