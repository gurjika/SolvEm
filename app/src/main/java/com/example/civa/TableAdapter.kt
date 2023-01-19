package com.example.civa


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.pow


class TableAdapter(
    var tables: List<Table>
) : RecyclerView.Adapter<TableAdapter.TableViewHolder>(){
    var e = 0
    inner class TableViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.tables, parent, false)
       return TableViewHolder(view)
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {

        if(tables[position].matrix.parent != null){
            (tables[position].matrix.parent as ViewGroup).removeView(tables[position].matrix)

        }
        holder.itemView.findViewById<LinearLayout>(R.id.lineari).addView(tables[position].matrix)


        holder.itemView.findViewById<TextView>(R.id.textView4).text = tables[position].sums
        if(tables[position].parentNumber.isNotEmpty()){
        holder.itemView.findViewById<TextView>(R.id.textView5).text = tables[position].parentNumber + "  *  "
        }
    }

    override fun getItemCount(): Int {
        return tables.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

