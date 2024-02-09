package com.nikendo.app.todolist.intents

import com.nikendo.app.todolist.models.Task

sealed class TaskIntent {
    data class AddTask(val task: Task): TaskIntent()
    data class ToggleTaskDone(val taskId: String): TaskIntent()
    // data class DeleteTask(val taskId: String): TaskIntent()
    // data class EditTask(val task: Task): TaskIntent()
}