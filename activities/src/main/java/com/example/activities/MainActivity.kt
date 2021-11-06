package com.example.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var clickButton: Button
    private lateinit var computeButton: Button
    private var nbClick = 0
    private lateinit var textViewNbClick: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clickButton = findViewById(R.id.btn_click_me)
        computeButton = findViewById(R.id.btn_compute)
        textViewNbClick = findViewById(R.id.text_view_nb_click)
        clickButton.setOnClickListener {
            nbClick++
            val newText = "Cliquez moi $nbClick"
            if (nbClick >= 5) {
                clickButton.setClickable(false)
                clickButton.setEnabled(false)
            }
            clickButton.text = "Vous avez cliquez $nbClick fois"
        }
        computeButton.setOnClickListener {
            val intent = Intent(baseContext, ComputeActivity::class.java)
            startActivity(intent)

        }


    }

}