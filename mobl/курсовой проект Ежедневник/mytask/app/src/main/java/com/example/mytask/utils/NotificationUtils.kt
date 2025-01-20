package com.example.mytask.utils

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mytask.NotificationWorker
import java.util.concurrent.TimeUnit

fun scheduleNotification(context: Context, title: String, id: String, dateInMillis: Long) {
    val currentTime = System.currentTimeMillis()
    val delay = dateInMillis - currentTime

    if (delay <= 0) {
        Log.e("ScheduleNotification", "Дата уведомления в прошлом. Уведомление не запланировано.")
        return
    }

    val inputData = Data.Builder()
        .putString("note_title", title)
        .putString("note_id", id)
        .build()

    val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .setInputData(inputData)
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
    Log.d("ScheduleNotification", "Задача уведомления запланирована. Заголовок: $title")
}
