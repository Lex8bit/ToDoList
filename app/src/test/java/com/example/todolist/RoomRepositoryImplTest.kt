package com.example.todolist

import android.content.Context
import com.example.todolist.data.RoomRepositoryImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class RoomRepositoryImplTest {

    private lateinit var subject : RoomRepositoryImpl
    private val contextMock : Context = mock()

    @Before
    fun setup(){
        subject = RoomRepositoryImpl(contextMock)
    }

    @Test
    fun getAllItems_success(){
        subject.getAllItems()
    }
} 