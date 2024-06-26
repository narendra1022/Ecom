package com.example.ecom.activities

import ShoppingViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.ecom.R
import com.example.ecom.viewmodel.FirebaseDb
import com.example.ecom.viewmodel.ShoppingViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class shopping : AppCompatActivity() {
    private lateinit var navController: NavController
    val viewModel by lazy {
        val fDatabase = FirebaseDb()
        val providerFactory = ShoppingViewModelProviderFactory(fDatabase)
        ViewModelProvider(this, providerFactory)[ShoppingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView)

        windowInsetsController?.isAppearanceLightNavigationBars = true
        windowInsetsController?.isAppearanceLightStatusBars = true

        setContentView(R.layout.activity_shopping)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav) as NavHostFragment

        navController = navHostFragment.navController
        val b = findViewById<BottomNavigationView>(R.id.navbar)
        setupWithNavController(b, navController)
    }

}