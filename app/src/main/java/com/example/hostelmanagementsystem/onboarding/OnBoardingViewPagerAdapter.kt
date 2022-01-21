package com.example.hostelmanagementsystem.onboarding

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hostelmanagementsystem.R

class OnBoardingViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val context: Context
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnBoardingFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_1),
                context.resources.getString(R.string.description_onboarding_1)
//                R.raw.lottie_splash_animation
            )
            1 -> OnBoardingFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_2),
                context.resources.getString(R.string.description_onboarding_2)
//                R.raw.lottie_study
            )
            else -> OnBoardingFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_3),
                context.resources.getString(R.string.description_onboarding_3)
//                R.raw.lottie_lecture
            )
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}