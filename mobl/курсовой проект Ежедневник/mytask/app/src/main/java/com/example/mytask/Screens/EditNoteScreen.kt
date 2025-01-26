package com.example.mytask.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.mytask.models.Note
import com.example.mytask.utils.showCustomDateTimePickerDialog
import com.example.mytask.utils.scheduleNotification
import java.text.SimpleDateFormat
import java.util.*

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
    var selectedCategory by remember { mutableStateOf(note?.tag ?: "Прочие") }
    var noteDateTime by remember { mutableStateOf(note?.date ?: System.currentTimeMillis()) }
    var isCompleted by remember { mutableStateOf(note?.isCompleted?.value ?: false) }
    var showCategoryDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val categories = listOf("Учеба", "Работа", "Дом", "Хобби", "Прочие")

    if (note != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Редактировать заметку", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = noteTitle,
                onValueChange = { noteTitle = it },
                label = { Text("Название заметки") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                label = { Text("Содержание заметки") },
                modifier = Modifier.fillMaxWidth()
       .fillMaxWidth()
                .heightIn(min = 100.dp),
            maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { showCategoryDialog = true }) {
                Text(if (selectedCategory.isBlank()) "Выбрать категорию" else "Категория: $selectedCategory")
            }

            if (showCategoryDialog) {
                AlertDialog(
                    onDismissRequest = { showCategoryDialog = false },
                    title = { Text("Выберите категорию") },
                    text = {
                        Column {
                            categories.forEach { category ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedCategory = category
                                            showCategoryDialog = false
                                        }
                                        .padding(8.dp)
                                ) {
                                    RadioButton(selected = selectedCategory == category, onClick = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(category)
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(onClick = { showCategoryDialog = false }) {
                            Text("Закрыть")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(noteDateTime)),
                onValueChange = {},
                label = { Text("Дата и время напоминания о заметки") },
                readOnly = true,
                modifier = Modifier.clickable {
                    showCustomDateTimePickerDialog(context) { selectedDateTime ->
                        noteDateTime = selectedDateTime
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val updatedNote = note.copy(
                    title = noteTitle,
                    content = noteContent,
                    tag = selectedCategory,
                    date = noteDateTime,
                    isCompleted = mutableStateOf(isCompleted)
                )
                onNotesUpdated(notes.map { if (it.id == note.id) updatedNote else it })
                scheduleNotification(context, noteTitle, note.id, noteDateTime)
                navController.popBackStack()
            }) {
                Text("Сохранить изменения")
            }
        }
    }
}
