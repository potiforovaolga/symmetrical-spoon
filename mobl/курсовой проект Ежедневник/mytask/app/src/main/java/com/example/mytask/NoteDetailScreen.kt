
package com.example.mytask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteDetailScreen(
    noteId: String,
    navController: NavController,
    notes: List<Note>,
    onNotesUpdated: (List<Note>) -> Unit
) {
    val note = notes.find { it.id == noteId }

    if (note != null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = note.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Содержание: ${note.content}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Дата: ${
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(note.date))
                }",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Время: ${
                    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(note.date))
                }",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Тег: ${note.tag}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(onClick = {
                    navController.navigate("edit_note/$noteId") // Переход к экрану редактирования заметки
                }) {
                    Text("Редактировать")
                }
                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = {
                    onNotesUpdated(notes.filter { it.id != note.id }) // Удаление заметки
                    navController.popBackStack() // Возврат на предыдущий экран
                }) {
                    Text("Удалить")
                }
            }
        }
    } else {
        Text("Заметка не найдена", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun EditNoteScreen(
    noteId: String,
    navController: NavController,
    notes: List<Note>,
    onNotesUpdated: (List<Note>) -> Unit
) {
    val note = notes.find { it.id == noteId }

    var noteTitle by remember { mutableStateOf(note?.title ?: "") }
    var noteContent by remember { mutableStateOf(note?.content ?: "") }
    var noteTag by remember { mutableStateOf(note?.tag ?: "") }
    var noteDate by remember { mutableStateOf(note?.date ?: System.currentTimeMillis()) }
    var showError by remember { mutableStateOf(false) }

    if (note != null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Редактировать заметку", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = noteTitle,
                onValueChange = { noteTitle = it },
                label = { Text("Введите название заметки") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next // Устанавливаем действие клавиатуры на "Next"
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                label = { Text("ВВедите содержание заметки") },
                modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = noteTag,
                onValueChange = { noteTag = it },
                label = { Text("Тег заметки") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Отображение ошибки, если поля пустые
            if (showError) {
                Text(
                    text = "Все поля должны быть заполнены",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Кнопка сохранения изменений
            Button(onClick = {
                if (noteTitle.isBlank() || noteContent.isBlank() || noteTag.isBlank()) {
                    showError = true
                } else {
                    showError = false
                    val updatedNote = note.copy(
                        title = noteTitle,
                        content = noteContent,
                        tag = noteTag,
                        date = noteDate // Обновляем дату заметки, если нужно
                    )
                    onNotesUpdated(notes.map { if (it.id == updatedNote.id) updatedNote else it })

                    navController.popBackStack()
                }
            }) {
                Text("Сохранить")
            }
        }
    } else {
        Text("Заметка не найдена", style = MaterialTheme.typography.bodyMedium)
    }
}

