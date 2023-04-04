package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zeolous.databinding.ActivityInstructorStage2Binding

class instructor_stage2 : AppCompatActivity() {
    private lateinit var binding : ActivityInstructorStage2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityInstructorStage2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button6.setOnClickListener {
            val intent = Intent(this,instructor_stage3::class.java )
            startActivity(intent)
        }
    }
}