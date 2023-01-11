package com.example.civa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.fragment.FragmentDeterminant
import com.example.civa.fragment.FragmentHistory
import com.example.civa.fragment.FragmentHistoryDirections
import com.example.civa.fragment.FragmentInverse

class RecyclerViewHistory (
    var matrixes: List<History>
): RecyclerView.Adapter<RecyclerViewHistory.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        if(matrixes[position].historyMatrix.parent != null){
            (matrixes[position].historyMatrix.parent as ViewGroup).removeView(matrixes[position].historyMatrix)
        }
        if(matrixes[position].historyMatrixTwo?.parent != null){
            (matrixes[position].historyMatrixTwo?.parent as ViewGroup).removeView(matrixes[position].historyMatrix)
        }
        if(matrixes[position].historyMatrixTwo != null){
            holder.itemView.findViewById<LinearLayout>(R.id.es_linearHistoryTwo)
                .addView(matrixes[position].historyMatrixTwo)
            holder.itemView.findViewById<TextView>(R.id.textViewHistory).text = matrixes[position].operation
        }
        holder.itemView.findViewById<LinearLayout>(R.id.es_linearHistory)
            .addView(matrixes[position].historyMatrix)

        holder.itemView.findViewById<Button>(R.id.buttonGoBack).setOnClickListener {
            if(matrixes[position].destination == "INVERSE"){

                val dimension = matrixes[position].toSend.last().toInt()
                val action = FragmentHistoryDirections
                    .actionFragmentHistoryToFragmentInverse(dimension, matrixes[position].toSend, true)
                it.findNavController().navigate(action)

            }
            else if(matrixes[position].destination == "MULTIPLY"){

                val dimension = intArrayOf(matrixes[position].toSend.last().toInt(),
                    matrixes[position].toSend[matrixes[position].toSend.size - 2].toInt(),
                    matrixes[position].toSendSecond.last().toInt(),
                    matrixes[position].toSendSecond[matrixes[position].toSendSecond.size - 2].toInt())
                val action = FragmentHistoryDirections
                    .actionFragmentHistoryToFragmentMultiply(dimension, matrixes[position].toSend, matrixes[position].toSendSecond, true)
                it.findNavController().navigate(action)
            }

            else if(matrixes[position].destination == "DETERMINANT"){

                val dimension = matrixes[position].toSend.last().toInt()

                val action = FragmentHistoryDirections
                    .actionFragmentHistoryToFragmentDeterminant(dimension, matrixes[position].toSend, true)
                it.findNavController().navigate(action)
            }

            else if(matrixes[position].destination == "ADD" || matrixes[position].destination == "SUBTRACT" ){

                val dimensionFirst = matrixes[position].toSend.last().toInt()
                val dimensionSecond =matrixes[position].toSend[matrixes[position].toSend.size - 2].toInt()
                var operation = ""
                if(matrixes[position].destination == "ADD"){
                    operation = "+"
                }
                else{
                    operation = "-"
                }
                val action = FragmentHistoryDirections
                    .actionFragmentHistoryToFragmentAddOrSubtract(
                        "$dimensionFirst$dimensionSecond$operation", matrixes[position].toSend,
                        matrixes[position].toSendSecond, true)
                it.findNavController().navigate(action)
            }




        }


    }

    override fun getItemCount(): Int {

        return matrixes.size
    }
}






