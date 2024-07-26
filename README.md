# Android’s Room in Kotlin ft. MVVM Architecture and Coroutines

If you’re looking for an explanation on **Room** implementation on Android using Kotlin and one of it’s Coroutine feature with MVVM architecture, then this one is for you.

Let’s see what I have in store for you.

  1.  What is Room, Kotlin, MVVM, Coroutines?
  2.  Advantages of Room over SQLite?
  3.  Important Annotation in Room.
  4.  Step-by-Step Simple Insert and Read Example
  5.  Conclusion

So let’s get started.

## 1. What is Room, Kotlin, MVVM, Coroutines?

**Answer:** Let's see what are the important concepts in ROOM and MVVM.

**Room**
**Room Database:** Database layer on top of SQLite database that takes care of mundane tasks that you used to handle with an SQLiteOpenHelper. Database holder that serves as an access point to the underlying SQLite database. The Room database uses the DAO to issue queries to the SQLite database.

**Entity:** When working with Architecture Components, this is an annotated class that describes a database table.

**SQLite database:** On the device, data is stored in an SQLite database. For simplicity, additional storage options, such as a web server, are omitted. The Room persistence library creates and maintains this database for you.

**DAO:** Data access object. A mapping of SQL queries to functions. You used to have to define these painstakingly in your SQLiteOpenHelper class. When you use a DAO, you call the methods, and Room takes care of the rest.

**Kotlin:** Kotlin is an open-source, statically-typed programming language that supports both object-oriented and functional programming. Kotlin provides similar syntax and concepts from other languages, including C#, Java, and Scala, among many others. Kotlin does not aim to be unique — instead, it draws inspiration from decades of language development. It exists in variants that target the JVM (Kotlin/JVM), JavaScript (Kotlin/JS), and native code (Kotlin/Native).

**MVVM**
**ViewModel:** Provides data to the UI. Acts as a communication center between the Repository and the UI. Hides where the data originates from the UI. ViewModel instances survive configuration changes.

**LiveData:** A data holder class that can be observed. Always holds/caches latest version of data. Notifies its observers when the data has changed. LiveData is lifecycle aware. UI components just observe relevant data and don’t stop or resume observation. LiveData automatically manages all of this since it’s aware of the relevant lifecycle status changes while observing.

**Repository:** A class that you create, for example using the WordRepository class. You use the Repository for managing multiple data sources.


<img src="https://i.imgur.com/UsNsFfN.png" />


**Coroutines**: Coroutines are a great new feature of Kotlin which allow you to write asynchronous code in a sequential fashion. … However, like RxJava, coroutines have a number of little subtleties that you end up learning for yourself during development time, or tricks that you pick up from others.


## 2. Advantages of Room over SQLite?

- In case of SQLite, There is no compile time verification of raw SQLite queries. But in Room there is SQL validation at compile time.

- As your schema changes, you need to update the affected SQL queries manually. Room solves this problem.

- You need to use lots of boilerplate code to convert between SQL queries and Java data objects. But, Room maps our database objects to Java Object without boilerplate code.

- Room is built to work with LiveData and RxJava for data observation, while SQLite does not.


## 3. Important Annotations in Room.


<img src="https://i.imgur.com/KEXp8s2.png" />


## 4. Implementation Step-by-Step?
As said before, this example uses MVVM with Room using Kotlin and Coroutines. Let's dive into the steps of doing it.

### **Step1:** Add dependencies to your project:

```xml
plugins {
    id ("kotlin-kapt")
}

dependencies {
...
...
    // Room Db
    // - - Room Persistence Library
    val  room_version = "2.6.0"
    implementation ("androidx.room:room-runtime:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:$room_version")

    // - - ViewModel and LiveData
    val  lifecycle_version = "2.6.2"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    // - - Kotlin Coroutines
    val coroutines_version = "1.3.7"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")

...
...
}
```


### **Step2:** Create different folders that relate to MVVM:


<img src="https://i.imgur.com/ug5rEp2.png" />


### **Step3:** Design your MainActivity which should look like this:
 
 ```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/black"
    android:gravity="center"
    android:orientation="vertical"
    android:padding="10dp"
    tools:context=".view.MainActivity">

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@color/black"
            android:orientation="vertical">

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_margin="10dp"
                android:text="@string/student_details"
                android:textAlignment="center"
                android:textColor="@color/white"
                android:textSize="30sp" />


            <EditText
                android:id="@+id/editName"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="10dp"
                android:background="@drawable/edit_bg"
                android:hint="@string/enter_your_name"
                android:inputType="text"
                android:padding="13dp"
                android:textColor="@color/black"
                android:textSize="16sp" />

            <EditText
                android:id="@+id/editRollNo"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="15dp"
                android:background="@drawable/edit_bg"
                android:hint="@string/enter_your_roll_no"
                android:inputType="number"
                android:padding="13dp"
                android:textColor="@color/black"
                android:textSize="16sp" />


            <EditText
                android:id="@+id/editAge"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="15dp"
                android:background="@drawable/edit_bg"
                android:hint="@string/enter_your_age"
                android:inputType="number"
                android:padding="13dp"
                android:textColor="@color/black"
                android:textSize="16sp" />

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp">

                <Button
                    android:id="@+id/btnAdd"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_margin="5dp"
                    android:layout_weight="1"
                    android:background="@drawable/btn_bg"
                    android:shadowColor="#7F7F7F"
                    android:shadowDx="0"
                    android:shadowDy="0"
                    android:shadowRadius="5"
                    android:text="Add"
                    android:textColor="#FFFFFF"
                    android:textSize="19sp" />

                <Button
                    android:id="@+id/btnUpdate"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_margin="5dp"
                    android:layout_weight="1"
                    android:background="@drawable/btn_bg"
                    android:shadowColor="#7F7F7F"
                    android:shadowDx="0"
                    android:shadowDy="0"
                    android:shadowRadius="5"
                    android:text="Update"
                    android:textColor="#FFFFFF"
                    android:textSize="19sp" />
            </LinearLayout>

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp">

                <Button
                    android:id="@+id/btnDelete"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_margin="5dp"
                    android:layout_weight="1"
                    android:background="@drawable/btn_bg"
                    android:shadowColor="#7F7F7F"
                    android:shadowDx="0"
                    android:shadowDy="0"

                    android:shadowRadius="5"
                    android:text="Delete"
                    android:textColor="#FFFFFF"
                    android:textSize="19sp" />

                <Button
                    android:id="@+id/btnDeleteTable"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_margin="5dp"
                    android:layout_weight="1"
                    android:background="@drawable/btn_bg"
                    android:shadowColor="#7F7F7F"
                    android:shadowDx="0"
                    android:shadowDy="0"
                    android:shadowRadius="5"
                    android:text="@string/delete_table"
                    android:textColor="#FFFFFF"
                    android:textSize="19sp" />

            </LinearLayout>

            <Button
                android:id="@+id/btnDeleteDb"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_margin="5dp"
                android:layout_weight="1"
                android:background="@drawable/btn_bg"
                android:shadowColor="#7F7F7F"
                android:shadowDx="0"
                android:shadowDy="0"
                android:shadowRadius="5"
                android:text="@string/delete_database"
                android:textColor="#FFFFFF"
                android:textSize="19sp" />

        </LinearLayout>

    </ScrollView>

</LinearLayout>


```

### **Step4:** Now let's create the Room Database INSTANCE:

**StudentDatabase.kt**

```kotlin
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
```

### **Step5:** Next, let's create a class for creating a Table for Room DB:

```kotlin
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
```

### **Step6:** Next is to create the queries using DAO Interface:

```kotlin
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
```

### **Step7:** Next we create the Repository Class:

```kotlin
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
```

### **Step8:** Next and very important step is to have a ViewModel in the project:

```kotlin
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
```

### **Step9:** Next and very important step is to have a Factory class for ViewModel in the project:

```kotlin
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
```



### **Step10:** Finally, we code the MainActivity kotlin file:

```kotlin
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
 
}
```

For any clarifications please refer to the repository.

## **Conclusion**

Hopefully this guide introduced you to a lesser known yet useful form of Android application data storage called ROOM with Kotlin, Repository and MVVM.

 
