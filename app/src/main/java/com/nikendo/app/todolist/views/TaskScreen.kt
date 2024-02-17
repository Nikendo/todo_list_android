package com.nikendo.app.todolist.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikendo.app.todolist.intents.TaskIntent
import com.nikendo.app.todolist.models.Task
import com.nikendo.app.todolist.states.TaskState
import com.nikendo.app.todolist.ui.theme.MyTheme

@Composable
fun TaskScreen(state: TaskState, intent: (TaskIntent) -> Unit, modifier: Modifier = Modifier) {
    val (completed, uncompleted) = state.tasks.partition { it.isDone }
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 80.dp,
            bottom = 80.dp
        )
    ) {
//        item { Spacer(Modifier.height(80.dp)) }
        item {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = MaterialTheme.shapes.small
            ) {
                Column {
                    uncompleted.forEach { task ->
                        TaskView(
                            task = task,
                            onTaskClick = {
                                intent(TaskIntent.UpdateTask(it.copy(isDone = !it.isDone)))
                            }
                        )
                        Spacer(Modifier.height(4.dp))
                    }
                }
            }
        }

        if (completed.isNotEmpty()) {
            item { Spacer(Modifier.height(8.dp)) }
            item {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = MaterialTheme.shapes.small
                ) {
                    Column {
                        Text("Done:", modifier = Modifier.padding(8.dp))
                        completed.forEach { task ->
                            TaskView(
                                task = task,
                                onTaskClick = {
                                    intent(TaskIntent.UpdateTask(it.copy(isDone = !it.isDone)))
                                }
                            )
                            Spacer(Modifier.height(4.dp))
                        }
                    }
                }
            }
        }

//        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskScreenPreview() {
    MyTheme {
        val taskState = TaskState(
            tasks = listOf(
                Task(
                    name = "Task 1",
                    isDone = false
                ),
                Task(
                    name = "Task 2",
                    isDone = false
                ),
                Task(
                    name = "Task 0",
                    isDone = true
                )
            )
        )
        TaskScreen(state = taskState, intent = { })
    }
}