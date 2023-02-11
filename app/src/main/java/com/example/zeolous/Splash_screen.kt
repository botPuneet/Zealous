package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.zeolous.databinding.ActivitySignInBinding
import com.example.zeolous.databinding.ActivitySplashScreenBinding

class Splash_screen : AppCompatActivity() {

    val splashScreen =1200

    private lateinit var bindind : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(bindind.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)




       Handler().postDelayed({
           var intent3 = Intent(this, MainActivity::class.java)

           startActivity(intent3)
           finish()
       },splashScreen.toLong())


    }
}