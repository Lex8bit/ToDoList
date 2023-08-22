package com.example.todolist

import android.content.Context
import com.example.todolist.data.RoomRepositoryImpl
import com.example.todolist.room.ItemsToDoDao
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class RoomRepositoryImplTest {

    private lateinit var subject : RoomRepositoryImpl
    private val toDoDaoMock : ItemsToDoDao = mock()

    @Before
    fun setup(){
        subject = RoomRepositoryImpl(toDoDaoMock)
    }

    @Test
    fun getAllItems_success(){
        subject.getAllItems()
    }
} 