package com.example.civa


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.civa.fragment.FragmentHome
import com.example.civa.fragment.FragmentInverse
import com.example.civa.fragment.FragmentLogin
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        if (FirebaseAuth.getInstance().currentUser != null) {
            Toast.makeText(this, "wabida", Toast.LENGTH_SHORT).show()
            navController.popBackStack(R.id.fragmentLogin, true);
            navController.navigate(R.id.fragmentHome, null)
            NavOptions.Builder().setPopUpTo(R.id.fragmentHome, true).build()
        } else {

            Toast.makeText(this, "arrari", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.fragmentLogin)
        }

        bottomNavigationView = findViewById(R.id.bottomnav)

        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()


    }


}