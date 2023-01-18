package recyclerviews

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.History
import com.example.civa.R
import com.example.civa.RecyclerViewHistory
import dataclasses.Binary

class RecyclerViewBinary (
    var binary: List<Binary>,
    var comeFrom: String,
): RecyclerView.Adapter<RecyclerViewBinary.BinaryViewHolder>() {

    inner class BinaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.binary, parent, false)
        return BinaryViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BinaryViewHolder, position: Int) {
        if(comeFrom == "BinaryToN") {
            holder.itemView.findViewById<TextView>(R.id.textViewDisplayBinary).text =
                "${binary[position].toDecimalFirst} * ${binary[position].toDecimalSecond} " +
                        " = ${binary[position].toDecimalThird}"
        }
        else{
            holder.itemView.findViewById<TextView>(R.id.textViewDisplayBinary).text =
                "${binary[position].toBinaryFirst} / 2 = ${binary[position].toBinarySecond} " +
                        "ნაშთი ${binary[position].toBinaryThird}"
        }
    }

    override fun getItemCount(): Int {
        return binary.size
    }
}