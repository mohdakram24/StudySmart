package com.example.studysmart.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DeleteDialog(
    isOpen: Boolean,
    title: String,
    bodyText: String,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = title)
            },
            // This Text is for the body of the Dialog box
            text = {
                Text(text = bodyText)
            },
            confirmButton = {
                TextButton(onClick = onConfirmButtonClick) {
                    Text(
                        text = "Delete"
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        )
    }

}