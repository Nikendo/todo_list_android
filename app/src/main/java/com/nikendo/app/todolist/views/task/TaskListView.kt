package com.nikendo.app.todolist.views.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nikendo.app.todolist.intents.TaskIntent
import com.nikendo.app.todolist.models.TaskEntity

@Composable
internal fun TaskListView(
    tasks: List<TaskEntity>,
    title: String? = null,
    intent: (TaskIntent) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        shape = MaterialTheme.shapes.small
    ) {
        Column {
            if (!title.isNullOrBlank()) {
                Text(text = title, modifier = Modifier.padding(8.dp))
            }
            tasks.forEachIndexed { index, task ->
                TaskView(
                    task = task,
                    onTaskClick = {
                        intent(TaskIntent.UpdateTask(it.copy(isDone = !it.isDone)))
                    }
                )
                if (tasks.size - 1 > index) Spacer(Modifier.height(4.dp))
            }
        }
    }
}