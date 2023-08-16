package com.example.todolist.data

import android.content.Context
import androidx.room.Room
import com.example.todolist.ItemsViewModel
import com.example.todolist.RoomManager
import com.example.todolist.room.AppDatabase

/**
 * Manager that handles logic with ROOM database
 */
class RoomManagerImpl(private val context : Context) : RoomManager {
       //room
       private var db = Room.databaseBuilder(
              context,
              AppDatabase::
              class.java, DATABASE_NAME
       )
              .allowMainThreadQueries()
              .build()

       override fun getAllItems() : List<ItemsViewModel>{
            return  db.todoDao().getAllItems()
       }

       override fun insertItem(item: ItemsViewModel) {
              db.todoDao().insertItem(item)
       }

       override fun updateItem(item: ItemsViewModel) {
              db.todoDao().updateItem(item)
       }

       override fun deleteItem(item: ItemsViewModel) {
              db.todoDao().deleteItem(item)
       }

       companion object{
              private const val DATABASE_NAME = "database-name"
       }

}