package com.example.helpyourneighbor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.viewpager.widget.ViewPager
import com.example.helpyourneighbor.adapters.PageAdapter
import com.google.android.material.tabs.TabLayout

class MyAccountActivity : AppCompatActivity() {

    private lateinit var viewPager : ViewPager
    private lateinit var tabLayout : TabLayout
    private lateinit var back_btn : ImageButton
    private val adapter by lazy { PageAdapter(supportFragmentManager) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        back_btn = findViewById(R.id.back_imgButton)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        back_btn.setOnClickListener(){

            val intent = Intent(this,HomePageActivity::class.java)
            startActivity(intent)
        }
    }
}