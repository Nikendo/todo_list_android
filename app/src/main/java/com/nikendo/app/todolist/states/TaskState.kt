package com.nikendo.app.todolist.states

import com.nikendo.app.todolist.models.TaskEntity

data class TaskState(
    val tasks: List<TaskEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)