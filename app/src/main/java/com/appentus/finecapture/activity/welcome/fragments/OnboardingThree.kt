package com.appentus.finecapture.activity.welcome.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appentus.finecapture.R
import com.appentus.finecapture.databinding.FragmentOnboardingThreeBinding
import com.appentus.finecapture.databinding.FragmentOnboardingTwoBinding

class OnboardingThree : Fragment() {

    lateinit var binding:FragmentOnboardingThreeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingThreeBinding.inflate(layoutInflater)

        return binding.root
    }

}