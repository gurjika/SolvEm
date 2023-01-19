package com.example.civa.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.civa.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FragmentLogin:Fragment(R.layout.fragment_login) {
    private lateinit var buttonLogin:Button
    private lateinit var buttonRegister:Button
    private lateinit var editTextEmail:EditText
    private lateinit var editTextPassword:EditText
    private lateinit var textViewForgotPassword:TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var database:DatabaseReference
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonLogin = view.findViewById(R.id.buttonLogin)
        buttonRegister = view.findViewById(R.id.buttonRegister)
        editTextEmail = view.findViewById(R.id.editTextTextEmail)
        editTextPassword = view.findViewById(R.id.editTextTextPassword)
        textViewForgotPassword = view.findViewById(R.id.textViewForgotPassword)
        val builder = BuilderTool()
        database = FirebaseDatabase.getInstance().getReference("Users")
        sharedPreferences = this.requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        textViewForgotPassword.setOnClickListener {
            val action = FragmentLoginDirections.actionFragmentLoginToFragmentForgotPassword()
            findNavController().navigate(action)
        }
        buttonLogin.setOnClickListener {

            if(!builder.checkInternet(requireActivity())){
                Toast.makeText(requireActivity(), "არ არის ინტერნეტთან წვდომა", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireActivity(), "შეიყვანეთ მონაცემები", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {task ->
                    if(task.isSuccessful){
                        val action = FragmentLoginDirections
                         .actionFragmentLoginToFragmentHome()
                        findNavController().navigate(action)
                    }
                    else{
                        Toast.makeText(requireActivity(), "${task.exception}", Toast.LENGTH_SHORT).show()
                    }
                }

        }
        buttonRegister.setOnClickListener {
            val action = FragmentLoginDirections.actionFragmentLoginToFragmentRegister()
            findNavController().navigate(action)
        }


    }


}