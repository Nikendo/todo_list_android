package com.nikendo.app.todolist.views.task

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikendo.app.todolist.intents.TaskIntent
import com.nikendo.app.todolist.models.TaskEntity
import com.nikendo.app.todolist.states.TaskState
import com.nikendo.app.todolist.ui.theme.MyTheme

@Composable
fun TaskScreenView(state: TaskState, intent: (TaskIntent) -> Unit, modifier: Modifier = Modifier) {
    val (completed, uncompleted) = state.tasks.partition { it.isDone }
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 80.dp,
            bottom = 80.dp
        )
    ) {
        item { TaskListView(tasks = uncompleted, intent = intent) }

        if (completed.isNotEmpty()) {
            item { Spacer(Modifier.height(8.dp)) }
            item { TaskListView(tasks = completed, title = "Done:", intent = intent) }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskScreenPreview() {
    MyTheme {
        val taskState = TaskState(
            tasks = listOf(
                TaskEntity(
                    name = "Task 1",
                    isDone = false
                ),
                TaskEntity(
                    name = "Task 2",
                    isDone = false
                ),
                TaskEntity(
                    name = "Task 0",
                    isDone = true
                )
            )
        )
        TaskScreenView(state = taskState, intent = { })
    }
}