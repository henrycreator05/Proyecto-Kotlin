package com.hbg0503.myapplication.room_database.ToDoRepository

import com.hbg0503.myapplication.room_database.ToDo
import com.hbg0503.myapplication.room_database.ToDoDAD

class ToDoRepository(private val toDoDao : ToDoDAD) {
    suspend fun getAllTask(): List<ToDo>{
        return toDoDao.getAllTasks()
    }
    suspend fun insertTask(toDo: ToDo): Long {
        return toDoDao.insertTask(toDo)
    }
    suspend fun insertTasks(toDo: ToDo): List<List<Long>> {
        return listOf(listOf(toDoDao.insertTasks(toDo)))
    }
}