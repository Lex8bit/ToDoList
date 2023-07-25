package com.example.todolist.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.ItemsViewModel


@Database(entities = [ItemsViewModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): ItemsToDoDao
}
