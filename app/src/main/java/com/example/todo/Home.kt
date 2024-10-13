package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)


        // Find the button by its ID
        val buttontodo: Button = findViewById(R.id.TODO)

        // Set an OnClickListener on the button
        buttontodo.setOnClickListener {
            // Create an Intent to start the next activity
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)


        }


        // Find the button by its ID
        val buttonStopwatch: Button = findViewById(R.id.timer)

        // Set an OnClickListener on the button
        buttonStopwatch.setOnClickListener {
            // Create an Intent to start the next activity
            val intent = Intent(this, stopWatch::class.java)
            startActivity(intent)
        }







    }
}