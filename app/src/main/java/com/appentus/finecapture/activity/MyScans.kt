package com.appentus.finecapture.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.appentus.finecapture.R
import com.appentus.finecapture.adapter.AdapterRVScanFolder
import com.appentus.finecapture.databinding.ActivityMyScansBinding
import com.appentus.finecapture.databinding.ActivityWelcomeBinding

class MyScans : AppCompatActivity() {

    lateinit var binding:ActivityMyScansBinding
    lateinit var adapterRVScanFolder:AdapterRVScanFolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyScansBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterRVScanFolder = AdapterRVScanFolder(this)
        binding.rvMyScans.layoutManager = GridLayoutManager(this,3)
        binding.rvMyScans.adapter =adapterRVScanFolder
    }


}