package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zeolous.databinding.ActivityInstructorStage1Binding
import com.example.zeolous.databinding.ActivityInstructorStage2Binding

class instructor_stage_1 : AppCompatActivity() {
    private lateinit var binding : ActivityInstructorStage1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructorStage1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button6.setOnClickListener {
            val intent = Intent(this,instructor_stage2::class.java )
            startActivity(intent)
        }
    }
}