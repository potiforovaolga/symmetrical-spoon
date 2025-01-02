
package com.example.mytask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
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

data class Note(val title: String, val content: String, var isCompleted: Boolean = false)

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

            TextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                label = { Text("Введите содержание заметки") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (noteTitle.isNotBlank() && noteContent.isNotBlank()) {
                            saveOrUpdateNote(
                                Note(noteTitle, noteContent),
                                editingNoteIndex,
                                notes,
                                { updatedNotes -> notes = updatedNotes },
                                { index -> editingNoteIndex = index }
                            )
                            noteTitle = ""
                            noteContent = ""
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (noteTitle.isNotBlank() && noteContent.isNotBlank()) {
                    saveOrUpdateNote(

                        Note(noteTitle, noteContent),
                        editingNoteIndex,
                        notes,
                        { updatedNotes -> notes = updatedNotes },
                        { index -> editingNoteIndex = index }
                    )
                    noteTitle = ""
                    noteContent = ""
                }
            }) {
                Text(if (editingNoteIndex >= 0) "Обновить" else "Сохранить")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Подсчет выполненных заметок
            val completedCount = notes.count { it.isCompleted }
            Text(text = "Выполнено заметок: $completedCount из ${notes.size}")

            LazyColumn {
                items(notes) { note ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = note.isCompleted,
                            onCheckedChange = { isChecked ->
                                note.isCompleted = isChecked // Обновляем состояние заметки
                                saveNotes(notes) // Сохраняем изменения в SharedPreferences
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = note.title,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    selectedNote = note
                                    showDialog = true // Показываем диалоговое окно
                                }
                        )
                    }
                }
            }
        }

        // Диалоговое окно для редактирования и удаления заметки
        if (showDialog && selectedNote != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Заметка") },
                text = {
                    Column {
                        Text("Название: ${selectedNote!!.title}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Содержание: ${selectedNote!!.content}")
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        editingNoteIndex = notes.indexOf(selectedNote)
                        noteTitle = selectedNote!!.title
                        noteContent = selectedNote!!.content
                        showDialog = false // Закрываем диалог
                    }) {
                        Text("Редактировать")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        notes.remove(selectedNote)
                        saveNotes(notes) // Сохраняем изменения после удаления
                        showDialog = false // Закрываем диалог
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
            if (title != null && content != null) {
                savedNotes.add(Note(title, content, isCompleted))
            }
        }
        return savedNotes
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
        saveNotes(currentNotes) // Сохраняем обновленный список заметок в SharedPreferences
        updateEditingIndex(-1) // Сброс индекса редактирования
    }

    private fun saveNotes(notes: List<Note>) {
        sharedPreferences.edit {
            putInt("notes_count", notes.size) // Сохраняем количество заметок
            for (i in notes.indices) {
                putString("note_title_$i", notes[i].title)
                putString("note_content_$i", notes[i].content)
                putBoolean("note_completed_$i", notes[i].isCompleted) // Сохраняем статус выполнения заметки
            }
        }
    }
}

