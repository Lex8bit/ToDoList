package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.RoomRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val roomRepository:RoomRepository) : ViewModel() {
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
        val foundIndex = todoItemList.value?.indexOfFirst { it.id == item.id }
        foundIndex?.let {
            val list = todoItemList.value?.toMutableList()
            list?.set(it, item)
            todoItemList.value= list!!
        }
//        todoItemList.postValue(roomRepository.getAllItems())
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