package com.example.mytask

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            val noteTitle = inputData.getString("note_title") ?: "Заметка"
            val noteId = inputData.getString("note_id") ?: "0"

            sendNotification(noteTitle, noteId)
            Log.d("NotificationWorker", "Уведомление успешно отправлено: $noteTitle")
            Result.success()
        } catch (e: Exception) {
            Log.e("NotificationWorker", "Ошибка выполнения: ${e.message}", e)
            Result.failure()
        }
    }

    private fun sendNotification(title: String, noteId: String) {
        val channelId = "note_reminders"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Напоминания о заметках",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Напоминание о заметке")
            .setContentText("Время для: $title")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(noteId.hashCode(), notification)
    }
}
