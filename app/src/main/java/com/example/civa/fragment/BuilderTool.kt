package com.example.civa.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.example.civa.MakeGridLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class BuilderTool {

    var userUID = FirebaseAuth.getInstance().currentUser?.uid
    fun buildLinear(context: Context, editTexts: Array<EditText?>, esLinear: LinearLayout, dimension:Int){
        for(i in 0 until dimension){
            val maxLength = 5
            val inputFilter = InputFilter.LengthFilter(maxLength)
            editTexts[i] = EditText(context)
            esLinear.addView(editTexts[i])
            editTexts[i]!!.gravity = Gravity.CENTER
            editTexts[i]!!.width = 100
            editTexts[i]!!.filters = arrayOf(inputFilter)
            editTexts[i]!!.height = 100
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
                setPos.setPosForEditText(editTexts[i][j], i, j, 110)
                gridLayout.addView(editTexts[i][j])
            }
        }
        return  gridLayout
    }

    fun uploadMatrix(
        context: Context,
        database: DatabaseReference,
        resultStringOne: String,
        resultStringTwo: String?){
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentValue = dataSnapshot.child("matrix").child("0").value.toString().toInt()
                val updatedValue = currentValue + 1
                database.child(userUID!!).child("matrix").child("0")
                    .setValue(updatedValue)
                database.child(userUID!!).child("matrix").child(updatedValue.toString())
                    .setValue(resultStringOne)




                if(resultStringTwo != null){
                    database.child(userUID!!).child("matrix").child((updatedValue + 1).toString())
                        .setValue(resultStringTwo)
                    database.child(userUID!!).child("matrix").child("0")
                        .setValue(updatedValue + 1)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.child(userUID!!).addListenerForSingleValueEvent(valueEventListener)
    }

    fun uploadVector(
        context: Context,
        database: DatabaseReference,
        resultStringOne: String,
        resultStringTwo: String,
        resultStringThree: String?){
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentValue = dataSnapshot.child("vector")
                    .child("0").value.toString().toInt()
                val updatedValue = currentValue + 1

                database.child(userUID!!).child("vector")
                    .child("0").setValue(updatedValue + 1)

                database.child(userUID!!).child("vector")
                    .child(updatedValue.toString()).setValue(resultStringOne)

                database.child(userUID!!).child("vector")
                    .child((updatedValue + 1).toString()).setValue(resultStringTwo)


                if(resultStringThree != null){
                    database.child(userUID!!).child("vector")
                        .child((updatedValue + 2).toString()).setValue(resultStringThree)
                    database.child(userUID!!).child("vector")
                        .child("0").setValue(updatedValue + 2)
                }


            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.child(userUID!!).addListenerForSingleValueEvent(valueEventListener)
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

                database.child(userUID!!).child("binary")
                    .child("0").setValue(updatedValue)

                database.child(userUID!!).child("binary")
                    .child(updatedValue.toString()).setValue(resultStringOne)


            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        database.child(userUID!!).addListenerForSingleValueEvent(valueEventListener)

    }

    fun checkInternet(context:Context):Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return if(networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            true
        } else {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("ინტერნეტთან წვდომა არ არის")
            builder.setMessage("")
            builder.setPositiveButton("OK") { _, _ ->

            }
            val dialog = builder.create()
            dialog.show()
            false
        }
    }
}