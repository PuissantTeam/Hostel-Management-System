package com.example.hostelmanagementsystem.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.viewpager2.widget.ViewPager2
import com.example.hostelmanagementsystem.data.Prefs
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.activities.*

import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var mViewPager: ViewPager2
    private lateinit var registerButtonOnBoard: Button
    private lateinit var loginButtonOnBoard: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val prefs = Prefs(this)
        if(prefs.status > 0){
            when(prefs.userType){
                "admin" -> {
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                }
                "warden" -> {
                    val intent = Intent(this, WardenActivity::class.java)
                    startActivity(intent)
                }
                "student" -> {
                    val intent = Intent(this, StudentActivity::class.java)
                    startActivity(intent)
                }
            }

        }

        val actionBar: ActionBar? = supportActionBar
        supportActionBar?.hide()

        registerButtonOnBoard = findViewById(R.id.registerButtonOnBoard)
        registerButtonOnBoard.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        loginButtonOnBoard = findViewById(R.id.loginButtonOnBoard)
        loginButtonOnBoard.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        mViewPager = findViewById(R.id.viewPager)
        mViewPager.adapter = OnBoardingViewPagerAdapter(this, this)
        TabLayoutMediator(findViewById(R.id.pageIndicator), mViewPager) { _, _ -> }.attach()
        mViewPager.offscreenPageLimit = 1

//        val prefs = Prefs(applicationContext)
//        val status = prefs.status
//        if(status == 1 && FirebaseAuth.getInstance().currentUser != null){
//            startActivity(Intent(applicationContext, MainActivity::class.java))
//            finish()
//        }
    }
}