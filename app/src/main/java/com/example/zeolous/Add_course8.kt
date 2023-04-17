package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.TopicAdapter
import com.example.zeolous.Models.Topic
import com.example.zeolous.R
import com.example.zeolous.databinding.ActivityAddCourse8Binding
import com.google.firebase.database.*

class Add_course8 : AppCompatActivity() {
    private lateinit var binding_a8 : ActivityAddCourse8Binding
    private lateinit var dbms : DatabaseReference
    var items  = ArrayList<Topic>()
    var index=0
    var flag = false
    var tposition : String?= null
    var UIDC : String?= null
    var category : String?= null
    private lateinit var adapter1 : TopicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_a8 = ActivityAddCourse8Binding.inflate(layoutInflater)
        setContentView(binding_a8.root)

        val bundle : Bundle? = intent.extras
         UIDC = bundle!!.getString("uidc")!!
       tposition = bundle!!.getString("position")
       category = bundle!!.getString("category")!!

        items = arrayListOf<Topic>()

        val layoutManager2 = LinearLayoutManager(this@Add_course8 )
        binding_a8?.recyclerObjectivenn?.layoutManager = layoutManager2
        binding_a8?.recyclerObjectivenn?.setHasFixedSize(false)
        dbms = FirebaseDatabase.getInstance().getReference("courses").child(category!!).child(UIDC!!)
        dbms.get().addOnSuccessListener {
            val topic = it.child("content").child("T$tposition").child("topic").value.toString()
            binding_a8.textView12fdg.setText("Write Introduction Airticle for the topic($topic)")
            binding_a8.tittlen.setText("$topic")
        }
        datafeed()
   binding_a8.button9.setOnClickListener {
       dbms.child("content").child("T$tposition").child("article").setValue(binding_a8.editTextTextMultiLine.text.toString())
       finish()
   }
    }



    private fun datafeed() {

        dbms.child("subtopic").child("T$tposition").addValueEventListener(object  : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){

                    for(topicShot in snapshot.children){
                        val topic1 = topicShot.child("topic").value.toString()
                        items.add(Topic(topic1!!))
                    }
                    adapter1= TopicAdapter(this@Add_course8,items)
                    binding_a8?.recyclerObjectivenn?.adapter =adapter1
                    adapter1.setOnItemClickListener(object : TopicAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                              val intent  = Intent(this@Add_course8, add_course9::class.java)
                            intent.putExtra("uidc",UIDC)
                            intent.putExtra("category",category)
                            intent.putExtra("sPosition", position.toString())
                            intent.putExtra("tPosition", tposition)
                            startActivity(intent)

                        }
                    }
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}