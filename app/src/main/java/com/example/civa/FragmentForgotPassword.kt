package com.example.civa

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class FragmentForgotPassword:Fragment(R.layout.fragment_forgot_password) {
    private lateinit var editTextEmail:EditText
    private lateinit var buttonEnter: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextEmail = view.findViewById(R.id.editTextTextEmailReset)

        buttonEnter = view.findViewById(R.id.buttonForgotPasswordEnter)

        buttonEnter.setOnClickListener {
            val email = editTextEmail.text.toString().trim()

            FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(requireActivity(), "შეამოწმეთ იმეილი", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(requireActivity(), "დარწმუნდით რომ" +
                                "შეყვანილი იმეილი სწორია", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}