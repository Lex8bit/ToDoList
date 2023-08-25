package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.PrefsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomDialogViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
    ) : ViewModel() {

<<<<<<< HEAD
    private val prefsRepository : PrefsRepository = PrefsRepositoryImpl(app)
=======
>>>>>>> newBrunch
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