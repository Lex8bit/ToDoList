package com.example.todolist.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.todolist.ItemsViewModel
import com.example.todolist.PrefsManager

/**
 * Use to manage work with shared preferences
 */
class PrefsManagerImpl(app: Application) : PrefsManager {
    private val sharedPref :SharedPreferences = app.getSharedPreferences("preferences",Context.MODE_PRIVATE)
    override fun getToDoItem(): ItemsViewModel {
        val title = sharedPref.getString("titleKey","") ?: ""
        val description = sharedPref.getString("descriptionKey","") ?: ""
        val number = sharedPref.getInt("numberKey",0)
        return ItemsViewModel(0, title, description)
    }

    override fun saveDataInPrefs(key: String, value: String) {
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }
}