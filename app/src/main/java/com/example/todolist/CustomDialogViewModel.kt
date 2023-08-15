package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.data.PrefsManagerImpl

class CustomDialogViewModel(app: Application) : AndroidViewModel(app) {

    private val prefsManager : PrefsManager = PrefsManagerImpl(app)
    private val todoItem: MutableLiveData<ItemsViewModel> = MutableLiveData()
    val todoItemResult: LiveData<ItemsViewModel> = todoItem

    fun getToDoItemFromPrefs() {
        val result = prefsManager.getToDoItem()
        todoItem.postValue(result)
    }

    fun saveDataInPrefs(key: String, value: String) {
        prefsManager.saveDataInPrefs(key,value)
    }
}