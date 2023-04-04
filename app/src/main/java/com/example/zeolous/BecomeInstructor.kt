package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zeolous.databinding.ActivityBecomeInstructorBinding

class BecomeInstructor : AppCompatActivity() {
    private lateinit var binding : ActivityBecomeInstructorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBecomeInstructorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backCourse.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        binding.btnBecomeInstructor.setOnClickListener{
            val intent = Intent(this, instructor_stage_1::class.java)
            startActivity(intent)
        }

    }
}