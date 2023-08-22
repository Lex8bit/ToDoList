package com.example.todolist.data

import android.content.Context
import androidx.room.Room
import com.example.todolist.ItemsViewModel
import com.example.todolist.RoomRepository
import com.example.todolist.room.AppDatabase
import com.example.todolist.room.ItemsToDoDao
import javax.inject.Inject

/**
 * Manager that handles logic with ROOM database
 */
class RoomRepositoryImpl @Inject constructor(private val toDoDao: ItemsToDoDao) : RoomRepository {

       override fun getAllItems(): List<ItemsViewModel> {
              return toDoDao.getAllItems()
       }

       override fun insertItem(item: ItemsViewModel) {
              toDoDao.insertItem(item)
       }

       override fun updateItem(item: ItemsViewModel) {
              toDoDao.updateItem(item)
       }

       override fun deleteItem(item: ItemsViewModel) {
              toDoDao.deleteItem(item)
       }

       companion object{
              const val DATABASE_NAME = "database-name"
       }

}