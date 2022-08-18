package com.appentus.finecapture.activity.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appentus.finecapture.activity.welcome.fragments.FragmentAapter
import com.appentus.finecapture.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val  adapter = FragmentAapter(supportFragmentManager)
        binding.vpWelcome.adapter = adapter
       /* binding.tvStarted.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }*/
    }

}