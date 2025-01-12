
package com.example.mytask

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.mytask.ui.theme.MyTaskTheme
import java.text.SimpleDateFormat
import java.util.*

data class Note(
    val title: String,
    val content: String,
    var isCompleted: MutableState<Boolean> = mutableStateOf(false),
    val date: Long // Сохраняем дату создания заметки
)

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var notes by mutableStateOf(mutableListOf<Note>()) // Переместили сюда

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("note_app", Context.MODE_PRIVATE)

        setContent {
            MyTaskTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NoteScreen()
                }
            }
        }
    }

    @Composable
    fun NoteScreen() {
        var noteTitle by remember { mutableStateOf("") }
        var noteContent by remember { mutableStateOf("") }
        var noteDate by remember { mutableStateOf(System.currentTimeMillis()) }
        var editingNoteIndex by remember { mutableStateOf(-1) }
        var showDialog by remember { mutableStateOf(false) }
        var selectedNote by remember { mutableStateOf<Note?>(null) }

        // Загружаем заметки при первом запуске
        LaunchedEffect(Unit) {
            notes = getSavedNotes().toMutableList()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TextField(
                value = noteTitle,
                onValueChange = { noteTitle = it },
                label = { Text("Введите название заметки") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                label = { Text("Введите содержание заметки") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Default // Убираем Next, чтобы не мешать многострочному вводу
                ),
                maxLines = 5,
                modifier = Modifier.fillMaxHeight(0.5f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(noteDate)),
                onValueChange = {},
                label = { Text("Дата заметки") },
                readOnly = true,
                modifier = Modifier.clickable {
                    showDatePickerDialog { selectedDate ->
                        noteDate = selectedDate
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (noteTitle.isNotBlank() && noteContent.isNotBlank()) {
                    if (editingNoteIndex >= 0) {
                        // Обновляем существующую заметку
                        notes[editingNoteIndex] = Note(noteTitle, noteContent, notes[editingNoteIndex].isCompleted, noteDate)
                    } else {
                        // Добавляем новую заметку
                        notes.add(Note(noteTitle, noteContent, mutableStateOf(false), noteDate))
                    }
                    saveNotes(notes)
                    noteTitle = ""
                    noteContent = ""
                    noteDate = System.currentTimeMillis()
                    editingNoteIndex = -1 // Сбрасываем индекс редактирования после обновления
                }
            }) {
                Text(if (editingNoteIndex >= 0) "Обновить" else "Сохранить")
            }

            Spacer(modifier = Modifier.height(16.dp))

            val completedCount = notes.count { it.isCompleted.value }
            Text("Выполнено заметок: $completedCount из ${notes.size}")

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(notes.sortedBy { it.date }) { note ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = note.isCompleted.value,
                            onCheckedChange = { isChecked ->
                                note.isCompleted.value = isChecked
                                saveNotes(notes)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${note.title} (${formatDate(note.date)})",
                            modifier = Modifier.clickable {
                                selectedNote = note
                                showDialog = true
                            }
                        )
                    }
                }
            }

            if (showDialog && selectedNote != null) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Заметка") },
                    text = {
                        Column {
                            Text("Название: ${selectedNote!!.title}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Содержание: ${selectedNote!!.content}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Дата: ${formatDate(selectedNote!!.date)}")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            editingNoteIndex = notes.indexOf(selectedNote)
                            noteTitle = selectedNote!!.title
                            noteContent = selectedNote!!.content
                            noteDate = selectedNote!!.date 
                            showDialog = false
                        }) {
                            Text("Редактировать")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            deleteNote(selectedNote!!)
                            showDialog = false
                        }) {
                            Text("Удалить")
                        }
                    }
                )
            }
        }
    }

    private fun formatDate(dateInMillis: Long): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(dateInMillis))
    }

    private fun getSavedNotes(): List<Note> {
        val notesListString = sharedPreferences.getString("notes", "") ?: return emptyList()
        return notesListString.split(";").mapNotNull { noteString ->
            val parts = noteString.split(",")
            if (parts.size == 4) {
                val title = parts[0]
                val content = parts[1]
                val isCompleted = parts[2].toBoolean()
                val date = parts[3].toLongOrNull() ?: return@mapNotNull null
                Note(title, content, mutableStateOf(isCompleted), date)
            } else null
        }
    }

    private fun saveNotes(notes: List<Note>) {
        val notesString = notes.joinToString(";") { note ->
            "${note.title},${note.content},${note.isCompleted.value},${note.date}"
        }
        sharedPreferences.edit {
            putString("notes", notesString)
        }
    }

    private fun showDatePickerDialog(onDateSelected: (Long) -> Unit) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }.timeInMillis
                onDateSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun deleteNote(note: Note) {
        // Удаляем заметку из списка и обновляем сохраненные данные
        notes.remove(note)
        saveNotes(notes)
    }
}
