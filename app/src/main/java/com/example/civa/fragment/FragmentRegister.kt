package com.example.civa.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.civa.R
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentRegister:Fragment(R.layout.fragment_register) {

    private lateinit var editTextFirstName:EditText
    private lateinit var editTextLastName:EditText
    private lateinit var editTextEmail:EditText
    private lateinit var editTextPassword:EditText
    private lateinit var buttonRegister:Button
    private lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextFirstName = view.findViewById(R.id.editTextFirstNameRegister)
        editTextLastName = view.findViewById(R.id.editTextLastnameRegister)
        editTextEmail = view.findViewById(R.id.editTextTextEmailRegister)
        editTextPassword = view.findViewById(R.id.editTextPasswordRegister)
        buttonRegister = view.findViewById(R.id.buttonRegisterNow)
        val builder = BuilderTool()
        sharedPreferences = this.requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        buttonRegister.setOnClickListener {

            if(!builder.checkInternet(requireActivity())){
                Toast.makeText(requireActivity(), "inte ar ari", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString()
            val firstName = editTextFirstName.text.toString().trim()
            val lastName = editTextLastName.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireActivity(), "შეიყვანეთ მონაცემები სწორად", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if(!isAlphabetic(firstName) || !isAlphabetic(lastName)){
                Toast.makeText(requireActivity(), "შეიყვანეთ სახელი და გვარი სწორად", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }





            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        sharedPreferences.edit()
                            .putString("firstname", firstName)
                            .putString("lastname", lastName)
                            .putString("email", email)
                            .apply()
                        Toast.makeText(requireActivity(), "aded", Toast.LENGTH_SHORT).show()
                        val user = task.result!!.user!!.uid
                        val database = FirebaseDatabase.getInstance().getReference("Users")
                        database.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                database.child(user).child("First Name").setValue(firstName)
                                database.child(user).child("Last Name").setValue(lastName)
                                database.child(user).child("Email").setValue(email)
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                            }
                        })
                        val action =
                            FragmentRegisterDirections.actionFragmentRegisterToFragmentLogin()
                        findNavController().navigate(action)
                    }
                    else{
                    when (task.exception) {
                        is FirebaseAuthUserCollisionException -> {
                            Toast.makeText(
                                requireActivity(),
                                "მომხმარებელი უკვე არსებობს",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is FirebaseAuthWeakPasswordException -> {
                            Toast.makeText(
                                requireActivity(),
                                "პაროლი უნდა შეიცავდეს 6 ან მეტ სიმბოლოს",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is FirebaseNetworkException -> {
                            Toast.makeText(requireActivity(),
                                "არ არის კავშირი ინტერნეტთან",
                                Toast.LENGTH_SHORT)
                                .show()
                        }

                        else -> {
                            Toast.makeText(
                                requireActivity(),
                                "არასწორი იმეილი ან პაროლი",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    }
                }
        }
    }
    private fun isAlphabetic(str: String): Boolean {
        return str.matches("[a-zA-Z]+".toRegex())
    }


}