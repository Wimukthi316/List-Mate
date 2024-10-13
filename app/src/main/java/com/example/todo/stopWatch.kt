package com.example.todo

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class stopWatch : AppCompatActivity() {

    private var timeSelected : Int = 0
    private var timeCountDown: CountDownTimer? = null
    private var timeProgress = 0
    private var pauseOffSet: Long = 0
    private var isStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_watch)

        val addBtn: ImageButton = findViewById(R.id.btnAdd)
        addBtn.setOnClickListener {
            setTimeFunction()
        }
        val startBtn:Button = findViewById(R.id.btnPlayPause)
        startBtn.setOnClickListener {
            startTimerSetup()
        }

        val resetBtn:ImageButton = findViewById(R.id.ib_reset)
        resetBtn.setOnClickListener {
            resetTime()
        }

        // Find the button by its ID
        val backArrow = findViewById<ImageView>(R.id.imageView8)

        backArrow.setOnClickListener {
            navigateBackToHome("Home")
        }

        // Set an OnClickListener on the button
        backArrow.setOnClickListener {
            // Create an Intent to start the next activity
            val intent = Intent(this, Home::class.java)
            startActivity(intent)

        }
    }

    private fun navigateBackToHome(s: String) {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)

    }

    private fun addExtraTime()
    {
        val progressBar :ProgressBar = findViewById(R.id.pbTimer)
        if (timeSelected!=0)
        {
            timeSelected+=15
            progressBar.max = timeSelected
            timePause()
            startTimer(pauseOffSet)
            Toast.makeText(this,"15 sec added",Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetTime()
    {
        if (timeCountDown!=null)
        {
            timeCountDown!!.cancel()
            timeProgress=0
            timeSelected=0
            pauseOffSet=0
            timeCountDown=null
            val startBtn:Button = findViewById(R.id.btnPlayPause)
            startBtn.text ="Start"
            isStart = true
            val progressBar = findViewById<ProgressBar>(R.id.pbTimer)
            progressBar.progress = 0
            val timeLeftTv: TextView = findViewById(R.id.tvTimeLeft)
            timeLeftTv.text = "0"
        }
    }

    private fun timePause()
    {
        if (timeCountDown!=null)
        {
            timeCountDown!!.cancel()
        }
    }

    private fun startTimerSetup()
    {
        val startBtn: Button = findViewById(R.id.btnPlayPause)
        if (timeSelected>timeProgress)
        {
            if (isStart)
            {
                startBtn.text = "Pause"
                startTimer(pauseOffSet)
                isStart = false
            }
            else
            {
                isStart =true
                startBtn.text = "Resume"
                timePause()
            }
        }
        else
        {
            Toast.makeText(this,"Enter Time",Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTimer(pauseOffSetL: Long)
    {
        val progressBar = findViewById<ProgressBar>(R.id.pbTimer)
        progressBar.progress = timeProgress
        timeCountDown = object :CountDownTimer(
            (timeSelected*1000).toLong() - pauseOffSetL*1000, 1000)
        {
            override fun onTick(p0: Long) {
                timeProgress++
                pauseOffSet = timeSelected.toLong()- p0/1000
                progressBar.progress = timeSelected-timeProgress
                val timeLeftTv:TextView = findViewById(R.id.tvTimeLeft)
                timeLeftTv.text = (timeSelected - timeProgress).toString()
            }

            override fun onFinish() {
                resetTime()
                Toast.makeText(this@stopWatch,"Times Up!", Toast.LENGTH_SHORT).show()
            }

        }.start()
    }


    private fun setTimeFunction()
    {
        val timeDialog = Dialog(this)
        timeDialog.setContentView(R.layout.add_dialog)
        val timeSet = timeDialog.findViewById<EditText>(R.id.etGetTime)
        val timeLeftTv: TextView = findViewById(R.id.tvTimeLeft)
        val btnStart: Button = findViewById(R.id.btnPlayPause)
        val progressBar = findViewById<ProgressBar>(R.id.pbTimer)
        timeDialog.findViewById<Button>(R.id.btnOk).setOnClickListener {
            if (timeSet.text.isEmpty())
            {
                Toast.makeText(this,"Enter Time Duration",Toast.LENGTH_SHORT).show()
            }
            else
            {
                resetTime()
                timeLeftTv.text = timeSet.text
                btnStart.text = "Start"
                timeSelected = timeSet.text.toString().toInt()
                progressBar.max = timeSelected
            }
            timeDialog.dismiss()
        }
        timeDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(timeCountDown!=null)
        {
            timeCountDown?.cancel()
            timeProgress=0
        }
    }

}