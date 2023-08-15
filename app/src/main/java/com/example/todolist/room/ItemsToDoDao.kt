package com.example.todolist.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.ItemsViewModel

@Dao
interface ItemsToDoDao {
    @Query("SELECT * FROM itemsviewmodel")
    fun getAllItems(): List<ItemsViewModel>

    @Insert
    fun insertItem(itemsViewModel: ItemsViewModel)

    @Delete
    fun deleteItem(itemsViewModel: ItemsViewModel)

    @Update
    fun updateItem(itemsViewModel: ItemsViewModel)
}