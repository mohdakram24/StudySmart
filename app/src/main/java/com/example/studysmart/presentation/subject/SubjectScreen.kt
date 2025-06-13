package com.example.studysmart.presentation.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.studysmart.presentation.components.AddSubjectDialog
import com.example.studysmart.presentation.components.CountCard
import com.example.studysmart.presentation.components.DeleteDialog
import com.example.studysmart.presentation.components.studySessionList
import com.example.studysmart.presentation.components.tasksList
import com.example.studysmart.destinations.TaskScreenRouteDestination
import com.example.studysmart.domain.model.Subject
import com.example.studysmart.listOfTask
import com.example.studysmart.sessionList
import com.example.studysmart.presentation.task.TaskScreenNavArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

// For passing the arguments from one screen to another
data class SubjectScreenNavArgs(
    val subjectId: Int
)
@Destination(navArgsDelegate = SubjectScreenNavArgs::class)
@Composable
fun SubjectScreenRoute(
    navigator: DestinationsNavigator
) {
    SubjectScreen(
        onBackButtonClick = {
            // Return to that screen from where it has come.
            navigator.navigateUp()
        },
        onAddTaskButtonClick = {
            val navArg = TaskScreenNavArgs(taskId = null, subjectId = -1)
            navigator.navigate(TaskScreenRouteDestination(navArg))
        },
        onTaskCardClick = { taskId ->
            val navArg = TaskScreenNavArgs(taskId = taskId, subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArg))
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreen(
    onBackButtonClick: () -> Unit,
    onAddTaskButtonClick: () -> Unit,
    onTaskCardClick: (Int?) -> Unit,

) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val isFABExtended by remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }
    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isEditSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }
    var subjectName by remember { mutableStateOf("") }
    var goalHours by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Subject.subjectColors.random()) }
    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = { isAddSubjectDialogOpen = false },
        subjectName = subjectName,
        goalHours = goalHours,
        onSubjectNameChange = { subjectName = it},
        onGoalHoursChange = {goalHours = it},
        selectedColors = selectedColor,
        onColorChange = {selectedColor = it}
    )
    AddSubjectDialog(
        isOpen = isEditSubjectDialogOpen,
        onDismissRequest = { isEditSubjectDialogOpen = false },
        onConfirmButtonClick = { isEditSubjectDialogOpen = false },
        subjectName = subjectName,
        goalHours = goalHours,
        onSubjectNameChange = { subjectName = it},
        onGoalHoursChange = {goalHours = it},
        selectedColors = selectedColor,
        onColorChange = {selectedColor = it}
    )
    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session",
        bodyText = "Are you sure you want to delete this session? Your study hours will be reduced"+
                "by this session time. This action cannot be undo.",
        onDismissRequest = {isDeleteSessionDialogOpen = false},
        onConfirmButtonClick = {isDeleteSessionDialogOpen = false}
    )
    DeleteDialog(
        isOpen = isDeleteSubjectDialogOpen,
        title = "Delete Subject",
        bodyText = "Are you sure you want to delete this subject? All related " +
        "tasks and study sessions will be permanently removed. This action cannot be undone.",
        onDismissRequest = {isDeleteSubjectDialogOpen = false},
        onConfirmButtonClick = {isDeleteSubjectDialogOpen = false}
    )
    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Delete Session",
        bodyText = "Are you sure you want to delete this session? Your study hours will be reduced"+
                "by this session time. This action cannot be undo.",
        onDismissRequest = {isDeleteSessionDialogOpen = false},
        onConfirmButtonClick = {isDeleteSessionDialogOpen = false}
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SubjectScreenTopAppBar(
                title = "English",
                onBackButtonClick = onBackButtonClick,
                onDeleteButtonClick = {isDeleteSubjectDialogOpen = true},
                onEditButtonClick = {isEditSubjectDialogOpen = true},
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onAddTaskButtonClick,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add")
                },
                text = { Text(
                    text = "Add task"
                ) },
                expanded = isFABExtended)
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
        ) {
            item{
                SubjectOverviewSection(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    studiedHours = "10",
                    goalHours = "15",
                    progress = 10f

                )
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks.\n" +
                        " Click the + Button to add new task.",
                tasks = listOfTask,
                onCheckBoxClick = {},
                onTaskCardClick = onTaskCardClick)

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            tasksList(
                sectionTitle = "COMPLETED TASKS",
                emptyListText = "You don't have any completed tasks.\n" +
                        " Click the check box on completion of task.",
                tasks = listOfTask,
                onCheckBoxClick = {},
                onTaskCardClick = onTaskCardClick)

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            studySessionList(
                sectionTitle = "RECENT STUDY SESSIONS",
                sessionList = sessionList,
                onDeleteIconClick = { isDeleteSessionDialogOpen = true}
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreenTopAppBar(
    title:String,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    // Places title on second line. It is used when we have more than 2 action icon.
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "navigate back"
                )
            }
        },
        actions = {
            IconButton(onClick = onDeleteButtonClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Subject"
                )
            }
            IconButton(onClick = onEditButtonClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Subject"
                )
            }
        }
    )
}

@Composable
private fun SubjectOverviewSection(
    modifier: Modifier,
    studiedHours: String,
    goalHours: String,
    progress: Float
) {
    val percentageProgress = remember(progress) {
        (progress * 100).toInt().coerceIn(0, 100)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
         verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalHours
        )
        Spacer(
            modifier = Modifier.width(10.dp)
        )
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Study Hours",
            count = studiedHours
        )
        Spacer(
            modifier = Modifier.width(10.dp)
        )
        Box(modifier = Modifier
            .size(75.dp),
            contentAlignment = Alignment.Center){
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = {1f},
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = {progress},
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round
            )
            Text(
                text = "$percentageProgress%"
            )
        }
    }
}