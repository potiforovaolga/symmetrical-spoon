
package com.example.mytask
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.example.mytask.utils.scheduleNotification
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
    var noteTag by remember { mutableStateOf("") }
    var noteDateTime by remember { mutableStateOf(System.currentTimeMillis()) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Создать заметку", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Поле для ввода названия заметки с настройками клавиатуры
        TextField(
            value = noteTitle,
            onValueChange = { noteTitle = it },
            label = { Text("Введите название заметки") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next // Устанавливаем действие клавиатуры на "Next"
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Поле для ввода содержания заметки
        TextField(
            value = noteContent,
            onValueChange = { noteContent = it },
            label = { Text("Введите содержание заметки") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp),
            maxLines = 5
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Поле для выбора тега заметки
        TextField(
            value = noteTag,
            onValueChange = { noteTag = it },
            label = { Text("Тег заметки") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Поле для выбора даты и времени заметки
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

        // Отображение ошибки, если поля пустые
        if (showError) {
            Text(
                text = "Все поля должны быть заполнены",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Кнопка сохранения заметки
        Button(onClick = {
            if (noteTitle.isBlank() || noteContent.isBlank() || noteTag.isBlank()) {
                showError = true
            } else {
                showError = false
                val newNote = Note(
                    title = noteTitle,
                    content = noteContent,
                    date = noteDateTime,
                    time = noteDateTime, // Сохраняем дату и время
                    tag = noteTag
                )
                onNotesUpdated(notes + newNote)

                // Запланировать уведомление
                scheduleNotification(context, noteTitle, newNote.id, noteDateTime)
                navController.popBackStack()
            }
        }) {
            Text("Сохранить")
        }
    }
}

