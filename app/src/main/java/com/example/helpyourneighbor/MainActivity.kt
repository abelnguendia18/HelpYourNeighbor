package com.example.helpyourneighbor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var topAnim : Animation
    private lateinit var bottomAnim : Animation
    private val SPLASH_SCREEN_TIMER: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        // One way to hide the ActionBar(ColorPrimary and ColorPrimaryDark in styles.xml)
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        //we assign each animation to his view
        imageView_start_application_logo.startAnimation(topAnim)
        textView_help_your_Neighbour.startAnimation(bottomAnim)
        textView_we_are_in_solidarity.startAnimation(bottomAnim)

        Handler().postDelayed( {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_TIMER)

      }
}
