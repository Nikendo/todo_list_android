package com.nikendo.app.todolist.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nikendo.app.todolist.intents.TaskIntent
import com.nikendo.app.todolist.models.Task
import com.nikendo.app.todolist.states.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskViewModel: ViewModel() {
    private val _state = MutableStateFlow(TaskState(emptyList()))
    val state: StateFlow<TaskState> = _state

    fun processIntent(intent: TaskIntent) {
        when (intent) {
            is TaskIntent.AddTask -> addTask(intent.task)
            is TaskIntent.ToggleTaskDone -> toggleTaskDone(intent.taskId)
        }
    }

    private fun addTask(task: Task) {
        // add new task to list
        val updatedList = _state.value.tasks.toMutableList().apply {
            add(task)
        }
        _state.value = _state.value.copy(updatedList)
    }

    private fun toggleTaskDone(taskId: String) {
        // change task state
        val updatedList = _state.value.tasks.map { task ->
            if (task.id == taskId) task.copy(isDone = !task.isDone) else task
        }

        _state.value = _state.value.copy(updatedList)
    }
}