package com.example.todolist

import android.app.Application
import com.example.todolist.data.PrefsRepositoryImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class PrefsRepositoryImplTest {

    private lateinit var subject : PrefsRepositoryImpl
    private val applicationMock: Application = mock()

    @Before
    fun setup(){
        subject = PrefsRepositoryImpl(applicationMock)
    }

    @Test
    fun getToDoItem_success(){
        subject.getToDoItem()
    }
}