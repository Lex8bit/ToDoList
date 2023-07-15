package com.example.todolist

import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var stubContainer : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("testlog","OnCreate has been started")

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        stubContainer = findViewById(R.id.main_no_items_container)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel("Title: $i","Description: $i",i))
        }
        if (data.isNullOrEmpty()){
            stubContainer.visibility = VISIBLE
            recyclerview.visibility = INVISIBLE
            Log.d("testlog", "List is EMPTY")
        }
        else{
            stubContainer.visibility = INVISIBLE
            recyclerview.visibility = VISIBLE
            Log.d("testlog", "List is NOT EMPTY")
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        Log.d("testlog","OnCreate has been finished")
    }
}
