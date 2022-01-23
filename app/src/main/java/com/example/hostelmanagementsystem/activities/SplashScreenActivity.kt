package com.example.hostelmanagementsystem.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.ActionBar
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.ActivitySplashScreenBinding
import com.example.hostelmanagementsystem.onboarding.OnBoardingActivity

class SplashScreenActivity : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW,
            WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW
        )
//        val middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation)
//
//        val title = binding.title
//        title.animation = middleAnimation
        handler = Handler()
        handler.postDelayed({
            val startAct = Intent(this, OnBoardingActivity::class.java)
            startActivity(startAct)
            finish()
        }, 2000)



    }
}