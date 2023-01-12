package com.hbg0503.myapplication.room_database.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hbg0503.myapplication.room_database.ToDo
import com.hbg0503.myapplication.room_database.ToDoRepository.ToDoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ToDoViewModel(private val repository: ToDoRepository): ViewModel() {
    var tasks : List<ToDo>?= null
    fun getAllTasks(): Job {
        return viewModelScope.async {
            tasks = repository.getAllTask()
        }
    }
    fun getTheTask(): List<ToDo>?{
        return tasks
    }
    fun insertTask(toDo: ToDo): Long{
        var idTask : Long = 0
        viewModelScope.launch {
            repository.insertTask(toDo)
        }
        return idTask
    }
    fun insertTasks(toDo: ToDo): Long{
        var idTasks : Long = 0
        viewModelScope.launch {
            repository.insertTasks(toDo)
        }
        return idTasks
    }

    fun getTheTasks() {

    }
}
