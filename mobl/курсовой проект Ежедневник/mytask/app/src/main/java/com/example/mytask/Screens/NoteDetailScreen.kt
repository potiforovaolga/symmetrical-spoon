package com.example.mytask.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mytask.models.Note
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun NoteDetailScreen(
    noteId: String,
    notes: List<Note>,
    onDelete: (String) -> Unit,
    navController: NavController
) {
    val note = notes.find { it.id == noteId }
    if (note != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Категория: ${note.tag}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            val formattedDateTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(note.date))
            Text(
                text = "Дата и время: $formattedDateTime",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = if (note.isCompleted.value) "Заметка выполнена" else "Заметка не выполнена",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigate("edit_note/${note.id}") }) {
                Text("Редактировать")
            }
            Button(
                onClick = {
                    onDelete(note.id)
                    navController.popBackStack("note_list", false)
                }
            ) {
                Text("Удалить")
            }
        }
    } else {
        Text("Заметка не найдена")
    }
}