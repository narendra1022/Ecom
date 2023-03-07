package com.example.ecom.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ecom.Communicator
import com.example.ecom.R
import com.example.ecom.fragments.categeries.home_categery
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class shopping : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav) as NavHostFragment

        navController=navHostFragment.navController
        val b=findViewById<BottomNavigationView>(R.id.navbar)
        setupWithNavController(b,navController)
    }

}