package com.example.zeolous

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.zeolous.Adapter.onBoardItemAdapter
import com.example.zeolous.Models.onBoardItem
import com.example.zeolous.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import java.text.ParsePosition


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth :FirebaseAuth
    private lateinit var onboardItemAdapter: onBoardItemAdapter
    private lateinit var indicatorcontainer : LinearLayout
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        setContentView(binding.root)
        setonBOarditems()
        setupindicator()
        setupcurrentindicator(0)

        if(auth.currentUser!=null){
            startActivity(Intent(applicationContext,Home::class.java))
           finish()
        }

        binding.getStarted.setOnClickListener{
            navSignup()
        }


    }

    private fun setonBOarditems(){
        onboardItemAdapter = onBoardItemAdapter(
            listOf(
                onBoardItem(
                    onBoardImg = R.drawable.onboard1,
                    textTitle = "It's easy to acheive your goals",
                    textDescp = "learn more about our App"
                ),
                onBoardItem(
                    onBoardImg = R.drawable.onboard2,
                    textTitle = "Search and learn what interests",
                    textDescp = "over 1000 courses"
                ),
                onBoardItem(
                    onBoardImg = R.drawable.onboard3,
                    textTitle = "Practice and learn something new",
                    textDescp = "with this learning app"
                )

            )
        )
        val onboardviewpager = findViewById<ViewPager2>(R.id.viewPager2)
        onboardviewpager.adapter = onboardItemAdapter
        onboardviewpager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setupcurrentindicator(position)
                }
            }
        )
        (onboardviewpager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

    private fun setupindicator(){
        indicatorcontainer = findViewById(R.id.indicatorContainer)
        val indicator = arrayOfNulls<ImageView>(onboardItemAdapter.itemCount)
        val layoutParams : LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT , WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0,)
        for(i in indicator.indices){
            indicator[i] = ImageView(applicationContext)
            indicator[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indiacotor_inactive_onboard
                    )
                )
                it.layoutParams = layoutParams
                indicatorcontainer.addView(it)
            }
        }


    }

    private fun navSignup(){
        startActivity(Intent(applicationContext,Sign_up::class.java))
        finish()
    }

    private fun setupcurrentindicator(position: Int){
        val childcount  = indicatorcontainer.childCount
        for  (i in 0 until childcount){
                val imageView = indicatorcontainer.getChildAt(i) as ImageView
            if(i==position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indiacator_active_onboard
                    )
                )
            }else{imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.indiacotor_inactive_onboard
                )
            )

            }

        }
    }

}