package com.example.mytask.screens

import androidx.compose.foundation.text.KeyboardOptions
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mytask.models.Note
import com.example.mytask.utils.scheduleNotification
import com.example.mytask.utils.showCustomDateTimePickerDialog
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteScreen(
    notes: List<Note>,
    onNotesUpdated: (List<Note>) -> Unit,
    context: Context,
    navController: NavController
) {
    var noteTitle by remember { mutableStateOf("") }
    var noteContent by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Прочие") }
    var noteDateTime by remember { mutableStateOf(System.currentTimeMillis()) }
    val categories = listOf("Учеба", "Работа", "Дом", "Хобби", "Прочие")
    var showCategoryDialog by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Создать заметку", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = noteTitle,
            onValueChange = { noteTitle = it },
            label = { Text("Введите название заметки") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = noteContent,
            onValueChange = { noteContent = it },
            label = { Text("Введите содержание заметки") },
            modifier = Modifier.fillMaxWidth()
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

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(noteDateTime)),
            onValueChange = {},
            label = { Text("Дата и время заметки") },
            readOnly = true,
            modifier = Modifier.clickable {
                showCustomDateTimePickerDialog(context) { selectedDateTime ->
                    noteDateTime = selectedDateTime
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (noteTitle.isBlank() || noteContent.isBlank() || selectedCategory.isBlank()) {
                showError = true
            } else {
                showError = false
                val newNote = Note(
                    title = noteTitle,
                    content = noteContent,
                    tag = selectedCategory,
                    date = noteDateTime,
                    time = noteDateTime,
                    isCompleted = mutableStateOf(false)
                )
                onNotesUpdated(notes + newNote)
                scheduleNotification(context, noteTitle, newNote.id, noteDateTime)
                navController.popBackStack()
            }
        }) {
            Text("Сохранить заметку")
        }

        if (showError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Все поля должны быть заполнены", color = MaterialTheme.colorScheme.error)
        }
    }
}
