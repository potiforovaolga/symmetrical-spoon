
package com.example.mytask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteListScreen(
    notes: List<Note>,
    onNotesUpdated: (List<Note>) -> Unit,
    onTagSelected: (String) -> Unit, // Добавляем параметр onTagSelected
    navController: NavController
) {
    // Состояние для выбранного тега
    var selectedTag by remember { mutableStateOf("") }

    // Фильтрация заметок по выбранному тегу
    val filteredNotes = if (selectedTag.isEmpty()) notes else notes.filter { it.tag == selectedTag }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Список заметок", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Заголовок для фильтрации по тегам
        Text(text = "Фильтр по тегу:")
        Row { // Горизонтальный контейнер для кнопок фильтрации
            // Кнопка для сброса фильтра (показать все заметки)
            Button(onClick = { 
                selectedTag = "" 
                onTagSelected("") // Сбрасываем выбранный тег в MainActivity
            }) {
                Text("Все")
            }
            // Генерация кнопок для каждого уникального тега из списка заметок
            notes.map { it.tag }.distinct().forEach { tag ->
                Spacer(modifier = Modifier.width(8.dp)) // Отступ между кнопками тегов
                Button(onClick = { 
                    selectedTag = tag 
                    onTagSelected(tag) // Устанавливаем выбранный тег в MainActivity
                }) {
                    Text(tag) // Отображение названия тега на кнопке
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Отступ перед списком заметок

        // Отображение каждой заметки в виде элемента списка
        for (note in filteredNotes) {
            NoteItem(note) { // Вызов компонента NoteItem для каждой заметки
                navController.navigate("note_detail/${note.id}") // Навигация к экрану деталей заметки по ID
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Занимает оставшееся пространство

        // Кнопка для добавления новой заметки
        Button(onClick = { navController.navigate("add_note") }) {
            Text("Добавить заметку") // Текст на кнопке
        }
    }
}

// Компонент для отображения отдельной заметки
@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick), // Карточка занимает всю ширину с отступами и кликабельностью
        elevation = CardDefaults.cardElevation(4.dp) // Тень карточки
    ) {
        Column(modifier = Modifier.padding(16.dp)) { // Вертикальный контейнер для содержимого карточки
            Text(text = note.title, style = MaterialTheme.typography.titleMedium) // Заголовок заметки
            Text(
                text = "Дата: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(note.date))}",
                style = MaterialTheme.typography.bodySmall // Форматирование даты создания заметки
            )
            Text(
                text = "Время: ${SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(note.time))}",
                style = MaterialTheme.typography.bodySmall // Форматирование времени создания заметки
            )
        }
    }
}

