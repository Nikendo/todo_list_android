package com.nikendo.app.todolist.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.nikendo.app.todolist.App
import com.nikendo.app.todolist.intents.TaskIntent
import com.nikendo.app.todolist.models.Task
import com.nikendo.app.todolist.repository.AppDatabase
import com.nikendo.app.todolist.states.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val database: AppDatabase) : ViewModel() {
    private val _state = MutableStateFlow(TaskState(emptyList()))
    val state: StateFlow<TaskState> = _state

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return TaskViewModel(database) as T
            }
        }
    }

    init {
        processIntent(TaskIntent.LoadTasks)
    }

    fun processIntent(intent: TaskIntent) {
        when (intent) {
            is TaskIntent.LoadTasks -> loadTasks()
            is TaskIntent.AddTask -> addTask(intent.task)
            is TaskIntent.UpdateTask -> updateTask(intent.task)
        }
    }

    private fun addTask(task: Task) {
        // add new task to list
        viewModelScope.launch {
            database.taskDao().insertTask(task)
        }
    }

    private fun updateTask(task: Task) {
        // change task state
        viewModelScope.launch {
            database.taskDao().updateTask(task)
        }
    }

    private fun loadTasks() {
        // load tasks
        viewModelScope.launch {
            database.taskDao().getAllTasks().collect { tasks ->
                _state.value = _state.value.copy(tasks = tasks, isLoading = false)
            }
        }
    }
}