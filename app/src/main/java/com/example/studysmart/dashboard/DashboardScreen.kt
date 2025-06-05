package com.example.studysmart.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.studysmart.R
import com.example.studysmart.components.CountCard
import com.example.studysmart.components.SubjectCard
import com.example.studysmart.components.studySessionList
import com.example.studysmart.components.tasksList
import com.example.studysmart.domain.model.Session
import com.example.studysmart.domain.model.Subject
import com.example.studysmart.domain.model.Task
import com.example.studysmart.ui.theme.gradient2

@Composable
fun DashboardScreen() {
    val subjectList= listOf(
        Subject(subjectId = 0, name = "English", 12f, Subject.subjectColors[0]),
        Subject(subjectId = 0, name = "Computer", 12f, Subject.subjectColors[1]),
        Subject(subjectId = 0, name = "Maths", 12f, Subject.subjectColors[2]),
        Subject(subjectId = 0, name = "Hindi", 12f, Subject.subjectColors[3]),
    )

    val listOfTask = listOf(
        Task(
            taskSubjectId = 0,
            taskId = 1,
            title = "Computer Tasks",
            description = "",
            dueDate = 10,
            priority = 1,
            relatedToSubject = "Computer",
            isComplete = true
        ),
        Task(
            taskSubjectId = 0,
            taskId = 1,
            title = "Maths Tasks",
            description = "",
            dueDate = 10,
            priority = 1,
            relatedToSubject = "Maths",
            isComplete = true
        ),
        Task(
            taskSubjectId = 0,
            taskId = 1,
            title = "Science Tasks",
            description = "",
            dueDate = 20 / 2 / 2025,
            priority = 2,
            relatedToSubject = "Science",
            isComplete = false
        ),
        Task(
            taskSubjectId = 0,
            taskId = 1,
            title = "Science Tasks",
            description = "",
            dueDate = 2022025,
            priority = 3,
            relatedToSubject = "Science",
            isComplete = false
        )
    )

    val sessionList = listOf(
        Session(
            sessionSubjectId = 1,
            relatedToSubject = "Maths",
            date = 10L,
            duration = 20,
            subjectId = 1
        )
    )

    Scaffold(topBar = {
        DashboardScreenTopBar()
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            item {
                CountCardsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    subjectCount = 5,
                    studiedHours = "10",
                    goalHours = "15"
                )
            }
            item {
                SubjectCardsSection(
                    Modifier.fillMaxWidth(),
                    subjectList = subjectList
                )
            }
            item {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text(text = "Start Study Session", textAlign = TextAlign.Center)
                }
            }
            tasksList(
                sectionTitle = "UPCOMING TASKS",
                tasks = listOfTask,
                onCheckBoxClick = {},
                onTaskCardClick = {})

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            studySessionList(
                sectionTitle = "RECENT STUDY SESSIONS",
                sessionList = sessionList,
                onDeleteIconClick = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopBar() {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "StudySmart",
            style = MaterialTheme.typography.headlineMedium
        )
    })
}

@Composable
private fun CountCardsSection(
    modifier: Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalHours: String
) {
    Row(modifier = modifier) {
        CountCard(
            headingText = "Subject Count",
            count = "$subjectCount",
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(10.dp))
        CountCard(
            headingText = "Studied hours",
            count = studiedHours,
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(10.dp))
        CountCard(
            headingText = "Goal Study hours",
            count = goalHours,
            modifier = Modifier.weight(1f)
        )

    }
}

@Composable
private fun SubjectCardsSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any subjects.\n Click the + button to add new subjects."
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "SUBJECTS",
                Modifier.padding(start = 12.dp),
                style = MaterialTheme.typography.bodySmall
            )

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Subject",
                    Modifier.padding(end = 10.dp)
                )
            }
        }
        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.img_books),
                contentDescription = "Books"
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emptyListText,
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) { subject ->
                SubjectCard(
                    subjectName = subject.name,
                    gradientColors = subject.colors,
                    onClick = {}
                )
            }
        }

    }
}