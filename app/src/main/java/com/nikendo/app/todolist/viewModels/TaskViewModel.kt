package com.nikendo.app.todolist.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.nikendo.app.todolist.App
import com.nikendo.app.todolist.intents.TaskIntent
import com.nikendo.app.todolist.models.TaskEntity
import com.nikendo.app.todolist.repository.AppDatabase
import com.nikendo.app.todolist.states.BottomSheetState
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
            is TaskIntent.AddTask -> addTask(intent.description)
            is TaskIntent.UpdateTask -> updateTask(intent.task)
            is TaskIntent.DeleteTask -> deleteTask(intent.task)
            is TaskIntent.ShowNewTaskSheet -> showCreateTaskSheet()
            is TaskIntent.ShowEditTaskSheet -> showEditTaskSheet(intent.task)
            is TaskIntent.HideBottomSheet -> hideBottomSheet()
        }
    }

    private fun addTask(taskDescription: String) {
        // add new task to list
        viewModelScope.launch {
            database.taskDao().insertTask(TaskEntity(name = taskDescription, isDone = false))
            _state.value = _state.value.copy(bottomSheetState = BottomSheetState.Hidden)
        }
    }

    private fun updateTask(task: TaskEntity) {
        // change task state
        viewModelScope.launch {
            database.taskDao().updateTask(task)
            _state.value = _state.value.copy(bottomSheetState = BottomSheetState.Hidden)
        }
    }

    private fun deleteTask(task: TaskEntity) {
        // change task state
        viewModelScope.launch {
            database.taskDao().deleteTask(task)
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

    private fun showCreateTaskSheet() {
        viewModelScope.launch {
            _state.value = _state.value.copy(bottomSheetState = BottomSheetState.CreateTask)
        }
    }

    private fun showEditTaskSheet(task: TaskEntity) {
        viewModelScope.launch {
            _state.value = _state.value.copy(bottomSheetState = BottomSheetState.EditTask(task))
        }
    }

    private fun hideBottomSheet() {
        viewModelScope.launch {
            _state.value = _state.value.copy(bottomSheetState = BottomSheetState.Hidden)
        }
    }
}