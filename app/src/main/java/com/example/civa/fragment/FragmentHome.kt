package com.example.civa.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.civa.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentHome:Fragment(R.layout.fragment_home) {
    private lateinit var imageButtonMatrix:Button
    private lateinit var imageButtonVector: Button
    private lateinit var imageButtonFormulas:Button
    private lateinit var imageButtonBinary:Button
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var imageButtonProfile:ImageButton
    private lateinit var sharedPreferences:SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageButtonMatrix = view.findViewById(R.id.imageButtonMatrix)
        imageButtonFormulas = view.findViewById(R.id.imageButtonFormulas)
        imageButtonProfile = view.findViewById(R.id.imageButtonProfile)

        bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)
        bottomNavigationView.visibility = View.VISIBLE

        val builder = BuilderTool()
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        builder.userUID = FirebaseAuth.getInstance().currentUser!!.uid
        val user = builder.userUID
        imageButtonMatrix.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.fragmentSamatrice)
            bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)
            bottomNavigationView.visibility = View.GONE

        }

        imageButtonVector = view.findViewById(R.id.imageButtonVector)

        imageButtonVector.setOnClickListener {
            bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)
            bottomNavigationView.visibility = View.GONE
            val action = FragmentHomeDirections.actionFragmentHomeToFragmentVectors()
            findNavController().navigate(action)

        }
        imageButtonBinary = view.findViewById(R.id.imageButtonBinary)

        imageButtonBinary.setOnClickListener {
            bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)
            bottomNavigationView.visibility = View.GONE
            Navigation.findNavController(view).navigate(R.id.fragmentHoldHistory)

        }
        imageButtonFormulas.setOnClickListener {
            bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)
            bottomNavigationView.visibility = View.GONE
            Navigation.findNavController(view).navigate(R.id.fragment010)

        }

        imageButtonProfile.setOnClickListener {
            bottomNavigationView = requireActivity().findViewById(R.id.bottomnav)
            bottomNavigationView.visibility = View.GONE

            val action = FragmentHomeDirections.actionFragmentHomeToFragmentProfile()
            findNavController().navigate(action)
        }


        val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(user!!)
            .child("matrix")
        val matrixRef = databaseRef.child("0")
        matrixRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.exists()){
                    matrixRef.setValue("0")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        val databaseRefVector = FirebaseDatabase.getInstance().getReference("Users").child(user)
            .child("vector")
        val matrixRefVector = databaseRefVector.child("0")
        matrixRefVector.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.exists()){
                    matrixRefVector.setValue("0")

                }

            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        val databaseRefBinary = FirebaseDatabase.getInstance().getReference("Users")
            .child(user).child("binary")
        val matrixRefBinary = databaseRefBinary.child("0")
        matrixRefBinary.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.exists()){
                    matrixRefBinary.setValue("0")

                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        sharedPreferences = this.requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val databaseRefInfo = FirebaseDatabase.getInstance().getReference("Users")

        databaseRefInfo.child(user).get().addOnSuccessListener {
            if(it.exists()){
                val firstname = it.child("First Name").value.toString()
                val lastname = it.child("Last Name").value.toString()
                val email = it.child("Email").value.toString()
                sharedPreferences.edit()
                    .putString("firstname", firstname)
                    .putString("lastname", lastname)
                    .putString("email", email)
                    .apply()

            }
        }
    }
}