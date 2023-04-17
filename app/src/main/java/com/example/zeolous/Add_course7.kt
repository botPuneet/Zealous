package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.NestedTopicAdapter
import com.example.zeolous.Models.Topic
import com.example.zeolous.Models.nestedTopic
import com.example.zeolous.databinding.ActivityAddCourse7Binding
import com.google.firebase.database.*

class Add_course7 : AppCompatActivity() {

    var items = ArrayList<nestedTopic>()
    private lateinit var binding_a7 : ActivityAddCourse7Binding
    var UIDC : String?=null
    var category : String?=null
    private lateinit var dbms : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_a7 = ActivityAddCourse7Binding.inflate(layoutInflater)
        setContentView(binding_a7.root)
        val bundle : Bundle? = intent.extras
        UIDC = bundle!!.getString("uidc")!!
        category = bundle!!.getString("category")!!
        items = arrayListOf<nestedTopic>()

        binding_a7.recyclerObjective.setHasFixedSize(true)
        binding_a7.recyclerObjective.layoutManager = LinearLayoutManager(this)

        datafeed()

        binding_a7.button9.setOnClickListener {
            var intent = Intent(this, add_course10::class.java)
            intent.putExtra("uidc",UIDC)
            intent.putExtra("category",category)

            startActivity(intent)
        }


    }

    private fun datafeed() {
        dbms = FirebaseDatabase.getInstance().getReference("courses").child(category!!).child(UIDC!!)

        dbms.get().addOnSuccessListener {
            val no = it.child("topicCount").value.toString()
            var count = Integer.parseInt(no)
                 for(i in 0..count-1){

                     var items2  = ArrayList<Topic>()
                     items2 = arrayListOf<Topic>()
                     dbms.child("subtopic").child("T$i").addValueEventListener(object : ValueEventListener{
                         override fun onDataChange(snapshot: DataSnapshot) {
                             if(snapshot.exists()){
                                 for(topicShot in snapshot.children){


                                     val topic1 = topicShot.child("topic").getValue().toString()

                                     items2.add(Topic(topic1!!))


                                 }
                                 items.add(nestedTopic(it.child("content").child("T$i").child("topic").value.toString(), items2))
                                 val adapter = NestedTopicAdapter(this@Add_course7,items)
                                 binding_a7.recyclerObjective.adapter = adapter
                                 adapter.setOnItemClickListener(object : NestedTopicAdapter.onItemClickListener{
                                     override fun onItemClick(position: Int) {
                                         val intent  = Intent(this@Add_course7, Add_course8::class.java)
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

    }
}