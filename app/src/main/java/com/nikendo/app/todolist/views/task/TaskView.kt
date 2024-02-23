package com.nikendo.app.todolist.views.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikendo.app.todolist.models.TaskEntity
import com.nikendo.app.todolist.ui.theme.MyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskView(
    task: TaskEntity,
    position: TaskViewPosition,
    onDoneClick: (TaskEntity) -> Unit,
    onEditClick: (TaskEntity) -> Unit,
    onRemove: (TaskEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val corner = 16f
    val topRoundedShape = RoundedCornerShape(topStart = corner, topEnd = corner)
    val middleShape = RectangleShape
    val bottomRoundedShape = RoundedCornerShape(bottomStart = corner, bottomEnd = corner)
    val singleShape = RoundedCornerShape(corner)
    val shape: Shape = when (position) {
        TaskViewPosition.TOP -> topRoundedShape
        TaskViewPosition.BOTTOM -> bottomRoundedShape
        TaskViewPosition.SINGLE -> singleShape
        else -> middleShape
    }
    SwipeToDeleteContainer(item = task, onDelete = onRemove) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = shape,
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable { onEditClick(task) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.isDone,
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = if (!task.isDone) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        uncheckedColor = MaterialTheme.colorScheme.primary,
                        checkedColor = MaterialTheme.colorScheme.outline,
                    ),
                    onCheckedChange = { onDoneClick(task) },
                    modifier = Modifier.semantics {
                        contentDescription = "Mark ${it.name} as ${if (it.isDone) "not done" else "done"}"
                    }
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
    }
}

enum class TaskViewPosition {
    TOP, MIDDLE, BOTTOM, SINGLE
}

@Preview(showSystemUi = true)
@Composable
fun TaskViewPreview() {
    MyTheme {
        Column {
            TaskView(
                task = TaskEntity(name = "Homework", isDone = false),
                position = TaskViewPosition.TOP,
                onDoneClick = {},
                onEditClick = {},
                onRemove = {}
            )
            TaskView(
                task = TaskEntity(name = "Homework", isDone = true),
                position = TaskViewPosition.MIDDLE,
                onDoneClick = {},
                onEditClick = {},
                onRemove = {}
            )
            TaskView(
                task = TaskEntity(name = "Homework", isDone = true),
                position = TaskViewPosition.BOTTOM,
                onDoneClick = {},
                onEditClick = {},
                onRemove = {}
            )
        }
    }
}