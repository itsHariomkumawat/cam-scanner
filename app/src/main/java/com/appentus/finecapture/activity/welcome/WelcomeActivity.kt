package com.appentus.finecapture.activity.welcome

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.appentus.finecapture.activity.MainActivity
import com.appentus.finecapture.activity.welcome.fragments.FragmentAapter
import com.appentus.finecapture.activity.welcome.fragments.OnboardingTwo
import com.appentus.finecapture.databinding.ActivityWelcomeBinding
import com.appentus.finecapture.R


class WelcomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding
    var pos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val  adapter = FragmentAapter(supportFragmentManager)
        binding.vpWelcome.adapter = adapter
        binding.dotsIndicator.setViewPager(binding.vpWelcome)

        binding.vpWelcome.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                pos = position
                if (position == 2){
                    binding.txtNextBtn.text = "Get Started"
                }
                else{
                    binding.txtNextBtn.text = "Next"
                }


            }
            override fun onPageScrollStateChanged(state: Int) {
            }

        })

        binding.txtNextBtn.setOnClickListener(View.OnClickListener {
            if (pos == 2){
                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                startActivity(intent)
            }else{
                binding.vpWelcome.setCurrentItem(binding.vpWelcome.currentItem + 1, true)

            }

        })

    }

}