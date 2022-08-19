package com.appentus.finecapture.activity.welcome.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appentus.finecapture.R
import com.appentus.finecapture.databinding.FragmentOnboardingOneBinding
import com.appentus.finecapture.databinding.FragmentOnboardingTwoBinding

class OnboardingTwo : Fragment() {

    lateinit var binding: FragmentOnboardingTwoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingTwoBinding.inflate(layoutInflater)

        return binding.root
    }

}