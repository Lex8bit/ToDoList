package com.example.todolist

import android.app.Application
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class CustomDialogViewModelTest {

    private lateinit var subject:CustomDialogViewModel
    private val applicationMock : Application = mock()

    @Before
    fun setup(){
        subject = CustomDialogViewModel(applicationMock)
    }

    @Test
    fun getToDoItemFromPrefs_success(){
        //Нужно все инициализировать 1 ЭТАП
        subject.getToDoItemFromPrefs()
        //Проверка результатов 3 ЭТАП
    }
}