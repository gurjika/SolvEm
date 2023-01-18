package com.example.civa.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.civa.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentHome:Fragment(R.layout.fragment_home) {
    private lateinit var imageButtonMatrix:ImageButton
    private lateinit var imageButtonVector:ImageButton
    private lateinit var imageButtonFormulas:ImageButton
    private lateinit var imageButtonBinary:ImageButton
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageButtonMatrix = view.findViewById(R.id.imageButtonMatrix)
        imageButtonFormulas = view.findViewById(R.id.imageButtonFormulas)

        imageButtonMatrix.setOnClickListener {
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentSamatrice()
            bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)
            bottomNavigationView.visibility = View.GONE
            findNavController().navigate(action)
        }

        imageButtonVector = view.findViewById(R.id.imageButtonVector)

        imageButtonVector.setOnClickListener {
            bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)
            bottomNavigationView.visibility = View.GONE
            Navigation.findNavController(view).navigate(R.id.fragment010)
        }
        imageButtonBinary = view.findViewById(R.id.imageButtonBinary)

        imageButtonBinary.setOnClickListener {
            bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)
            bottomNavigationView.visibility = View.GONE
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentVectors()
            findNavController().navigate(action)
        }
        imageButtonFormulas.setOnClickListener {
           Navigation.findNavController(view).navigate(R.id.fragmentHoldHistory)
        }

        val email = "email"
        val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(email).child("matrix")
        val matrixRef = databaseRef.child("0")
        matrixRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.exists()){
                    matrixRef.setValue("0")
                    Toast.makeText(requireActivity(), "adeed", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireActivity(), "ari", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        val databaseRefVector = FirebaseDatabase.getInstance().getReference("Users").child("email")
            .child("vector")
        val matrixRefVector = databaseRefVector.child("0")
        matrixRefVector.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.exists()){
                    matrixRefVector.setValue("0")
                    Toast.makeText(requireActivity(), "adeed", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireActivity(), "ari", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        val databaseRefBinary = FirebaseDatabase.getInstance().getReference("Users").child("email")
            .child("binary")
        val matrixRefBinary = databaseRefBinary.child("0")
        matrixRefBinary.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.exists()){
                    matrixRefBinary.setValue("0")
                    Toast.makeText(requireActivity(), "adeed", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireActivity(), "ari", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}