package com.example.civa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar

class MainActivity : AppCompatActivity() {
    private lateinit var button:Button
    private lateinit var editText: EditText
    private lateinit var editText1: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        button = findViewById(R.id.button)
        editText = findViewById(R.id.editTextTextPersonName)
        editText1 = findViewById(R.id.editTextTextPersonName2)

        button.setOnClickListener {
            if(editText.text.isEmpty() && editText1.text.isEmpty()){
                editText.error = "შეიყვანეთ განზომილებები"
                return@setOnClickListener
            }
            if((editText.text.toString() != editText1.text.toString())) {
                editText.error = "მატრიცა კვადრატული არაა"
                return@setOnClickListener
            }
            if(editText.text.toString().toInt() > 6){
                editText.error = "მაქსიმუმი განზომილებაა 6"
                return@setOnClickListener
            }
            if(editText.text.toString().toInt() < 2){
                editText.error = "შეიყვანეთ სწორი განზომილებები"
                return@setOnClickListener
            }
            val text = editText.text.toString()
            val intent = Intent(this, MainActivity4::class.java)
            intent.putExtra("N", text)
            startActivity(intent)
        }
    }

}