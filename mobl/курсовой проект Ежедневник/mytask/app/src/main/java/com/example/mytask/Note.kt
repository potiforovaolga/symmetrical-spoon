package com.example.mytask

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.*

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    var isCompleted: MutableState<Boolean> = mutableStateOf(false),
    val date: Long, // Дата в миллисекундах
    val time: Long, 
    var tag: String
)