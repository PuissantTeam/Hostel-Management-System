package com.example.hostelmanagementsystem.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.ActivityAdminBinding

import com.example.hostelmanagementsystem.utils.hideSoftKeyboard
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class AdminActivity : AppCompatActivity() {
    private lateinit var adminBinding: ActivityAdminBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adminBinding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_admin)
        bottomNav = adminBinding.adminBottomNavigation


        navController = findNavController(R.id.admin_host_fragment)
        setupBottomNavigation()

        navigationView = adminBinding.adminNavigationView
        drawerLayout = adminBinding.drawerLayout

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