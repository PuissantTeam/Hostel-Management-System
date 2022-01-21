package com.example.hostelmanagementsystem.activities

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.utils.hideSoftKeyboard


import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView as NavigationView

class StudentActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        bottomNav = findViewById(R.id.student_bottom_navigation)


        navController = findNavController(R.id.student_host_fragment)
        setupBottomNavigation()

        navigationView = findViewById(R.id.student_navigation_view)
        drawerLayout = findViewById(R.id.student_drawer_layout)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        NavigationUI.setupWithNavController(navigationView, navController)

    }

    private fun setupBottomNavigation() {
        bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        hideSoftKeyboard(this)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

}