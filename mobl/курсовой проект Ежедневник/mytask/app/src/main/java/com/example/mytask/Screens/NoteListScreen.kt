package com.example.mytask.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mytask.models.Note
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteListScreen(
    notes: List<Note>,
    onNotesUpdated: (List<Note>) -> Unit,
    onDelete: (String) -> Unit,
    navController: NavController
) {
    var completedCount by remember { mutableStateOf(0) }

    // Подсчет выполненных заметок
    LaunchedEffect(notes) {
        completedCount = notes.count { it.isCompleted.value }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        // Заголовок
        Text(
            text = "Список заметок",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Подсчет выполненных задач
        Text(
            text = "Выполнено заметок: $completedCount из ${notes.size}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Список заметок
        notes.forEach { note ->
            NoteItem(
                note = note,
                onClick = { navController.navigate("note_detail/${note.id}") },
                onDelete = onDelete
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка добавления заметки
        Button(onClick = { navController.navigate("add_note") }) {
            Text("Добавить заметку")
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onClick: () -> Unit,
    onDelete: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Название, дата и время заметки
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Дата: ${
                    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(note.date))
                }",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Чекбокс выполненности
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = note.isCompleted.value,
                    onCheckedChange = { isChecked ->
                        note.isCompleted.value = isChecked
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (note.isCompleted.value) "Выполнено" else "Не выполнено",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}
