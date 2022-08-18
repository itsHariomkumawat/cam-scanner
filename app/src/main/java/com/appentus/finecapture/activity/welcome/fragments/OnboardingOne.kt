package com.appentus.finecapture.activity.welcome.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appentus.finecapture.R
import com.appentus.finecapture.databinding.ActivityWelcomeBinding
import com.appentus.finecapture.databinding.FragmentOnboardingOneBinding

class OnboardingOne : Fragment() {

    lateinit var binding: FragmentOnboardingOneBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingOneBinding.inflate(layoutInflater)

        return binding.root
    }

}