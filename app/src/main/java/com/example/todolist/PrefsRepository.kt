package com.example.todolist

interface PrefsRepository {
    /**
     * Return ToDoItem from Prefs
     */
    fun getToDoItem() : ItemsViewModel
    /**
     * Saving ToDoItem in Prefs
     */
    fun saveDataInPrefs(key: String, value: String)
}