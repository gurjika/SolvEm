package com.example.civa.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.R
import com.google.firebase.database.*
import dataclasses.HistoryBinary
import recyclerviews.RecyclerViewHistoryBinary


class FragmentHistoryBinary:Fragment(R.layout.fragment_history_binary) {
    private lateinit var database: DatabaseReference
    private var toSendData = mutableListOf<HistoryBinary>()
    private lateinit var adapter: RecyclerViewHistoryBinary
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().getReference("Users")

        recyclerView = view.findViewById(R.id.recyclerHistoryBinary)

        var counter: Int
        database.child("email").child("binary").get().addOnSuccessListener {
            counter = it.child("0").value.toString().toInt()
            val database = FirebaseDatabase.getInstance()
            val ref = database.getReference("Users").child("email").child("binary")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (i in 1..counter) {
                        val key = i.toString()
                        val value = dataSnapshot.child(key).getValue(String::class.java)
                        val resultArray = value!!.split(";")
                        toSendData.add(HistoryBinary(resultArray[0], resultArray[1]))
                    }
                    if (isAdded) {
                        adapter = RecyclerViewHistoryBinary(toSendData.reversed())
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        }
    }
}