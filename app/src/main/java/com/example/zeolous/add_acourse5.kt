package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.TopicAdapter
import com.example.zeolous.Adapter.messageAdapter
import com.example.zeolous.Models.Topic
import com.example.zeolous.databinding.ActivityAddAcourse5Binding
import com.google.firebase.database.*

class add_acourse5 : AppCompatActivity() {
    private lateinit var binding_a5 : ActivityAddAcourse5Binding
    var items  = ArrayList<Topic>()
    var index=0
    var flag = false
    private lateinit var adapter1 : TopicAdapter
    private lateinit var dbms : DatabaseReference
    var UIDC : String?=null
    var category : String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_a5 = ActivityAddAcourse5Binding.inflate(layoutInflater)
        setContentView(binding_a5.root)

        val bundle : Bundle? = intent.extras
         UIDC = bundle!!.getString("uidc")!!
         category = bundle!!.getString("category")!!


        items = arrayListOf<Topic>()

        val layoutManager2 = LinearLayoutManager(this@add_acourse5 )
        binding_a5?.recyclerObjective?.layoutManager = layoutManager2
        binding_a5?.recyclerObjective?.setHasFixedSize(false)

        datafeed()
      binding_a5.button9.setOnClickListener {
          var intent = Intent(this, Add_course7::class.java)
          intent.putExtra("uidc",UIDC)
          intent.putExtra("category",category)
          startActivity(intent)
      }


    }

    private fun datafeed() {
        dbms = FirebaseDatabase.getInstance().getReference("courses").child(category!!).child(UIDC!!).child("content")
        dbms.addValueEventListener(object  : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){

                    for(topicShot in snapshot.children){
                        val topic1 = topicShot.getValue(Topic::class.java)
                        items.add(topic1!!)
                    }
                    adapter1= TopicAdapter(this@add_acourse5,items)
                    binding_a5?.recyclerObjective?.adapter =adapter1
                    adapter1.setOnItemClickListener(object : TopicAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            var intent = Intent(this@add_acourse5, Add_course6::class.java)
                            intent.putExtra("uidc",UIDC)
                            intent.putExtra("category",category)
                            intent.putExtra("position",position.toString())
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