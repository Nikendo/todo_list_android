package com.nikendo.app.todolist.views.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikendo.app.todolist.models.TaskEntity
import com.nikendo.app.todolist.ui.theme.MyTheme

@Composable
fun TaskView(
    task: TaskEntity,
    onTaskClick: (TaskEntity) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(32.dp)
    ) {
        Checkbox(
            checked = task.isDone,
            colors = CheckboxDefaults.colors(
                checkmarkColor = if (!task.isDone) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                uncheckedColor = MaterialTheme.colorScheme.primary,
                checkedColor = MaterialTheme.colorScheme.outline,
            ),
            onCheckedChange = { onTaskClick(task) }
        )
        Text(
            text = task.name,
            color = if (task.isDone) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(1f)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskViewPreview() {
    MyTheme {
        Column {
            TaskView(task = TaskEntity(name = "Homework", isDone = false), onTaskClick = {})
            TaskView(task = TaskEntity(name = "Homework", isDone = true), onTaskClick = {})
        }
    }
}