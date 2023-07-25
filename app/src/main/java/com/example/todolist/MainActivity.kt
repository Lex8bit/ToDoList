package com.example.todolist

//import android.arch.lifecycle.LiveData
//import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todolist.room.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var stubContainer : LinearLayout
    private lateinit var fab : FloatingActionButton
    private lateinit var recyclerview : RecyclerView
    private lateinit var adapter : CustomAdapter
    private lateinit var db : AppDatabase

    /** Шаг 3. Создаем LiveData для обработки данных*/
    private lateinit var todoLiveData: LiveData<List<ItemsViewModel>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("testlog","OnCreate has been started")

        // getting the recyclerview by its id
        recyclerview = findViewById(R.id.recyclerview)
        stubContainer = findViewById(R.id.main_no_items_container)
        fab = findViewById(R.id.main_fab)

        fab.setOnClickListener(){
            /**ШАГ 1 Появление диалогового окна для сбора информации*/
            val dialog = CustomDialog(this, true)
            dialog.show()
        }

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        adapter = CustomAdapter(mutableListOf(), this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        Log.d("testlog","OnCreate has been finished")

        //room
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        )
            .allowMainThreadQueries()
            .build()

        todoLiveData = db.todoDao().getAllItems()

        todoLiveData.observe(this, Observer {
            /** Шаг 4. Отображаем получаенные данные в списке*/
            //This will pass the ArraiList to our adapter
            adapter.updateList(it)
            screenDataValidation(it)
            Log.d("testlog","room check $it")
        })
    }

    private fun screenDataValidation(list: List<ItemsViewModel>) {
        if (list.isNullOrEmpty()){
            Log.d("testlog","Container is EMPTY")
            stubContainer.visibility = VISIBLE
            recyclerview.visibility = INVISIBLE
        }else{
            stubContainer.visibility = INVISIBLE
            recyclerview.visibility = VISIBLE
        }
    }

    fun addItem(item: ItemsViewModel) {
        /**Отправка собранные данные в БД*/
        stubContainer.visibility = INVISIBLE
        recyclerview.visibility = VISIBLE
        db.todoDao().insertItem(item)
    }

    override fun itemClicked(item: ItemsViewModel) {
        Log.d("testlog", "открыли ячейку для редактирования $item")
        val dialog = CustomDialog(this, false)
        dialog.show()
    }
}
