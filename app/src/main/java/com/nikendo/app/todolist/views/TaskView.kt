package com.nikendo.app.todolist.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikendo.app.todolist.models.Task
import com.nikendo.app.todolist.ui.theme.ToDoListTheme

@Composable
fun TaskView(
    task: Task,
    onTaskClick: (Task) -> Unit
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
            colors = CheckboxDefaults.colors(checkedColor = Color.LightGray),
            onCheckedChange = { onTaskClick(task) }
        )
        Text(
            text = task.name,
            color = if (task.isDone) Color.LightGray else Color.Unspecified,
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(1f)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskViewPreview() {
    ToDoListTheme {
        Column {
            TaskView(task = Task("0", "Homework", false), onTaskClick = {})
            TaskView(task = Task("0", "Homework", true), onTaskClick = {})
        }
    }
}