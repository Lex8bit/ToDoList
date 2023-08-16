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

    /**Provides preferences values for ToDoItem*/
    fun getToDoItemFromPrefs() {
        val result = prefsManager.getToDoItem()
        todoItem.postValue(result)
    }

    /**Save data in shared preferences manager
     * @param key - provides prefs information to save data
     * @param value - provides data that need to be saved in prefs
     */
    fun saveDataInPrefs(key: String, value: String) {
        prefsManager.saveDataInPrefs(key,value)
    }
}