package com.example.todolist

interface RoomRepository {
    fun getAllItems():List<ItemsViewModel>
    fun insertItem(item:ItemsViewModel)
    fun updateItem(item:ItemsViewModel)
    fun deleteItem(item:ItemsViewModel)

}