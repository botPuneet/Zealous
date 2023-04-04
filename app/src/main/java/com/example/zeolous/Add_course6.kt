package com.example.zeolous

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.objectiveAdapter
import com.example.zeolous.databinding.ActivityAddCourse6Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Add_course6 : AppCompatActivity() {
    private lateinit var binding_a6 : ActivityAddCourse6Binding
    private lateinit var dbms : DatabaseReference
    var items  = listOf<String>("").toMutableList()
    var index=0
    private lateinit var adapter1 : objectiveAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_a6 = ActivityAddCourse6Binding.inflate(layoutInflater)
        setContentView(binding_a6.root)
        val bundle : Bundle? = intent.extras
        val UIDC = bundle!!.getString("uidc")!!
        val position = bundle!!.getString("position")
        val category = bundle!!.getString("category")!!

        dbms = FirebaseDatabase.getInstance().getReference("courses").child(category).child(UIDC)
        dbms.child("content").child("T$position").get().addOnSuccessListener {
            val topic = it.child("topic").value.toString()
            binding_a6.textView12dfg.setText("Add  SubTopics of $topic ")
            binding_a6.textView13.setText("SubTopics of $topic ")
        }
        items = arrayListOf<String>()
        binding_a6.button8.setOnClickListener {
            val objective = binding_a6.editobjective.text.toString()
            binding_a6.editobjective.setText("")
            items.add(index, objective)
            index++

            val layoutManager2 = LinearLayoutManager(this )
            adapter1= objectiveAdapter(items as ArrayList<String>)
            binding_a6?.recyclerObjective?.layoutManager = layoutManager2
            binding_a6?.recyclerObjective?.setHasFixedSize(false)
            binding_a6?.recyclerObjective?.adapter = adapter1

        }
        var count = 0
        binding_a6.button9.setOnClickListener {
            for(i in items){
                dbms.child("subtopic").child("T$position").child("S$count").child("topic").setValue(i)
                count++
            }
            finish()
        }
    }
}