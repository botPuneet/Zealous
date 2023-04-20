package com.example.zeolous

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zeolous.databinding.ActivityWelcomeBinding

class Welcome : AppCompatActivity() {
    lateinit var preferences: SharedPreferences
    private lateinit var binding_w : ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_w = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding_w.root)
        preferences = getSharedPreferences("userData", Context.MODE_PRIVATE)
        val name = preferences.getString("username", "")


        binding_w.textView4.setText("Hello $name!")


        binding_w.button.setOnClickListener{

            var intent3 = Intent(this, Home::class.java)


            startActivity(intent3)
        }

        }


    }


