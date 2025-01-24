package com.example.mytask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytask.models.Note
import com.example.mytask.screens.NoteDetailScreen
import com.example.mytask.screens.NoteListScreen
import com.example.mytask.screens.NoteScreen
import com.example.mytask.screens.EditNoteScreen
import com.example.mytask.ui.theme.MyTaskTheme
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("note_app", Context.MODE_PRIVATE)

        setContent {
            MyTaskTheme {
                var notes by remember { mutableStateOf(loadNo
