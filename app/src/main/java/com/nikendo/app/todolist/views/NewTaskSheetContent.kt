package com.nikendo.app.todolist.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.nikendo.app.todolist.intents.TaskIntent
import com.nikendo.app.todolist.models.Task
import com.nikendo.app.todolist.viewModels.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskSheetContent(
    viewModel: TaskViewModel,
    scope: CoroutineScope,
    sheetState: SheetState,
    showBottomSheet: (Boolean) -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val createTask: () -> Unit = {
        if (text.text.isNotBlank()) {
            viewModel.processIntent(
                TaskIntent.AddTask(
                    Task(
//                        UUID.randomUUID(),
                        name = text.text,
                        isDone = false
                    )
                )
            )
            text = TextFieldValue("")
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet(false)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .navigationBarsPadding()
    ) {
        // TextField for new task
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { createTask() }),
            placeholder = { Text("Add new task") }
        )

        // New task button
        Button(
            onClick = createTask,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Add")
        }
    }
}