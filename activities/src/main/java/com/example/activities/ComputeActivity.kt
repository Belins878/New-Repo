package com.example.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ComputeActivity : AppCompatActivity(), TextWatcher {
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var textView: TextView
    private lateinit var clickButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compute_activity)
        clickButton = findViewById(R.id.btn_calculer)
        editText1 = findViewById(R.id.field_1)
        editText2 = findViewById(R.id.field_2)
        textView = findViewById(R.id.resultat)

        clickButton.setOnClickListener {

            val a = editText1.text.toString().toIntOrNull() ?: 0
            val b = editText2.text.toString().toIntOrNull() ?: 0

            val resultat = "Le résultat est ${a + b}"
            textView.text = resultat
        }

        editText1.addTextChangedListener(this)
        editText2.addTextChangedListener(this)
    }

    private fun verifButton() {
        val field1 = editText1.text.isNotBlank()
        val field2 = editText2.text.isNotBlank()

        clickButton.isEnabled = field1 && field2

    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        verifButton()
    }

}
