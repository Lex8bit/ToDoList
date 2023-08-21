package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.data.PrefsRepositoryImpl

class CustomDialogViewModel(app: Application) : AndroidViewModel(app) {

    private val prefsRepository : PrefsRepository = PrefsRepositoryImpl(app)
    private val todoItem: MutableLiveData<ItemsViewModel> = MutableLiveData()
    val todoItemResult: LiveData<ItemsViewModel> = todoItem

    /**Provides preferences values for ToDoItem*/
    fun getToDoItemFromPrefs() {
        val result = prefsRepository.getToDoItem()
        todoItem.postValue(result)
    }

    /**Save data in shared preferences manager
     * @param key - provides prefs information to save data
     * @param value - provides data that need to be saved in prefs
     */
    fun saveDataInPrefs(key: String, value: String) {
        prefsRepository.saveDataInPrefs(key,value)
    }
}