package com.example.todolist

import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var stubContainer : LinearLayout
    private lateinit var fab : FloatingActionButton
    private lateinit var recyclerview : RecyclerView
    private lateinit var adapter : CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("testlog","OnCreate has been started")

        // getting the recyclerview by its id
        recyclerview = findViewById(R.id.recyclerview)
        stubContainer = findViewById(R.id.main_no_items_container)
        fab = findViewById(R.id.main_fab)

        fab.setOnClickListener(){
            val dialog = CustomDialog(this)
            dialog.show()
        }

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()
        if (data.isNullOrEmpty()){
            Log.d("testlog","Container is EMPTY")
            stubContainer.visibility = VISIBLE
            recyclerview.visibility = INVISIBLE
        }

        // This will pass the ArrayList to our Adapter
        adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        Log.d("testlog","OnCreate has been finished")
    }

    fun addItem(item: ItemsViewModel) {
        stubContainer.visibility = INVISIBLE
        recyclerview.visibility = VISIBLE
        adapter.addItem(item)
    }
}
