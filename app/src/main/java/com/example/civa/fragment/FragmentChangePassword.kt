package com.example.civa.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.civa.R
import com.google.firebase.auth.FirebaseAuth

class FragmentChangePassword:Fragment(R.layout.fragment_change_passsword) {

    private lateinit var changeButton:Button
    private lateinit var editTextPasswordChange:EditText
    private lateinit var editTextPasswordRepeat:EditText
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeButton = view.findViewById(R.id.buttonChangePassword)
        editTextPasswordChange = view.findViewById(R.id.editTextPasswordChange)
        editTextPasswordRepeat = view.findViewById(R.id.editTextTextPasswordRepeat)

        changeButton.setOnClickListener {
            if(editTextPasswordChange.text.isEmpty()){
                Toast.makeText(requireActivity(), "შეიყვანეთ პაროლი", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(editTextPasswordChange.text.length < 6){
                Toast.makeText(requireActivity(), "პაროლი უნდა იყოს 6 სიმბოლოზე მეტი",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(editTextPasswordChange.text.toString() != editTextPasswordRepeat.text.toString()){
                Toast.makeText(requireActivity(), "პაროლები ერთმანეთს არ ემთხვევა", Toast.LENGTH_SHORT).show()
            }
            FirebaseAuth.getInstance().currentUser!!
                .updatePassword(editTextPasswordChange.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(requireActivity(), "პაროლი წარმატებით შეიცვალა", Toast.LENGTH_SHORT).show()
                        val action = FragmentChangePasswordDirections
                            .actionFragmentChangePasswordToFragmentProfile()
                        findNavController().navigate(action)
                    }
                    else{
                        Toast.makeText(requireActivity(), "${it.exception}", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }

}