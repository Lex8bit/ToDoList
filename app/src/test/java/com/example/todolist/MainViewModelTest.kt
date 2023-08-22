package com.example.todolist

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var subject : MainViewModel
    private val roomRepositoryMock : RoomRepository = mock()

    private val itemTest : ItemsViewModel = ItemsViewModel(0, "test Title", "test Description")

    @Before
    fun setup(){
        subject = MainViewModel(roomRepositoryMock)
    }

    @Test
    fun insertItem_success(){
        subject.insertItem(itemTest)
        //3 Проверка room manager получил ли элемент с заголовком itemTest.title
    }
}