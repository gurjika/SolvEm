package com.example.civa.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.civa.R
import dataclasses.Help
import recyclerviews.RecyclerViewHelp

class FragmentHelp2: Fragment(R.layout.fragment_help_2){

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RecyclerViewHelp
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listToPass = mutableListOf<Help>()
        listToPass.add(Help("სკალარული ნამრავლი", "სკალარული ნამრავლის საპოვნელად საჭიროა " +
                "ვექტორის შესაბამისი წევრები გადავამრავლოთ და შემდეგ ვიპოვოთ მათი ჯამი",
            "https://www.youtube.com/watch?v=qGM38ciU4Ek&ab_channel=KhanAcademyGeorgian"))
        listToPass.add(Help("ვექტორული ნამრავლი", "ვექტორული ნამრავლი შეგვიძლია ვიპოვოთ" +
                " მატრიცის შედგენის მეთოდით. პირველ სვეტში ვწერთ I j k წევრებს, ხოლო მეორე და " +
                "მესამე სტრიქონები შეგვიძლია შევავსოთ შესაბამისად პირველი და მეორე ვექტორების " +
                "კოორდინატებით. შედეგად მივიღებთ I j k მეთოდით ჩაწერილ ვექტორს.",
            "https://www.youtube.com/watch?v=V1ewGuXTf4c"))
        listToPass.add(Help("კოსინუსი ორ ვექტორს შორის", "ორ ვექტორს შორის კოსინუსის საპოვნელად" +
                " შეგვიძლია გამოვიყენოთ სკალარული ნამრავლის ფორმულა: " +
                "a * b = cosa * |a|*|b|. სკალარული ნამრავლის მეორე ხერხით" +
                " დათვლის შემდეგ, შეგვიძლია გამოვითვალოთ კოსუნუსიც.",
            "https://www.youtube.com/watch?v=HUu1wCVpmeA&ab_channel=KhanAcademyGeorgian"))
        listToPass.add(Help("სინუსი ორ ვექტორს შორის", "ორ ვექტორს შორის სინუსის საპოვნელად" +
                " შეგვიძლია გამოვიყენოთ ვექტორული ნამრავლის ფორმულა: a x b = sina * |a|*|b|. " +
                "ვექტორული ნამრავლის მეორე ხერხით გამოთვილის შემდეგ, შეგვიძლია გამოვითვალოთ სინუსიც.",
                "https://www.youtube.com/watch?v=HUu1wCVpmeA&ab_channel=KhanAcademyGeorgian"))
        listToPass.add(Help("შერეული ნამრავლი", "შერეული ნამრავლის საპოვნელად შეგვიძლია" +
                " მოცემული სამი ვექტორით შევადინოთ მატრიცა, რომლის პირველი სვეტი იქნება პირველი" +
                "ვექტორის კოორდინატები, მეორე სვეტი მეორე ვექტორის ხოლო მესამე სვეტი მესამე ვექტორის." +
                " შერეული ნამრავლი კი უდრის ამ მატრიცის დეტერმინანტის მოდულს.",
                "https://www.youtube.com/watch?v=R3ZHQNLF7lc&ab_channel=Lettherebemath"))
        adapter = RecyclerViewHelp(listToPass)
        recycler = view.findViewById(R.id.recyclerHelpVectors)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireActivity())
    }
}