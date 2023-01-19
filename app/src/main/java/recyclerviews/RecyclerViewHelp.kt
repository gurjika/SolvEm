package recyclerviews

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.R
import dataclasses.Help

class RecyclerViewHelp (
    var helps: List<Help>,

    ): RecyclerView.Adapter<RecyclerViewHelp.HelpViewHolder>() {

    inner class HelpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.help, parent, false)
        return HelpViewHolder(view)
    }

    override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.textViewHelpTitle).text = helps[position].title
        holder.itemView.findViewById<TextView>(R.id.textViewHelpInfo).text = helps[position].info
        holder.itemView.findViewById<Button>(R.id.buttonSeeHowExample).setOnClickListener {
            val url = helps[position].url
            val toOpen = Intent(Intent.ACTION_VIEW)
            toOpen.data = Uri.parse(url)
            holder.itemView.context.startActivity(toOpen)
        }
    }

    override fun getItemCount(): Int {
        return helps.size
    }
}