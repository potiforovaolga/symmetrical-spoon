package com.example.mytask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.mytask.ui.theme.MyTaskTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("note_app", Context.MODE_PRIVATE)

        setContent {
            MyTaskTheme {
                var notes by remember { mutableStateOf(loadNotes()) }
                var selectedTag by remember { mutableStateOf("") }

                val navController = rememberNavController()
                NavHost(navController, startDestination = "note_list") {
                    composable("note_list") {
                        val filteredNotes = if (selectedTag.isNotEmpty()) {
                            notes.filter { it.tag == selectedTag }
                        } else {
                            notes
                        }

                        NoteListScreen(
                            notes = filteredNotes,
                            onNotesUpdated = { updatedNotes ->
                                notes = updatedNotes
                                saveNotes(updatedNotes)
                            },
                            onTagSelected = { tag ->
                                selectedTag = tag
                            },
                            navController = navController
                        )
                    }
                    composable("add_note") {
                        NoteScreen(
                            notes = notes,
                            onNotesUpdated = { updatedNotes ->
                                notes = updatedNotes
                                saveNotes(updatedNotes)
                            },
                            context = this@MainActivity,
                            navController = navController
                        )
                    }
                    composable("note_detail/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId") ?: return@composable
                        NoteDetailScreen(
                            noteId = noteId,
                            navController = navController,
                            notes = notes,
                            onNotesUpdated = { updatedNotes ->
                                notes = updatedNotes
                                saveNotes(updatedNotes)
                            }
                        )
                    }
                    composable("edit_note/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId") ?: return@composable
                        EditNoteScreen(
                            noteId = noteId,
                            navController = navController,
                            notes = notes,
                            onNotesUpdated = { updatedNotes ->
                                notes = updatedNotes
                                saveNotes(updatedNotes)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun saveNotes(notes: List<Note>) {
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(notes)
        editor.putString("notes", json)
        editor.apply()
    }

    private fun loadNotes(): List<Note> {
        val json = sharedPreferences.getString("notes", null)
        return if (json != null) {
            Gson().fromJson(json, Array<Note>::class.java).toList()
        } else {
            emptyList()
        }
    }
}

