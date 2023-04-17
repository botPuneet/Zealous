package com.example.zeolous

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.TopicAdapter
import com.example.zeolous.Adapter.objectiveAdapter
import com.example.zeolous.Models.Topic
import com.example.zeolous.R
import com.example.zeolous.databinding.ActivityViewCourse2Binding
import com.google.firebase.database.*

class viewCourse_2 : AppCompatActivity() {
    private lateinit var binding_v2 : ActivityViewCourse2Binding
    private lateinit var dbms : DatabaseReference

    var category : String?= null
    var UIDC : String?= null
    var index = 0
     private lateinit var adapter1 : objectiveAdapter
    var position1 : String?= null
    var items  = listOf<String>("").toMutableList()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding_v2 = ActivityViewCourse2Binding.inflate(layoutInflater)
        setContentView(binding_v2.root)
        val bundle : Bundle? = intent.extras
        UIDC = bundle!!.getString("uidc")!!
        category = bundle!!.getString("category")!!
        position1 = bundle!!.getString("position")!!
        dbms = FirebaseDatabase.getInstance().getReference("courses").child(category!!).child(UIDC!!)
        dbms.child("content").get().addOnSuccessListener {
            val tittle = it.child("T$position1").child("topic").value.toString()
            val article = it.child("T$position1").child("article").value.toString()
            binding_v2.topic555.text = tittle
            binding_v2.introA.text = article
        }
        val layoutManager2 = LinearLayoutManager(this@viewCourse_2 )
        binding_v2?.obRS?.layoutManager = layoutManager2
        binding_v2?.obRS?.setHasFixedSize(false)
        items = arrayListOf<String>()
           datafeed()





    }

    private fun datafeed() {
        dbms.child("subtopic").child("T$position1").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(topicShot in snapshot.children){
                        items.add(index,topicShot.child("topic").value.toString())
                        index++

                        adapter1= objectiveAdapter(items as ArrayList<String>)
                        binding_v2?.obRS?.adapter = adapter1

                    }

                    adapter1.setOnItemClickListener(object : objectiveAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            var intent = Intent(this@viewCourse_2, viewCourse_3::class.java)
                            intent.putExtra("uidc",UIDC)
                            intent.putExtra("category",category)
                            intent.putExtra("position2",position.toString())
                            intent.putExtra("position1",position1)
                            startActivity(intent)
                        }
                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}