package com.example.todolist

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var subject : MainViewModel
    private val roomRepositoryMock : RoomRepository = mock()

    private val mockItemOne = ItemsViewModel(0,"TestTileOne","TestDescriptionOne")
    private val mockItemTwo = ItemsViewModel(1,"TestTileTwo","TestDescriptionTwo")
    private val mockItemThree = ItemsViewModel(2,"TestTileThree","TestDescriptionThree")
    private val mockItemFour = ItemsViewModel(0,"TestTileFour","TestDescriptionFour")
    private val mockList : List<ItemsViewModel> = listOf(
        mockItemOne,
        mockItemTwo
    )

    @Before
    fun setup(){
        subject = MainViewModel(roomRepositoryMock)
    }

    @Test
    fun getAllData_success(){
        //Нужно все инициализировать 1 ЭТАП
        `when`(roomRepositoryMock.getAllItems()).thenReturn(mockList)
        //2 ЭТАП
        subject.getAllData()
        val expectedResult = subject.todoItemListResult.value?.size
        val firstItemTitle = subject.todoItemListResult.value?.get(0)
        //Проверка результатов 3 ЭТАП
        assertEquals(2, expectedResult)
        assertEquals("TestTileOne", firstItemTitle?.title)
        assertEquals("TestDescriptionOne", firstItemTitle?.description)
    }
    @Test
    fun insertItem_success(){
        `when`(roomRepositoryMock.getAllItems()).thenReturn(mockList)
        subject.getAllData()
        val expectedResult = subject.todoItemListResult.value?.size
        assertEquals(2, expectedResult)
        subject.insertItem(mockItemThree)
        val expectedResultAfterInsert = subject.todoItemListResult.value?.size
        assertEquals(3, expectedResultAfterInsert)
        val lastItemTitle = subject.todoItemListResult.value?.last()
        assertEquals("TestTileThree", lastItemTitle?.title)
        assertEquals("TestDescriptionThree", lastItemTitle?.description)
    }

    @Test
    fun updateItem_success(){
        `when`(roomRepositoryMock.getAllItems()).thenReturn(mockList)
        subject.getAllData()

        subject.updateItem(mockItemFour)
        val updatedItem = subject.todoItemListResult.value?.first()
        assertEquals("TestTileFour", updatedItem?.title)
        assertEquals("TestDescriptionFour", updatedItem?.description)

    }
    @Test
    fun deleteItem_success(){
        `when`(roomRepositoryMock.getAllItems()).thenReturn(mockList)
        subject.getAllData()
        val expectedResult = subject.todoItemListResult.value?.size
        assertEquals(2, expectedResult)

        subject.deleteItem(mockItemTwo)
        val expectedResultAfterDelete = subject.todoItemListResult.value?.size
        assertEquals(1, expectedResultAfterDelete)
    }

}