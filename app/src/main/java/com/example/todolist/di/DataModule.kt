package com.example.todolist.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.todolist.PrefsRepository
import com.example.todolist.RoomRepository
import com.example.todolist.data.PrefsRepositoryImpl
import com.example.todolist.data.RoomRepositoryImpl
import com.example.todolist.room.AppDatabase
import com.example.todolist.room.ItemsToDoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::
        class.java, RoomRepositoryImpl.DATABASE_NAME
    )
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideToDoDao(appDatabase: AppDatabase) = appDatabase.todoDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) : SharedPreferences =
        context.getSharedPreferences(PrefsRepositoryImpl.PREFS_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideRoomRepository(toDoDao: ItemsToDoDao) : RoomRepository = RoomRepositoryImpl(toDoDao)

    @Singleton
    @Provides
    fun providePrefsRepository(@ApplicationContext application: Application) : PrefsRepository = PrefsRepositoryImpl(application)



}