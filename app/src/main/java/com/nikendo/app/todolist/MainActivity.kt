package com.nikendo.app.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikendo.app.todolist.intents.TaskIntent
import com.nikendo.app.todolist.states.BottomSheetState
import com.nikendo.app.todolist.ui.theme.MyTheme
import com.nikendo.app.todolist.viewModels.TaskViewModel
import com.nikendo.app.todolist.views.sheets.EditTaskSheetContent
import com.nikendo.app.todolist.views.sheets.NewTaskSheetContent
import com.nikendo.app.todolist.views.task.TaskScreenView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ) {
                    MyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val viewModel: TaskViewModel = viewModel(factory = TaskViewModel.factory)
    val state = viewModel.state.collectAsState().value
    val bottomSheetState = viewModel.state.collectAsState().value.bottomSheetState

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.task_list))
            })
        },
        content = { padding ->
            TaskScreenView(state, viewModel::processIntent, Modifier.padding(padding))
            when (bottomSheetState) {
                is BottomSheetState.Hidden -> Unit
                is BottomSheetState.CreateTask -> {
                    ModalBottomSheet(
                        onDismissRequest = { viewModel.processIntent(TaskIntent.HideBottomSheet) }
                    ) {
                        NewTaskSheetContent(viewModel::processIntent)
                    }
                }

                is BottomSheetState.EditTask -> {
                    val task = bottomSheetState.task
                    ModalBottomSheet(
                        onDismissRequest = { viewModel.processIntent(TaskIntent.HideBottomSheet) }
                    ) {
                        EditTaskSheetContent(task = task, intent = viewModel::processIntent)
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.processIntent(TaskIntent.ShowNewTaskSheet) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyTheme {
        MyApp()
    }
}

