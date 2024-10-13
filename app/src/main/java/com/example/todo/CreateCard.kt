package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.room.Room
import com.example.todo.databinding.ActivityCreateCardBinding // Import ViewBinding class generated for your layout file
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateCard : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCardBinding // Declare ViewBinding variable
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCardBinding.inflate(layoutInflater) // Inflate layout using ViewBinding
        setContentView(binding.root)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()

        binding.saveButton.setOnClickListener { // Access views through ViewBinding variable
            if (binding.createTitle.text.toString().trim { it <= ' ' }.isNotEmpty()
                && binding.createPriority.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {
                val title = binding.createTitle.text.toString()
                val priority = binding.createPriority.text.toString()
                DataObject.setData(title, priority)
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title, priority))
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        // Find imageView6 by its ID
        val backButton: ImageView = findViewById(R.id.imageView6)

        // Set an onClickListener to navigate to the home screen
        backButton.setOnClickListener {
            // Create an intent to navigate to HomeActivity (replace HomeActivity with your home screen activity)
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
        // Find the button by its ID
        val AllTask: Button = findViewById(R.id.All_Tasks)

        // Set an OnClickListener on the button
        AllTask.setOnClickListener {
            // Create an Intent to start the next activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }
}
