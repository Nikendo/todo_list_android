package com.nikendo.app.todolist.states

import com.nikendo.app.todolist.models.Task

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)