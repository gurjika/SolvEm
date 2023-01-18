package com.example.civa.fragment

import android.content.Context
import android.content.SharedPreferences
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

class FragmentNToBinary:Fragment(R.layout.fragment_n_to_binary) {
    private lateinit var editTextNToBinary: EditText
    private lateinit var textViewNToBinary:TextView
    private lateinit var buttonCalculate: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewBinary
    private var toSendData = mutableListOf<Binary>()
    private lateinit var esLinearToGo: LinearLayout
    private lateinit var buttonSeeHow:Button
    private var comeFromHistory = false
    private lateinit var database: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNToBinary = view.findViewById(R.id.editTextNToBinary)
        textViewNToBinary = view.findViewById(R.id.textViewNToBinary)
        buttonCalculate = view.findViewById(R.id.buttonCalculateNToBinary)
        esLinearToGo = view.findViewById(R.id.es_linearToGoNToBinary)
        recyclerView = view.findViewById(R.id.recyclerNToBinary)
        buttonSeeHow = view.findViewById(R.id.buttonSeeHowNToBinary)
        database = FirebaseDatabase.getInstance().getReference("Users")
        comeFromHistory = FragmentNToBinaryArgs.fromBundle(requireArguments()).comeFromHistory
        val builder = BuilderTool()
        if(comeFromHistory){
            val receivedDecimal = FragmentNToBinaryArgs.fromBundle(requireArguments()).numbers
            editTextNToBinary.text.clear()
            editTextNToBinary.append(receivedDecimal)
        }
        buttonCalculate.setOnClickListener {
            toSendData.clear()
            var goToBinary = editTextNToBinary.text.toString().toLong()
            if(comeFromHistory) {
                builder.uploadBinary(
                    requireActivity(), database, "$goToBinary;NToBinary")
            }
            comeFromHistory = false
            var leftovers = 0L
            val leftoverArray = mutableListOf<Long>()
            val binaryArray = mutableListOf<Long>()
            val numeratorArray = mutableListOf<String>()
            do{
                leftovers = goToBinary % 2
                numeratorArray.add(goToBinary.toString())
                goToBinary /= 2
                binaryArray.add(goToBinary)
                leftoverArray.add(leftovers)
            }while (goToBinary / 2 != 0L)
            numeratorArray.add("1")
            leftoverArray.add(1)
            binaryArray.add(0)
            val resultArray = leftoverArray.reversed()
            var result = ""
            for(i in 0 until leftoverArray.size){
                result += resultArray[i].toString()
            }
            textViewNToBinary.text = result
            for(i in 0 until leftoverArray.size){
                toSendData.add(Binary
                    (numeratorArray[i],
                    binaryArray[i].toString(),
                    leftoverArray[i].toString(),
                    null,
                    null,
                    null))
            }
        }
        buttonSeeHow.setOnClickListener {
            esLinearToGo.visibility = View.GONE
            adapter = RecyclerViewBinary(toSendData, "NToBinary")
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        }
    }
}