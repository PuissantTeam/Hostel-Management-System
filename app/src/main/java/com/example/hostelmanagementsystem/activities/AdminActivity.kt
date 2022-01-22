package com.example.hostelmanagementsystem.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.hostelmanagementsystem.data.Prefs
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.onboarding.OnBoardingActivity

import com.example.hostelmanagementsystem.utils.hideSoftKeyboard
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var adminNavigationView : NavigationView
    private lateinit var adminBottomNavigation : BottomNavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        adminBottomNavigation = findViewById(R.id.admin_bottom_navigation)
        drawerLayout = findViewById(R.id.drawer_layout)
        adminNavigationView = findViewById(R.id.admin_navigation_view)

        bottomNav = adminBottomNavigation
        navController = findNavController(R.id.admin_host_fragment)
        setupBottomNavigation()

        navigationView = adminNavigationView
        drawerLayout = drawerLayout

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_drawer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.admin_logout -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Are you sure you want to logout?").setPositiveButton(
                    "Yes"
                ) { dialogInterface: DialogInterface?, i: Int ->
                    FirebaseAuth.getInstance().signOut()
                    val prefs = Prefs(this)
                    prefs.status = 0
                    prefs.userType= "none"
                    val intent = Intent(this, OnBoardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                    .setNegativeButton(
                        "No"
                    ) { dialogInterface: DialogInterface, i: Int -> dialogInterface.cancel() }
                val alertDialog = builder.create()
                alertDialog.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}