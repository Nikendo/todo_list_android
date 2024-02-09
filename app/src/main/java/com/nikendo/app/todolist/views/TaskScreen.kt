package com.nikendo.app.todolist.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikendo.app.todolist.intents.TaskIntent
import com.nikendo.app.todolist.models.Task
import com.nikendo.app.todolist.states.TaskState
import com.nikendo.app.todolist.ui.theme.ToDoListTheme
import java.util.UUID

@Composable
fun TaskScreen(state: TaskState, intent: (TaskIntent) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
//        var text by remember { mutableStateOf(TextFieldValue("")) }
//
//        // TextField for new task
//        OutlinedTextField(
//            value = text,
//            onValueChange = { text = it },
//            modifier = Modifier.fillMaxWidth(),
//            placeholder = { Text("Add new task") }
//        )
//
//        Spacer(Modifier.height(8.dp))
//
//        // New task button
//        Button(
//            onClick = {
//                if (text.text.isNotBlank()) {
//                    intent(TaskIntent.AddTask(Task(UUID.randomUUID().toString(), text.text, false)))
//                    text = TextFieldValue("")
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Add")
//        }
//
//        Spacer(Modifier.height(16.dp))

        // Task list
        TasksToDo(state.tasks, intent)
        // Completed tasks
        val completedTasks = state.tasks
        if (completedTasks.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            CompletedTasks(state.tasks.filter { it.isDone }, intent)
        }
    }
}

@Composable
private fun TasksToDo(
    tasks: List<Task>,
    intent: (TaskIntent) -> Unit
) {
    Surface(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shape = MaterialTheme.shapes.small
    ) {
        LazyColumn {
            items(tasks) { task ->
                AnimatedVisibility(
                    visible = !task.isDone,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    TaskView(
                        task = task,
                        onTaskClick = { intent(TaskIntent.ToggleTaskDone(it.id)) }
                    )
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
private fun CompletedTasks(
    tasks: List<Task>,
    intent: (TaskIntent) -> Unit
) {
    Surface(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shape = MaterialTheme.shapes.small
    ) {
        Column {
            if (tasks.filter { it.isDone }.isNotEmpty()) {
                Text("Done:", modifier = Modifier.padding(8.dp))
            }
            LazyColumn {
                items(tasks) { task ->
                    AnimatedVisibility(
                        visible = task.isDone,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        TaskView(
                            task = task,
                            onTaskClick = { intent(TaskIntent.ToggleTaskDone(it.id)) }
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskScreenPreview() {
    ToDoListTheme {
        val taskState = TaskState(tasks = listOf(
            Task(
                id = UUID.randomUUID().toString(),
                name = "Task 1",
                isDone = false
            ),
            Task(
                id = UUID.randomUUID().toString(),
                name = "Task 2",
                isDone = false
            ),
            Task(
                id = UUID.randomUUID().toString(),
                name = "Task 0",
                isDone = true
            )
        ))
        TaskScreen(state = taskState, intent = { })
    }
}