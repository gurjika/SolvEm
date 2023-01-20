package recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.R
import com.example.civa.fragment.FragmentHistoryVector
import com.example.civa.fragment.FragmentHistoryVectorDirections
import dataclasses.HistoryVector

class RecyclerViewVectorHistory (
    var vectors: List<HistoryVector>
): RecyclerView.Adapter<RecyclerViewVectorHistory.HistoryVectorViewHolder>() {

    inner class HistoryVectorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVectorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vectors, parent, false)
        return HistoryVectorViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryVectorViewHolder, position: Int){
        if(vectors[position].vectorOne.parent != null){
            (vectors[position].vectorOne.parent as ViewGroup).removeView(vectors[position].vectorOne)
        }


        if(vectors[position].vectorTwo.parent != null){
            (vectors[position].vectorTwo.parent as ViewGroup).removeView(vectors[position].vectorTwo)
        }


        if(vectors[position].vectorThree?.parent != null){
            (vectors[position].vectorThree?.parent as ViewGroup).removeView(vectors[position].vectorThree)
        }




        holder.itemView.findViewById<LinearLayout>(R.id.es_linearVectorFirst)
            .addView(vectors[position].vectorOne)

        holder.itemView.findViewById<LinearLayout>(R.id.es_linearVectorSecond)
            .addView(vectors[position].vectorTwo)

        if(vectors[position].vectorThree != null) {
            holder.itemView.findViewById<LinearLayout>(R.id.es_linearVectorThird)
                .addView(vectors[position].vectorThree)
        }


        holder.itemView.findViewById<Button>(R.id.buttonGoToVectors).setOnClickListener {
            if(vectors[position].destination == "To easier"){
                if(vectors[position].operation == "COS"){
                    val operation = vectors[position].operation
                    val dimension = vectors[position].dimension
                    val vectorFirst = vectors[position].toSend
                    val vectorSecond = vectors[position].toSendSecond
                    val action = FragmentHistoryVectorDirections
                        .actionFragmentHistoryVectorToFragmentVectorEasier(
                            dimension,
                            operation,
                            vectorFirst,
                            true,
                            vectorSecond)
                    it.findNavController().navigate(action)
                }
                else{
                    val operation = vectors[position].operation
                    val dimension = vectors[position].dimension
                    val vectorFirst = vectors[position].toSend
                    val vectorSecond = vectors[position].toSendSecond
                    val action = FragmentHistoryVectorDirections
                        .actionFragmentHistoryVectorToFragmentVectorEasier(
                            dimension,
                            operation,
                            vectorFirst,
                            true,
                            vectorSecond)
                    it.findNavController().navigate(action)
                }
            }
            else{
                if(vectors[position].operation == "SIN"){
                    val operation = vectors[position].operation
                    val dimension = vectors[position].dimension
                    val vectorFirst = vectors[position].toSend
                    val vectorSecond = vectors[position].toSendSecond
                    val action = FragmentHistoryVectorDirections
                        .actionFragmentHistoryVectorToFragmentVectorHarder(
                            operation,
                            vectorFirst,
                            true,
                            vectorSecond,
                            null)
                    it.findNavController().navigate(action)
                }
                else if(vectors[position].operation == "x"){
                    val operation = vectors[position].operation
                    val dimension = vectors[position].dimension
                    val vectorFirst = vectors[position].toSend
                    val vectorSecond = vectors[position].toSendSecond
                    val action = FragmentHistoryVectorDirections
                        .actionFragmentHistoryVectorToFragmentVectorHarder(
                            operation,
                            vectorFirst,
                            true,
                            vectorSecond,
                            null)
                    it.findNavController().navigate(action)
                }
                else{
                    val operation = "MIXED"
                    val dimension = vectors[position].dimension
                    val vectorFirst = vectors[position].toSend
                    val vectorSecond = vectors[position].toSendSecond
                    val vectorThird = vectors[position].toSendThird
                    val action = FragmentHistoryVectorDirections
                        .actionFragmentHistoryVectorToFragmentVectorHarder(
                            operation,
                            vectorFirst,
                            true,
                            vectorSecond,
                            vectorThird)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return vectors.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}