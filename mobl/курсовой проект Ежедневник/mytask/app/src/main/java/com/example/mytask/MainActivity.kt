package com.example.mytask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.mytask.models.Note
import com.example.mytask.screens.*
import com.example.mytask.ui.theme.MyTaskTheme
import com.example.mytask.utils.scheduleNotification
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("note_app", Context.MODE_PRIVATE)

        setContent {
            MyTaskTheme {
                var notes by remember { mutableStateOf(loadNotes()) }
                var selectedCategory by remember { mutableStateOf("Все") }
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = { Text("Список заметок") },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                                    Icon(Icons.Filled.Menu, contentDescription = "Открыть меню")
                                }
                            }
                        )
                    },
                    drawerContent = {
                        DrawerContent(
                            categories = listOf("Все", "Учеба", "Работа", "Дом", "Хобби", "Прочие", "Выполненные", "Невыполненные"),
                            onCategorySelected = { category ->
                                selectedCategory = category
                                scope.launch { scaffoldState.drawerState.close() }
                            }
                        )
                    }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = "note_list",
                        modifier = Modifier.padding(padding)
                    ) {
                        // Экран списка заметок
                        composable("note_list") {
                            NoteListScreen(
                                notes = notes.filter {
                                    when (selectedCategory) {
                                        "Все" -> true
                                        "Выполненные" -> it.isCompleted.value
                                        "Невыполненные" -> !it.isCompleted.value
                                        else -> it.tag == selectedCategory
                                    }
                                },
                                onNotesUpdated = { updatedNotes ->
                                    notes = updatedNotes
                                    saveNotes(updatedNotes)
                                },
                                onDelete = { noteId ->
                                    notes = notes.filterNot { it.id == noteId }
                                    saveNotes(notes)
                                },
                                navController = navController
                            )
                        }

                        // Экран деталей заметки
                        composable(
                            "note_detail/{noteId}",
                            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getString("noteId") ?: return@composable
                            NoteDetailScreen(
                                noteId = noteId,
                                notes = notes,
                                onDelete = { id ->
                                    notes = notes.filterNot { it.id == id }
                                    saveNotes(notes)
                                    navController.popBackStack("note_list", false)
                                },
                                navController = navController
                            )
                        }

// Экран создания новой заметки
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

                        // Экран редактирования заметки
                        composable(
                            "edit_note/{noteId}",
                            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
                        ) { backStackEntry ->
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

@Composable
fun DrawerContent(categories: List<String>, onCategorySelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    ) {
        categories.forEach { category ->
            Text(
                text = category,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategorySelected(category) }
                    .padding(8.dp)
            )
        }
    }
}
