package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Screen2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_screen2)

// Find the button by its ID
        val buttonwelcome: Button = findViewById(R.id.WELCOME)

        // Set an OnClickListener on the button
        buttonwelcome.setOnClickListener {
            // Create an Intent to start the next activity
            val intent = Intent(this, Home::class.java)
            startActivity(intent)



        }








    }
}