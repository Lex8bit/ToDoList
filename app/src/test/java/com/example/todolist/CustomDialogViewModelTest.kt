package com.example.todolist

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class CustomDialogViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var subject: CustomDialogViewModel
    private val prefsRepositoryMock: PrefsRepository = mock()

    private val todoItemFake : ItemsViewModel = ItemsViewModel(0,"testItem","testDescription")
    private val keyTestValue : String = "testKey"
    private val valueTestValue : String = "testValue"

    @Before
    fun setup(){
        subject = CustomDialogViewModel(prefsRepositoryMock)
    }

    @Test
    fun getToDoItemFromPrefs_success(){
        //Нужно все инициализировать 1 ЭТАП
        `when`(prefsRepositoryMock.getToDoItem()).thenReturn(todoItemFake)
        //2 ЭТАП
        subject.getToDoItemFromPrefs()
        val expectedResult = subject.todoItemResult.value?.title
        //Проверка результатов 3 ЭТАП
        assertEquals("testItem",expectedResult)
    }

    @Test
    fun saveDataInPrefs_success(){
        subject.saveDataInPrefs(keyTestValue,valueTestValue)
        verify(prefsRepositoryMock).saveDataInPrefs(keyTestValue,valueTestValue)
    }
}