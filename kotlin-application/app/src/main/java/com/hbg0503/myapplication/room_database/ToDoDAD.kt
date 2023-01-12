package com.hbg0503.myapplication.room_database

import androidx.room.*

@Dao
interface ToDoDAD {
    @Query("SELECT * FROM ToDo")
    suspend fun getAllTasks(): List<ToDo>
    @Insert
    suspend fun  insertTask(task: ToDo): Long
    @Insert
    suspend fun  insertTasks(task: ToDo): Long
    @Update
    suspend fun updateTask(task: ToDo)
    @Delete
    suspend fun deleteTask(task: ToDo)
    @Query("SELECT * FROM ToDo")
    suspend fun getTask(): ToDo?
}