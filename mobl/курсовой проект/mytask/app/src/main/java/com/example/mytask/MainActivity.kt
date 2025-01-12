
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
    var isCompleted: Boolean = false,
    val date: Long // Сохраняем дату создания заметки
)

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences

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
        var notes by remember { mutableStateOf(getSavedNotes()) }
        var editingNoteIndex by remember { mutableStateOf(-1) }
        var showDialog by remember { mutableStateOf(false) }
        var selectedNote by remember { mutableStateOf<Note?>(null) }

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

    // Изменение TextField для содержания заметки
    TextField(
        value = noteContent,
        onValueChange = { noteContent = it },
        label = { Text("Введите содержание заметки") },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Default // Убираем Next, чтобы не мешать многострочному вводу
        ),
        maxLines = 5, // Устанавливаем максимальное количество строк
        modifier = Modifier.fillMaxHeight(0.5f) // Задаем высоту, если нужно
    )

            Spacer(modifier = Modifier.height(8.dp))

            // Поле для выбора даты
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
                    saveOrUpdateNote(
                        Note(noteTitle, noteContent, date = noteDate),
                        editingNoteIndex,
                        notes,

                        { updatedNotes -> notes = updatedNotes },
                        { index -> editingNoteIndex = index }
                    )
                    noteTitle = ""
                    noteContent = ""
                    noteDate = System.currentTimeMillis() // Сброс даты после сохранения
                }
            }) {
                Text(if (editingNoteIndex >= 0) "Обновить" else "Сохранить")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Подсчет выполненных заметок
            val completedCount = notes.count { it.isCompleted }
            Text("Выполнено заметок: $completedCount из ${notes.size}")

            LazyColumn {
    items(notes.sortedBy { it.date }) { note ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = note.isCompleted,
                            onCheckedChange = { isChecked ->
                                note.isCompleted = isChecked
                                saveNotes(notes)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${note.title} (${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(note.date))})",
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    selectedNote = note
                                    showDialog = true
                                }
                        )
                    }
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
                        Text("Дата: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selectedNote!!.date))}")
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        editingNoteIndex = notes.indexOf(selectedNote)
                        noteTitle = selectedNote!!.title
                        noteContent = selectedNote!!.content
                        noteDate = selectedNote!!.date // Устанавливаем дату выбранной заметки для редактирования
                        showDialog = false
                    }) {
                        Text("Редактировать")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        notes.remove(selectedNote)
                        saveNotes(notes)
                        showDialog = false
                    }) {
                        Text("Удалить")
                    }
                }
            )
        }
    }

    private fun getSavedNotes(): MutableList<Note> {
        val savedNotes = mutableListOf<Note>()
        val notesCount = sharedPreferences.getInt("notes_count", 0)

        for (i in 0 until notesCount) {
            val title = sharedPreferences.getString("note_title_$i", null)
            val content = sharedPreferences.getString("note_content_$i", null)
            val isCompleted = sharedPreferences.getBoolean("note_completed_$i", false)
            val date = sharedPreferences.getLong("note_date_$i", System.currentTimeMillis())
            if (title != null && content != null) {
                savedNotes.add(Note(title, content, isCompleted, date))

            }
        }
        return savedNotes
    }

    private fun saveNotes(notes: List<Note>) {
        sharedPreferences.edit {
            putInt("notes_count", notes.size)
            notes.forEachIndexed { index, note ->
                putString("note_title_$index", note.title)
                putString("note_content_$index", note.content)
                putBoolean("note_completed_$index", note.isCompleted)
                putLong("note_date_$index", note.date)
            }
        }
    }

    private fun saveOrUpdateNote(
        note: Note,
        editingIndex: Int,
        currentNotes: MutableList<Note>,
        updateNotes: (MutableList<Note>) -> Unit,
        updateEditingIndex: (Int) -> Unit
    ) {
        if (editingIndex >= 0) {
            currentNotes[editingIndex] = note // Обновление существующей заметки
        } else {
            currentNotes.add(note) // Добавление новой заметки
        }

        updateNotes(currentNotes)
        saveNotes(currentNotes)
        updateEditingIndex(-1) // Сброс индекса редактирования после сохранения
    }

    private fun showDatePickerDialog(onDateSelected: (Long) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, selectedYear)
                set(Calendar.MONTH, selectedMonth)
                set(Calendar.DAY_OF_MONTH, selectedDay)
            }.timeInMillis

            onDateSelected(selectedDate)
        }, year, month, day).show()
    }
}

