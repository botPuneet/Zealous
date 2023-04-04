package com.example.zeolous

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.zeolous.databinding.ActivityInstructorStage3Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class instructor_stage3 : AppCompatActivity() {
    private lateinit var binding : ActivityInstructorStage3Binding
    private lateinit var database: DatabaseReference
    private lateinit var autth : FirebaseAuth
    lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstructorStage3Binding.inflate(layoutInflater)
        autth = FirebaseAuth.getInstance()
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("Users")

        preferences = this.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val UID = preferences.getString("Uid","")
        binding.button6.setOnClickListener {
            database.child(UID!!).child("type").setValue("Instructor")
            database.child(UID!!).child("courseNumber").setValue("0")
            val intent = Intent(this,Home::class.java )
            startActivity(intent)
        }
    }
}