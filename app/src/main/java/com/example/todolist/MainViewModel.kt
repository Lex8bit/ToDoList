package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.data.RoomManagerImpl

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val roomManager : RoomManager = RoomManagerImpl(app)
    private val todoItemList: MutableLiveData<List<ItemsViewModel>> = MutableLiveData()
    val todoItemListResult: LiveData<List<ItemsViewModel>> = todoItemList

    fun getAllData() {
        val result = roomManager.getAllItems()
        todoItemList.postValue(result)
    }

    fun insertItem(item: ItemsViewModel) {
         todoItemList.value.let {
             todoItemList.postValue(it?.plus(item))
             roomManager.insertItem(item)
         }
    }

    fun updateItem(item: ItemsViewModel) {
        roomManager.updateItem(item)
// не обновляется Исправить!
    }

    fun deleteItem(item: ItemsViewModel) {
        todoItemList.value.let {
            todoItemList.postValue(it?.minus(item))
            roomManager.deleteItem(item)
        }
    }
}