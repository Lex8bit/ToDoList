package com.example.todolist

import android.app.Application
import android.content.SharedPreferences
import com.example.todolist.data.PrefsRepositoryImpl
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class PrefsRepositoryImplTest {

    private lateinit var subject : PrefsRepositoryImpl
    private val sharedPreferencesMock : SharedPreferences = mock()
    private val editorMock : SharedPreferences.Editor = mock()

    private val titleTest : String = "titleTest"
    private val descriptionTest : String = "descriptionTest"
    private val keyTest : String = "keyTest"
    private val valueTest : String = "valueTest"

    @Before
    fun setup(){
        subject = PrefsRepositoryImpl(sharedPreferencesMock)
    }

    @Test
    fun getToDoItem_success(){
        `when`(
            sharedPreferencesMock.getString(
                PrefsRepositoryImpl.PREFS_TITLE_KEY,
                PrefsRepositoryImpl.PREFS_DEFAULT_VALUE
            )
        ).thenReturn(titleTest)
        `when`(
            sharedPreferencesMock.getString(
                PrefsRepositoryImpl.PREFS_DESCRIPTION_KEY,
                PrefsRepositoryImpl.PREFS_DEFAULT_VALUE
            )
        ).thenReturn(descriptionTest)
        val result = subject.getToDoItem()
        assertEquals("titleTest", result.title)
        assertEquals("descriptionTest", result.description)
    }
    @Test
    fun saveDataInPrefs_success(){
        `when`(sharedPreferencesMock.edit()).thenReturn(editorMock)
        subject.saveDataInPrefs(keyTest,valueTest)
        verify(editorMock).putString(keyTest,valueTest)
    }
}