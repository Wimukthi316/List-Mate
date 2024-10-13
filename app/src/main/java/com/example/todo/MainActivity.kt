package com.example.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.todo.databinding.ActivityMainBinding // Import ViewBinding class generated for your layout file
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Declare ViewBinding variable
    private lateinit var database: myDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inflate layout using ViewBinding
        setContentView(binding.root)
        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "To_Do"
        ).build()

        binding.add.setOnClickListener { // Access views through ViewBinding variable
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)
        }

        setRecycler()


        // Find the button by its ID
        val back: Button = findViewById(R.id.back)

        // Set an OnClickListener on the button
        back.setOnClickListener {
            // Create an Intent to start the next activity
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)

        }


    }

    private fun setRecycler() {
        binding.recyclerView.adapter = Adapter(DataObject.getAllData()) // Access recyclerView through ViewBinding variable
        binding.recyclerView.layoutManager = LinearLayoutManager(this) // Access recyclerView through ViewBinding variable
    }
}
