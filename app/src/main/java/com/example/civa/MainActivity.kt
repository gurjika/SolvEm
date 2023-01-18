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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.civa.fragment.FragmentInverse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val network = connectivityManager.activeNetwork
//        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
//        if(networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
//            Toast.makeText(this, "ae", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "ae", Toast.LENGTH_SHORT).show()
//        }








        bottomNavigationView = findViewById(R.id.bottomnav)
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()


    }


}