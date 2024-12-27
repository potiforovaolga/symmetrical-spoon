
package com.example.mytask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import example.mytask.ui.theme.MyTaskTheme

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
        var note by remember { mutableStateOf(getSavedNote()) }
        var isSaved by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = note,
                onValueChange = {
                    note = it
                    isSaved = false // Сбрасываем статус сохранения при изменении текста
                },
                label = { Text("Введите вашу заметку") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                saveNote(note)
                isSaved = true // Устанавливаем статус сохранения
            }) {
                Text("Сохранить заметку")
            }
            if (isSaved) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Заметка сохранена!", color = MaterialTheme.colorScheme.primary)
            }
        }
    }

    private fun saveNote(note: String) {
        Log.d("MainActivity", "Saving note: $note")
        sharedPreferences.edit { putString("note_key", note) }
    }

    private fun getSavedNote(): String {
        val savedNote = sharedPreferences.getString("note_key", "")
        Log.d("MainActivity", "Retrieved note: $savedNote")
        return savedNote ?: ""
    }
}
