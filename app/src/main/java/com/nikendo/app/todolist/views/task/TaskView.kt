package com.nikendo.app.todolist.views.task

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikendo.app.todolist.models.TaskEntity
import com.nikendo.app.todolist.ui.theme.MyTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskView(
    task: TaskEntity,
    position: TaskViewPosition,
    onTaskClick: (TaskEntity) -> Unit,
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
                .height(32.dp)
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
                onTaskClick = {},
                onRemove = {}
            )
            TaskView(
                task = TaskEntity(name = "Homework", isDone = true),
                position = TaskViewPosition.MIDDLE,
                onTaskClick = {},
                onRemove = {}
            )
            TaskView(
                task = TaskEntity(name = "Homework", isDone = true),
                position = TaskViewPosition.BOTTOM,
                onTaskClick = {},
                onRemove = {}
            )
        }
    }
}