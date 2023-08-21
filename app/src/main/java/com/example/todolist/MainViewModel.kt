package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.data.RoomRepositoryImpl

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val roomRepository : RoomRepository = RoomRepositoryImpl(app)
    private val todoItemList: MutableLiveData<List<ItemsViewModel>> = MutableLiveData()
    val todoItemListResult: LiveData<List<ItemsViewModel>> = todoItemList

    /**
     * Provides all data from Room
     */
    fun getAllData() {
        val result = roomRepository.getAllItems()
        todoItemList.postValue(result)
    }

    /**
     * Insert data in Room database
     * @param item - provides item that need to be insert in room database
     */
    fun insertItem(item: ItemsViewModel) {
         todoItemList.value.let {
             todoItemList.postValue(it?.plus(item))
             roomRepository.insertItem(item)
         }
    }

    /**
     * Update existing item in Room database
     * @param item - provides item that need to be update in room database
     */
    fun updateItem(item: ItemsViewModel) {
        roomRepository.updateItem(item)
        todoItemList.postValue(roomRepository.getAllItems())
    }

    /**
     * Delete existing item from Room database
     * @param item - provides item that need to be deleted from room database
     */
    fun deleteItem(item: ItemsViewModel) {
        todoItemList.value.let {
            todoItemList.postValue(it?.minus(item))
            roomRepository.deleteItem(item)
        }
    }
}