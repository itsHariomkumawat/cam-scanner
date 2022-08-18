package com.appentus.finecapture.activity.welcome.fragments

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@SuppressLint("WrongConstant")
class FragmentAapter (
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                OnboardingOne()
            }
            1 -> {
                OnboardingTwo()
            }
            2 -> {
                OnboardingThree()
            }

            else -> OnboardingOne()
        }
    }

    override fun getCount() = 3
}