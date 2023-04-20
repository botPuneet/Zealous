package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.zeolous.databinding.ActivityPersonilazation2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class personilazation2 : AppCompatActivity() {
    private lateinit var binding : ActivityPersonilazation2Binding
    private lateinit var mdef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonilazation2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        mdef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
        var items = listOf("Netaji Subhash University of Technologyy", "Delhi Technological University (Coming Soon) " ,
                 "Indira Gandhi Delhi Technical University for Women (Coming soon)" )

        var items2 = listOf("CSAI", "COE", "CSDS", "EE", "ECE", "EIOT", "IT", "ITNS", "ICE", "MCE", "ME", "BT" )

        var adapter = ArrayAdapter(this, R.layout.categories_drop_course,items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriesDrop.adapter = adapter

        var category1 : String?= "Others"
        binding.categoriesDrop.onItemSelectedListener = object :   AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category1  = items[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }



        }

        var adapter1 = ArrayAdapter(this, R.layout.categories_drop_course,items2)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriesDrop2.adapter = adapter1

        var category2 : String?= "Others"
        binding.categoriesDrop2.onItemSelectedListener = object :   AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category2  = items2[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }



        }

        binding.button7.setOnClickListener {
            mdef.child("college").setValue(category1)
            mdef.child("branch").setValue(category2)
            mdef.child("coins").setValue("50")
            mdef.child("coursesBN").setValue("0")
            var intent3 = Intent(this, enterRefferal::class.java)

            startActivity(intent3)
        }


        }


    }
