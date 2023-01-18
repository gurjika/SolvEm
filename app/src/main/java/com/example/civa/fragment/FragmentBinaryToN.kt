package com.example.civa.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dataclasses.Binary
import recyclerviews.RecyclerViewBinary
import recyclerviews.RecyclerViewVectorHistory
import kotlin.math.pow

class FragmentBinaryToN:Fragment(R.layout.fragment_binary_to_n) {

    private lateinit var editTextNToBinary: EditText
    private lateinit var textViewNToBinary: TextView
    private lateinit var buttonCalculate: Button
    private lateinit var buttonSeeHow:Button
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewBinary
    private var comeFromHistory = false
    private var toSendData = mutableListOf<Binary>()
    private lateinit var esLinearToGo: LinearLayout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNToBinary = view.findViewById(R.id.editTextBinaryToN)
        textViewNToBinary = view.findViewById(R.id.textViewBinaryToN)
        buttonCalculate = view.findViewById(R.id.buttonCalculateBinaryToN)
        recyclerView = view.findViewById(R.id.recyclerBinaryToN)
        buttonSeeHow = view.findViewById(R.id.buttonSeeHowBinaryTonN)
        esLinearToGo = view.findViewById(R.id.es_linearBinaryToN)
        database = FirebaseDatabase.getInstance().getReference("Users")
        val builder = BuilderTool()
        comeFromHistory = FragmentBinaryToNArgs.fromBundle(requireArguments()).comeFromHistory

        if(comeFromHistory){
            val receivedBinary = FragmentBinaryToNArgs.fromBundle(requireArguments()).numbers
            editTextNToBinary.text.clear()
            editTextNToBinary.append(receivedBinary)
        }
        buttonCalculate.setOnClickListener {
            toSendData.clear()
            val size = editTextNToBinary.text.length
            val binary = editTextNToBinary.text.toString()
            if(!comeFromHistory) {
                builder.uploadBinary(requireActivity(), database, "$binary;BinaryToN")
            }
            comeFromHistory = false
            val powArray = mutableListOf<Int>()
            var toAdd = 0
            var result = 0
            val sumArray = mutableListOf<Int>()
            for((index, i) in (size - 1  downTo   0).withIndex()){
                toAdd = 2.0.pow(i).toInt()
                powArray.add(toAdd)
                sumArray.add(toAdd * binary[index].toString().toInt())
                result += toAdd * binary[index].toString().toInt()
            }
            textViewNToBinary.text = result.toString()

            for(i in 0 until size){
                toSendData.add(Binary
                    ( null, null, null,
                powArray[i].toString(), binary[i].toString(), sumArray[i].toString()))
            }
        }
        buttonSeeHow.setOnClickListener {
            esLinearToGo.visibility = View.GONE
            adapter = RecyclerViewBinary(toSendData, "BinaryToN")
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        }
    }
}