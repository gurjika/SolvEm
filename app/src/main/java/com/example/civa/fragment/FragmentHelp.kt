package com.example.civa.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.civa.R
import com.example.civa.ViewPagerFragmentAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import dataclasses.Help
import recyclerviews.RecyclerViewHelp

class FragmentHelp: Fragment(R.layout.fragment_help) {

    private lateinit var recycler:RecyclerView
    private lateinit var adapter:RecyclerViewHelp
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listToPass = mutableListOf<Help>()
        listToPass.add(Help("მატრიცის დეტერმინანტი", "მატრიცის დეტერმინანტის პოვნა შეგვიძლია " +
                "ალგებრულის დამატებების მეთოდით. " +
                "ესაა ერთგვარი რეკურსიული პროცესი, რომელიც გულიხსმობს მოცემული მატრიცის „ქვემატრიცებად“ " +
                "დაყოფას. მაგალითისთვის თუ გვაქვს მატრიცა განზომილებით 3x3, შეგვიძლია ავირჩიოთ პირველი " +
                "სტრიქონი და ამ სტრიქონში ყოველი სვეტისთვის დავითვალოთ მინორი. მინორის დასათველელად" +
                " უნდა ჩამოვაშოროთ არჩეული სვეტი და სტრიქონი მატრიცას და დარჩენილი 2x2 მატრიცის მთავარ " +
                "დიაგონალზე მდგომი წევრების ნამრავლს გამოვაკლოთ ირიბ დიაგონალზე მდგომი წევრების ნამრავლი. " +
                "მინორი უნდა დავითვალოთ არჩეული სტრიქონის ყოველი სვეტისთვის. მინორის დათვლის შემდეგ, " +
                "ალგებრული დამატებების მისაღებად, ის წევრი, რომლის არჩევის შემდეგაც ჩამოვაშორეთ სტრიქონი და" +
                " სვეტი მატრიცას, უნდა გავამრავლოთ მინორზე. გასათვალისწინებელია აუცილებლად ისიც რომ თუ" +
                " ამ წევრის ინდექსების ჯამი კენტია, მაშინ ალგებრული დამატება შეიცვლის ნიშანს. დეტერმინანტის" +
                " მისაღებად კი ჩვენ მიერ არჩეული სტრიქონის ალგებრული დამატებები შევკრიბოთ.",
            "https://www.youtube.com/watch?v=u3TPOoHXu74&ab_channel=KhanAcademyGeorgian"))
        listToPass.add(Help("შებრუნებული მატრიცა", "შებრუნებული მატრიცის საპოვნელად ამ მატრიცის" +
                " მიკავშირებული მატრიცის თითოეული წევრი უნდა გავყოთ მატრიცის დეტერმინანტზე." +
                " მიკავშირებული მატრიცის საპოვნელად უნდა ვიპოვოთ მოცემული მატრიცის" +
                " ყველა წევრის ალგებრული დამატება. მათი პოვნის შემდეგ შევადგინოთ მატრიცა," +
                " რომელშიც მატრიცის წევრების ნაცვლად, მათი ალგებრული დამატებებს ჩავწერთ და ვიპოვოთ" +
                " ამ მატრიცის ტრანსპონირებული. ასე მივიღებთ მიკავშირებულ მატრიცას.",
        "https://www.youtube.com/watch?v=iuiCsLqObtI&ab_channel=KhanAcademyGeorgian"))
        listToPass.add(Help("ტრანსპონირებული მატრიცა", "მატრიცის ტრანსპონირებულის" +
                " საპოვნელად მატრიცის სტრიქონები უნდა ჩავწეროთ სვეტებად",
            "https://www.youtube.com/watch?v=s4iKYhO6FV0&ab_channel=KhanAcademyGeorgian"))
        listToPass.add(Help("მატრიცათა ნამრავლი", " ნამრავლი, რომელიც აღინიშნება როგორც C, " +
                "არის mxp ზომის მატრიცა. ახალი C მატრიცის მისაღებად მატრიცის წევრები უნდა გადავამრავლოთ " +
                "სკალარული ნამრავლის პრინციპით: მატრიცის თითოეული სტრიქონის ნამრავლი მატრიცის თითოეულ " +
                "სვეტზე. მაგალითად C მატრიცის პირველი სტრიქონსა და პირველ სვეტზე მდგომი წევრი იქნება A " +
                "მატრიცის პირველი სტრიქონის სკალარული ნამრავლის B მატრიცის პირველ სვეტზე. სხვა სიტყვებით" +
                " რომ ვთქვათ, მატრიცის გამრავლებისას, პირველ მატრიცაში სვეტების რაოდენობა უნდა იყოს მეორე " +
                "მატრიცის მწკრივების რაოდენობის ტოლი. შემდეგ, ორი მატრიცის ნამრავლი არის ახალი მატრიცა," +
                " რომელსაც აქვს მწკრივების იგივე რაოდენობა, როგორც პირველი მატრიცა და იგივე რაოდენობის სვეტები," +
                " როგორც მეორე მატრიცა. მნიშვნელოვანია აღინიშნოს, რომ მატრიცის გამრავლება არ არის კომუტაციური," +
                " რაც იმას ნიშნავს, რომ მატრიცების გამრავლების თანმიმდევრობა მნიშვნელოვანია, AB არ არის BA-ს ტოლი",
                "https://www.youtube.com/watch?v=IVkZlxHEGCU"))
        listToPass.add(Help("მიმატება და გამოკლება", "მატრიცის მიმატება და გამოკლება ნიშნავს " +
                "ორი მატრიცის შესაბამისი წევრების მიმატებასა და გამოკლებას. ცხადია ამ ორ მატრიცას" +
                " ერთი და იგივე განზომილებები უნდა ჰქონდეს",
            "https://www.youtube.com/watch?v=t20pqQKIqrQ&ab_channel=KhanAcademyGeorgian"))
        listToPass.add(Help("უცნობის პოვნა", "იმისათვის რომ AX=B გამოსახულებაში X მატრიცა " +
                "ვიპოვოთ, უნდა გამოვთვალოთ A ს შებრუნებული მატრიცა და შემდეგ ეს მატრიცა გავამრავლოთ " +
                "B მატრიცაზე. მნიშვნელოვანია რომ AX=B არ არის იგივე რაც XA=B. XA=B ს შემთხვევაში B" +
                "მატრიცას ვამრავლებთ A მატრიცის შებრუნებულზე.",
            "https://www.youtube.com/watch?v=-JIIp93ptCg&t=743s&ab_channel=KhanAcademyGeorgian"))
        adapter = RecyclerViewHelp(listToPass)
        recycler = view.findViewById(R.id.recyclerHelpMatrix)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireActivity())


    }
}