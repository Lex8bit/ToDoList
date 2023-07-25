package com.example.todolist

//import android.arch.lifecycle.LiveData
//import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todolist.room.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var stubContainer : LinearLayout
    private lateinit var fab : FloatingActionButton
    private lateinit var recyclerview : RecyclerView
    private lateinit var adapter : CustomAdapter
    private lateinit var db : AppDatabase

    /** Шаг 3. Создаем LiveData для обработки данных*/
    private lateinit var todoLiveData: LiveData<List<ItemsViewModel>>
    private lateinit var data: List<ItemsViewModel>

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
            val dialog = CustomDialog(this, true, null)
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
            data = it
            /** Шаг 4. Отображаем получаенные данные в списке*/
            //This will pass the ArraiList to our adapter
            adapter.updateList(it)
            screenDataValidation(it)
            Log.d("testlog","room check $it")
        })


        /**Удаление по свайпу*/
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val deletedCourse: ItemsViewModel =
                    data.get(viewHolder.adapterPosition)

                // below line is to get the position
                // of the item at that position.
                val position = viewHolder.adapterPosition

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                data.toMutableList().removeAt(viewHolder.adapterPosition)

                // below line is to notify our item is removed from adapter.
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                deleteItem(deletedCourse)   /**ТУТ УДАЛЯЕМ ЯЧЕЙКУ*/
                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                Snackbar.make(recyclerview, "Deleted " + deletedCourse.title, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            db.todoDao().insertItem(deletedCourse)
                            //data.toMutableList().add(position, deletedCourse) - строчка которая была до этого. не работала, тк мы добавляли в лист
                            // below line is to notify item is
                            // added to our adapter class.
                            adapter.notifyItemInserted(position)
                        }).show()
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerview)
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
    fun  updateItem(item: ItemsViewModel) {
        /** Обновление данных в БД*/
        db.todoDao().updateItem(item)
    }
    fun  deleteItem(item: ItemsViewModel) {
        /** Обновление данных в БД*/
        db.todoDao().deleteItem(item)
    }

    override fun itemClicked(item: ItemsViewModel) {
        Log.d("testlog", "открыли ячейку для редактирования $item")
        val dialog = CustomDialog(this, false, item)
        dialog.show()
    }
}
