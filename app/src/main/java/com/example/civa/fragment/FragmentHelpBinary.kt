package com.example.civa.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.R
import dataclasses.Help
import recyclerviews.RecyclerViewHelp

class FragmentHelpBinary:Fragment(R.layout.fragment_help_binary) {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RecyclerViewHelp
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recyclerViewHelpBinary)

        val listToPass = mutableListOf<Help>()

        listToPass.add(Help("ორობითიდან ათობითში", "ორობითიდან ათობითში გადასაყვანად საჭიროა" +
                " მოცემული ორობითი რიცხვის სიგრძის მიხედვით, ავიყვანოთ 2 შესაბამის ხარისხებში და" +
                " გავამრავლოთ ორობითი რიცხვის შესაბამის ციფრებზე და ვიპოვოთ მათი ჯამი. მაგალითად" +
                " თუ ავიღებთ ორობითში რიცხვს 1000. უნდა ვიპოვოთ 2 ის მესამე ხარისხი გამრავლებული 1 " +
                "დამატებული 2 ის მეორე ხარისხი გამრავლებული ნულზე და ა.შ.",
                "https://www.youtube.com/watch?v=zoQTARJxOwc"))
        listToPass.add(Help("ათობითიდან ორობითში", "ათობითიდან ორობითში გადასაყვანად საჭიროა" +
                " მოცემული რიცხვი განმეორებით გავყოთ ორზე, იქამდე სანამ განაყოფში ნულს არ მივიღებთ, " +
                "დავიმახსოვროთ ნაშთები და შესაბამისი ორობითი რიცხვის მისაღებად ჩავიწყოთ მათი ჩაწერა ბოლოდან.",
                "https://www.youtube.com/watch?v=S8r3tnQ2khw"))

        adapter = RecyclerViewHelp(listToPass)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireActivity())




    }
}