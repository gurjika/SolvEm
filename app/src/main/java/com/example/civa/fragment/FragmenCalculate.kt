package com.example.civa.fragment

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.civa.MainActivity4
import com.example.civa.R

class FragmenCalculate: Fragment(R.layout.fragment_calculate) {
    private lateinit var button: Button
    private lateinit var editText: EditText
    private lateinit var editText1: EditText
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button = view.findViewById(R.id.button)
        editText = view.findViewById(R.id.editTextTextPersonName)
        editText1 = view.findViewById(R.id.editTextTextPersonName2)

        button.setOnClickListener {
            activity.let{
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
            val intent = Intent(activity, MainActivity4::class.java)
            intent.putExtra("N", text)
            startActivity(intent)
        }
    }
    }




}

