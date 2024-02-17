package com.nikendo.app.todolist.intents

import com.nikendo.app.todolist.models.Task

sealed class TaskIntent {
    object LoadTasks: TaskIntent()
    data class AddTask(val task: Task): TaskIntent()
    data class UpdateTask(val task: Task): TaskIntent()
    // data class DeleteTask(val taskId: String): TaskIntent()
}