package recyclerviews

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.R
import com.example.civa.fragment.FragmentHistoryBinary
import com.example.civa.fragment.FragmentHistoryBinaryDirections
import dataclasses.HistoryBinary

class RecyclerViewHistoryBinary (
    var binary: List<HistoryBinary>,
): RecyclerView.Adapter<RecyclerViewHistoryBinary.BinaryHistoryViewHolder>() {

    inner class BinaryHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinaryHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_binary, parent, false)
        return BinaryHistoryViewHolder(view)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BinaryHistoryViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.textViewHistoryBinary).text = binary[position].binary
        holder.itemView.findViewById<Button>(R.id.buttonHistoryBinary).setOnClickListener {
            if(binary[position].destination == "BinaryToN"){
                val toSend = binary[position].binary
                val action = FragmentHistoryBinaryDirections
                    .actionFragmentHistoryBinaryToFragmentBinaryToN(toSend, true)
                it.findNavController().navigate(action)
            }
            else{
                val toSend = binary[position].binary
                val action = FragmentHistoryBinaryDirections
                    .actionFragmentHistoryBinaryToFragmentNToBinary(toSend, true)
                it.findNavController().navigate(action)

            }
         }
    }

    override fun getItemCount(): Int {
        return binary.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}