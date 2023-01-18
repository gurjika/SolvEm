package com.example.civa.fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.example.civa.MakeGridLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class BuilderTool {
    fun buildLinear(context: Context, editTexts: Array<EditText?>, esLinear: LinearLayout, dimension:Int){
        for(i in 0 until dimension){
            val maxLength = 5
            val inputFilter = InputFilter.LengthFilter(maxLength)
            editTexts[i] = EditText(context)
            esLinear.addView(editTexts[i])
            editTexts[i]!!.gravity = Gravity.CENTER
            editTexts[i]!!.width = 70
            editTexts[i]!!.filters = arrayOf(inputFilter)
            editTexts[i]!!.height = 70
            editTexts[i]!!.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
            editTexts[i]!!.setTextColor(Color.BLACK)
        }
    }
    fun buildGrid(
        context: Context,
        editTexts: Array<Array<EditText?>>,
        rows:Int, columns:Int):GridLayout{
        val setPos = MakeGridLayout()
        val gridLayout = GridLayout(context)
        gridLayout.rowCount = rows
        gridLayout.columnCount = columns
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                editTexts[i][j] = EditText(context)
                setPos.setPosForEditText(editTexts[i][j], i, j, 100)
                gridLayout.addView(editTexts[i][j])
            }
        }
        return  gridLayout
    }

    fun uploadMatrix(
        context: Context,
        database: DatabaseReference,
        sharedPreferences: SharedPreferences,
        resultStringOne: String,
        resultStringTwo: String?){
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentValue = dataSnapshot.child("matrix").child("0").value.toString().toInt()
                val updatedValue = currentValue + 1
                database.child("email").child("matrix").child("0")
                    .setValue(updatedValue)
                database.child("email").child("matrix").child(updatedValue.toString())
                    .setValue(resultStringOne)

                sharedPreferences.edit()
                    .putString(updatedValue.toString(), resultStringOne)
                    .apply()
                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show()

                if(resultStringTwo != null){
                    database.child("email").child("matrix").child((updatedValue + 1).toString())
                        .setValue(resultStringTwo)
                    database.child("email").child("matrix").child("0")
                        .setValue(updatedValue + 1)
                    sharedPreferences.edit()
                        .putString((updatedValue + 1).toString(), resultStringTwo)
                        .apply()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "noooooooo", Toast.LENGTH_SHORT).show()
            }
        }
        database.child("email").addListenerForSingleValueEvent(valueEventListener)
    }

    fun uploadVector(
        context: Context,
        database: DatabaseReference,
        sharedPreferences: SharedPreferences,
        resultStringOne: String,
        resultStringTwo: String,
        resultStringThree: String?){
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentValue = dataSnapshot.child("vector")
                    .child("0").value.toString().toInt()
                val updatedValue = currentValue + 1

                database.child("email").child("vector")
                    .child("0").setValue(updatedValue + 1)

                database.child("email").child("vector")
                    .child(updatedValue.toString()).setValue(resultStringOne)

                database.child("email").child("vector")
                    .child((updatedValue + 1).toString()).setValue(resultStringTwo)

                sharedPreferences.edit()
                    .putString(updatedValue.toString(), resultStringOne)
                    .putString((updatedValue + 1).toString(), resultStringTwo)
                    .apply()
                if(resultStringThree != null){
                    database.child("email").child("vector")
                        .child((updatedValue + 2).toString()).setValue(resultStringThree)
                    sharedPreferences.edit()
                        .putString((updatedValue + 2).toString(), resultStringThree)
                        .apply()
                    database.child("email").child("vector")
                        .child("0").setValue(updatedValue + 2)
                }

                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "noooooooo", Toast.LENGTH_SHORT).show()
            }
        }
        database.child("email").addListenerForSingleValueEvent(valueEventListener)
    }
    fun uploadBinary(
        context: Context,
        database: DatabaseReference,
        resultStringOne: String,
    ){
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentValue = dataSnapshot.child("binary")
                    .child("0").value.toString().toInt()
                val updatedValue = currentValue + 1

                database.child("email").child("binary")
                    .child("0").setValue(updatedValue)

                database.child("email").child("binary")
                    .child(updatedValue.toString()).setValue(resultStringOne)

                Toast.makeText(context, "added", Toast.LENGTH_SHORT).show()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "noooooooo", Toast.LENGTH_SHORT).show()
            }
        }
        database.child("email").addListenerForSingleValueEvent(valueEventListener)

    }
}