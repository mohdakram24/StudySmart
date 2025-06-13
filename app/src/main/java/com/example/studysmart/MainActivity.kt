package com.example.studysmart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.studysmart.domain.model.Session
import com.example.studysmart.domain.model.Subject
import com.example.studysmart.domain.model.Task
import com.example.studysmart.ui.theme.StudySmartTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudySmartTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

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
    ),
    Session(
        sessionSubjectId = 1,
        relatedToSubject = "Maths",
        date = 10L,
        duration = 20,
        subjectId = 1
    ),
    Session(
        sessionSubjectId = 1,
        relatedToSubject = "Maths",
        date = 10L,
        duration = 20,
        subjectId = 1
    )
)