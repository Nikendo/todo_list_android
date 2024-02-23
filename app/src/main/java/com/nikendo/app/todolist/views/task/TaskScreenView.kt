package com.nikendo.app.todolist.views.task

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikendo.app.todolist.intents.TaskIntent
import com.nikendo.app.todolist.models.TaskEntity
import com.nikendo.app.todolist.states.TaskState
import com.nikendo.app.todolist.ui.theme.MyTheme

@Composable
fun TaskScreenView(state: TaskState, intent: (TaskIntent) -> Unit, modifier: Modifier = Modifier) {
    if (state.tasks.isNotEmpty()) {
        TaskListView(state, intent)
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = "You don't have any task yet.\n" +
                        "Add a new task by + button",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun TaskListView(
    state: TaskState,
    intent: (TaskIntent) -> Unit
) {
    val (completed, uncompleted) = state.tasks.partition { it.isDone }
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 80.dp,
            bottom = 80.dp
        )
    ) {
        items(items = uncompleted, key = { it.hashCode() }) { task ->
            TaskView(
                task = task,
                position = getTaskPosition(uncompleted, task),
                onDoneClick = {
                    intent(TaskIntent.UpdateTask(it.copy(isDone = !it.isDone)))
                },
                onEditClick = {
                    intent(TaskIntent.ShowEditTaskSheet(it))
                },
                onRemove = {
                    intent(TaskIntent.DeleteTask(it))
                },
                modifier = Modifier.animateContentSize()
            )
        }

        if (completed.isNotEmpty()) {
            item { Spacer(Modifier.height(8.dp)) }
            item { Text(text = "Done:", modifier = Modifier.padding(8.dp)) }
            items(items = completed, key = { it.hashCode() }) { task ->
                TaskView(
                    task = task,
                    position = getTaskPosition(completed, task),
                    onDoneClick = {
                        intent(TaskIntent.UpdateTask(it.copy(isDone = !it.isDone)))
                    },
                    onEditClick = {
                        intent(TaskIntent.ShowEditTaskSheet(it))
                    },
                    onRemove = {
                        intent(TaskIntent.DeleteTask(it))
                    },
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    }
}

private fun getTaskPosition(
    tasks: List<TaskEntity>,
    task: TaskEntity
) = if (tasks.size > 1) when (task) {
    tasks.first() -> TaskViewPosition.TOP
    tasks.last() -> TaskViewPosition.BOTTOM
    else -> TaskViewPosition.MIDDLE
} else TaskViewPosition.SINGLE

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