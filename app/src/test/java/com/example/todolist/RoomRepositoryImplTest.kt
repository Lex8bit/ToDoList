package com.example.todolist

import android.content.Context
import com.example.todolist.data.RoomRepositoryImpl
import com.example.todolist.room.ItemsToDoDao
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class RoomRepositoryImplTest {

    private lateinit var subject : RoomRepositoryImpl
    private val toDoDaoMock : ItemsToDoDao = mock()

    private val mockItemOne = ItemsViewModel(0,"TestTileOne","TestDescriptionOne")
    private val mockItemTwo = ItemsViewModel(1,"TestTileTwo","TestDescriptionTwo")
    private val mockItemThree = ItemsViewModel(2,"TestTileThree","TestDescriptionThree")
    private val mockList : List<ItemsViewModel> = listOf(
        mockItemOne,
        mockItemTwo
    )

    @Before
    fun setup(){
        subject = RoomRepositoryImpl(toDoDaoMock)
    }

    @Test
    fun getAllItems_success(){
        `when`(toDoDaoMock.getAllItems()).thenReturn(mockList)
        val result = subject.getAllItems()
        assertEquals(2,result.size)
    }
    @Test
    fun insertItem_success(){
        subject.insertItem(mockItemThree)
        verify(toDoDaoMock).insertItem(mockItemThree)
    }
    @Test
    fun updateItem_success(){
        subject.updateItem(mockItemTwo)
        verify(toDoDaoMock).updateItem(mockItemTwo)
    }
    @Test
    fun deleteItem_success(){
        subject.deleteItem(mockItemTwo)
        verify(toDoDaoMock).deleteItem(mockItemTwo)
    }
} 