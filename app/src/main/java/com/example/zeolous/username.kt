package com.example.zeolous

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zeolous.databinding.ActivityUsernameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class username : AppCompatActivity() {
    private lateinit var binding : ActivityUsernameBinding
    private lateinit var dbms : DatabaseReference
    private lateinit var dbms2 : DatabaseReference
    private lateinit var auth : FirebaseAuth
    lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbms = FirebaseDatabase.getInstance().getReference("username")
        dbms2 = FirebaseDatabase.getInstance().getReference("Users")
       auth = FirebaseAuth.getInstance()
        binding.button.setOnClickListener {
            val name = binding.imageView.text.toString()
            dbms.child(name!!).get().addOnSuccessListener {
                if(it.value=="true"){
                    Toast.makeText(this,"Sorry Already taken",Toast.LENGTH_SHORT).show()
                }
               else{ dbms.child(name!!).setValue("true")
                    preferences = this.getSharedPreferences("userData", Context.MODE_PRIVATE)
                    val editor : SharedPreferences.Editor = preferences.edit()
                    editor.putString("username",name)
                    editor.apply()
                    dbms2.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid()).child("username").setValue(name)
                var intent3 = Intent(this,personilazation2::class.java)
                startActivity(intent3)

            }}
        }

    }
}