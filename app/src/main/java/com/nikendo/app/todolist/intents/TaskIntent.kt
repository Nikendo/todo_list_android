package com.nikendo.app.todolist.intents

import com.nikendo.app.todolist.models.TaskEntity

sealed class TaskIntent {
    data object LoadTasks : TaskIntent()

    //    data class AddTask(val task: TaskEntity) : TaskIntent()
    data class AddTask(val description: String) : TaskIntent()
    data class UpdateTask(val task: TaskEntity) : TaskIntent()
    data class DeleteTask(val task: TaskEntity) : TaskIntent()
    data object HideBottomSheet : TaskIntent()
    data object ShowNewTaskSheet : TaskIntent()
    data class ShowEditTaskSheet(val task: TaskEntity) : TaskIntent()
}