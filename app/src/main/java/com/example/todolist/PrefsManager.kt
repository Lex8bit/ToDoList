package com.example.todolist

interface PrefsManager {
    fun getToDoItem() : ItemsViewModel
    fun saveDataInPrefs(key: String, value: String)
}