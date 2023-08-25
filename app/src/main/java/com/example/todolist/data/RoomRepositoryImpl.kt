package com.example.todolist.data

import android.content.Context
import androidx.room.Room
import com.example.todolist.ItemsViewModel
import com.example.todolist.RoomRepository
import com.example.todolist.room.AppDatabase
import com.example.todolist.room.ItemsToDoDao
<<<<<<< HEAD
=======
import javax.inject.Inject
>>>>>>> newBrunch

/**
 * Manager that handles logic with ROOM database
 */
<<<<<<< HEAD
class RoomRepositoryImpl(private val toDoDao: ItemsToDoDao) : RoomRepository {
       //room
//       private var db = Room.databaseBuilder(
//              context,
//              AppDatabase::
//              class.java, DATABASE_NAME
//       )
//              .allowMainThreadQueries()
//              .build()

       override fun getAllItems() : List<ItemsViewModel>{
//            return  db.todoDao().getAllItems()
              return listOf()
       }

       override fun insertItem(item: ItemsViewModel) {
//              db.todoDao().insertItem(item)
       }

       override fun updateItem(item: ItemsViewModel) {
//              db.todoDao().updateItem(item)
       }

       override fun deleteItem(item: ItemsViewModel) {
//              db.todoDao().deleteItem(item)
=======
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
>>>>>>> newBrunch
       }

       companion object{
              const val DATABASE_NAME = "database-name"
       }

}