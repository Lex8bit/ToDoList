package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemsViewModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String
    ) {
}
