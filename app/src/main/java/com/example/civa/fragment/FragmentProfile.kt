package com.example.civa.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.civa.R
import com.google.firebase.auth.FirebaseAuth

class FragmentProfile:Fragment(R.layout.fragment_profile) {

    private lateinit var firstName: TextView
    private lateinit var lastName:TextView
    private lateinit var logOutButton:ImageButton
    private lateinit var email:TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var passwordTextView: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firstName = view.findViewById(R.id.textViewFirstNameProfileDisplay)
        lastName = view.findViewById(R.id.textViewLastNameProfileDisplay)
        logOutButton = view.findViewById(R.id.imageButtonLogOut)
        email = view.findViewById(R.id.textViewEmailDsiplay)
        passwordTextView = view.findViewById(R.id.textViewPasswordDisplay)
        sharedPreferences = this.requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        firstName.text = sharedPreferences.getString("firstname", "")
        lastName.text = sharedPreferences.getString("lastname", "")
        email.text = sharedPreferences.getString("email", "")

        logOutButton.setOnClickListener {
            val sharedPreferences = this.requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            FirebaseAuth.getInstance().signOut()
            val action = FragmentProfileDirections.actionFragmentProfileToFragmentLogin()
            findNavController().navigate(action)
        }
        passwordTextView.setOnClickListener {
            val action = FragmentProfileDirections.actionFragmentProfileToFragmentChangePassword()
            findNavController().navigate(action)
        }
    }
}