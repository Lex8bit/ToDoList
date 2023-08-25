package com.example.todolist.data

import android.content.SharedPreferences
import com.example.todolist.ItemsViewModel
import com.example.todolist.PrefsRepository
<<<<<<< HEAD
=======
import javax.inject.Inject
>>>>>>> newBrunch

/**
 * Manager that handles logic with shared preferences
 */
<<<<<<< HEAD
class PrefsRepositoryImpl(app: Application) : PrefsRepository {
    private val sharedPref : SharedPreferences = app.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
=======
class PrefsRepositoryImpl @Inject constructor(private val sharedPreferences :SharedPreferences) : PrefsRepository {
>>>>>>> newBrunch
    override fun getToDoItem(): ItemsViewModel {
        val title = sharedPreferences.getString(PREFS_TITLE_KEY,PREFS_DEFAULT_VALUE) ?: PREFS_DEFAULT_VALUE
        val description = sharedPreferences.getString(PREFS_DESCRIPTION_KEY,PREFS_DEFAULT_VALUE) ?: PREFS_DEFAULT_VALUE
        return ItemsViewModel(0, title, description)
    }

    override fun saveDataInPrefs(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(keyq, value)
            apply()
        }
    }

    companion object{
        const val PREFS_TITLE_KEY = "titleKey"
        const val PREFS_DESCRIPTION_KEY = "descriptionKey"
        const val PREFS_NAME = "preferences"
        const val PREFS_DEFAULT_VALUE = ""
    }
}