package com.example.todo

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Reminder : AppCompatActivity() {

    private lateinit var titleText: TextView
    private lateinit var taskEditText: EditText
    private lateinit var timeEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var addReminderButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reminder)

        titleText = findViewById(R.id.title_text)
        taskEditText = findViewById(R.id.create_reminder)
        timeEditText = findViewById(R.id.time)
        dateEditText = findViewById(R.id.date)
        addReminderButton = findViewById(R.id.add_reminderButton)


// Setting up date picker
        dateEditText.setOnClickListener {
            showDatePicker()
        }

        // Setting up time picker
        timeEditText.setOnClickListener {
            showTimePicker()
        }

        // Handling add reminder button click
        addReminderButton.setOnClickListener {
            val task = taskEditText.text.toString()
            val time = timeEditText.text.toString()
            val date = dateEditText.text.toString()

            if (task.isNotEmpty() && time.isNotEmpty() && date.isNotEmpty()) {
                Toast.makeText(this, "Reminder set for $task on $date at $time", Toast.LENGTH_LONG).show()
                // You can save the reminder or perform further actions here
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }



        val backArrow = findViewById<ImageView>(R.id.back_Arrow)

        backArrow.setOnClickListener {
            navigateBackToHomePage("Home")
        }



        // Create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "reminderChannel", "Reminder Channel", NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for Reminder Notifications"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }




    }

    private fun showDatePicker() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dateEditText.setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()


    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                timeEditText.setText(selectedTime)
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }


    private fun scheduleReminder(task: String, date: String, time: String) {
        val calendar = Calendar.getInstance()

        // Parse date and time from the user input
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val reminderDateTime = "$date $time"
        val reminderDate = dateFormat.parse(reminderDateTime)
        if (reminderDate == null) {
            Toast.makeText(this, "Invalid date or time", Toast.LENGTH_SHORT).show()
            return
        }

        // Set calendar to the selected date and time
        calendar.time = reminderDate



        // Get AlarmManager
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create intent for BroadcastReceiver


        // Create a PendingIntent
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set the alarm to trigger at the specified time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            Toast.makeText(this, "Selected time is in the past", Toast.LENGTH_SHORT).show()
            return
        }


        Log.d("Reminder", "Reminder set for: ${calendar.time}")


    }



    private fun navigateBackToHomePage(s: String) {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

}