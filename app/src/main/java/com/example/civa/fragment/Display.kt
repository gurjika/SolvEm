package com.example.civa.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.R


class Display(
    var algebrals: List<DisplayAlgebrals>
) : RecyclerView.Adapter<Display.DisplayViewHolder>(){
    inner class DisplayViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.algebrals, parent, false)
        return DisplayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DisplayViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.textViewA).text = algebrals[position].asAndDs
        holder.itemView.findViewById<TextView>(R.id.textViewIndex).text = algebrals[position].index
        holder.itemView.findViewById<TextView>(R.id.textViewInverseResult).text = algebrals[position].algebrals
    }

    override fun getItemCount(): Int {
        return algebrals.size
    }

}