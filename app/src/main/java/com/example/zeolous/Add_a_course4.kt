package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.objectiveAdapter
import com.example.zeolous.R
import com.example.zeolous.databinding.ActivityAddAcourse2Binding
import com.example.zeolous.databinding.ActivityAddAcourse4Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Add_a_course4 : AppCompatActivity() {
    private lateinit var binding_a2 : ActivityAddAcourse4Binding
    var items  = listOf<String>("").toMutableList()
    var index=0
    private lateinit var adapter1 : objectiveAdapter
    private lateinit var dbms : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_a2 = ActivityAddAcourse4Binding.inflate(layoutInflater)
        setContentView(binding_a2.root)

        val bundle : Bundle? = intent.extras
        val UIDC = bundle!!.getString("uidc")!!
        val category = bundle!!.getString("category")!!
        dbms = FirebaseDatabase.getInstance().getReference("courses")
        items = arrayListOf<String>()
        binding_a2.button8.setOnClickListener {
            val objective = binding_a2.editobjective.text.toString()
            binding_a2.editobjective.setText("")
            items.add(index, objective)
            index++

            val layoutManager2 = LinearLayoutManager(this )
            adapter1= objectiveAdapter(items as ArrayList<String>)
            binding_a2?.recyclerObjective?.layoutManager = layoutManager2
            binding_a2?.recyclerObjective?.setHasFixedSize(false)
            binding_a2?.recyclerObjective?.adapter = adapter1

        }
        val size1 = items.size
        var count = 0
        binding_a2.button9.setOnClickListener {

            for(i in items){
                dbms.child(category).child(UIDC).child("content").child("T$count").child("topic").setValue(i)
                count++
            }
            dbms.child(category).child(UIDC).child("topicCount").setValue(count)

            var intent = Intent(this, add_acourse5::class.java)
            intent.putExtra("uidc",UIDC)
            intent.putExtra("category",category)
            startActivity(intent)
        }



    }
}