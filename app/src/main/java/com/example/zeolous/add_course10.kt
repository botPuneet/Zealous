package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zeolous.databinding.ActivityAddCourse10Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class add_course10 : AppCompatActivity() {
    private lateinit var binding_a10 : ActivityAddCourse10Binding
    private lateinit var dbms : DatabaseReference
    var UIDC : String?=null
    var category : String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_a10 = ActivityAddCourse10Binding.inflate(layoutInflater)
        setContentView(binding_a10.root)
        val bundle : Bundle? = intent.extras
        UIDC = bundle!!.getString("uidc")!!
        category = bundle!!.getString("category")!!

        dbms = FirebaseDatabase.getInstance().getReference("courses").child(category!!).child(UIDC!!)



        binding_a10.button9.setOnClickListener {
            dbms.child("price").setValue(binding_a10.title.text.toString())
            dbms.child("discount").setValue(binding_a10.titletr.text.toString())
            dbms.child("rating").setValue("0")

            val intent  = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }
}