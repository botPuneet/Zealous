package com.example.zeolous

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.objectiveAdapter
import com.example.zeolous.Adapter.topicX
import com.example.zeolous.Models.Topic
import com.example.zeolous.Models.nestedTopicX
import com.example.zeolous.databinding.ActivityViewCourseBinding
import com.google.firebase.database.*

class viewCourse : AppCompatActivity() {
    private lateinit var dbms : DatabaseReference
    private lateinit var binding_v: ActivityViewCourseBinding
    var items  = listOf<String>("").toMutableList()
    var index=0
    var items21  = ArrayList<nestedTopicX>()
    var itemsTs  = ArrayList<Topic>()
    var index2=0
    var category : String?= null
    var UIDC : String?= null
    private lateinit var adapter1 : objectiveAdapter
    private lateinit var adapter2 : topicX
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_v = ActivityViewCourseBinding.inflate(layoutInflater)
        setContentView(binding_v.root)


        val bundle : Bundle? = intent.extras
        UIDC = bundle!!.getString("Uid")!!
         category = bundle!!.getString("category")!!
        dbms = FirebaseDatabase.getInstance().getReference("courses")

        val layoutManager2 = LinearLayoutManager(this )
        items = arrayListOf<String>()
        items21 = arrayListOf<nestedTopicX>()

        binding_v?.obR?.layoutManager = layoutManager2
        binding_v?.obR?.setHasFixedSize(false)

        binding_v?.tpR?.layoutManager =LinearLayoutManager(this )
        binding_v?.tpR?.setHasFixedSize(true)

        dbms.child(category!!).child(UIDC!!).get().addOnSuccessListener {
            var tittle = it.child("title").value.toString()
            var iintro = it.child("introArticle").value.toString()
            var iintroV = it.child("introVideo").value.toString()
            var access = it.child("access").value.toString()
            val no = it.child("topicCount").value.toString()
            var count = Integer.parseInt(no)
            binding_v.tittle.text = tittle
            binding_v.introA.text =iintro
            var uri = Uri.parse(iintroV)
            binding_v.introvideoView.setVideoURI(uri)
            binding_v.introvideoView.start()
            val mediaController = MediaController(this)
            binding_v.introvideoView.setMediaController(mediaController)
            mediaController.setAnchorView( binding_v.introvideoView)

            if(access == "0") {
                binding_v.access.text = "Unlimited access"
            }else{
                binding_v.access.text = "$access weeks"
            }

            dbms.child(category!!).child(UIDC!!).child("Objective").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        for(topicShot in snapshot.children){
                        items.add(index,topicShot.value.toString())
                            index++

                            adapter1= objectiveAdapter(items as ArrayList<String>)
                            binding_v?.obR?.adapter = adapter1
                            adapter1.setOnItemClickListener(object : objectiveAdapter.onItemClickListener{
                                override fun onItemClick(position: Int) {

                                }
                            })
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })




            dbms.child(category!!).child(UIDC!!).get().addOnSuccessListener {
                val no = it.child("topicCount").value.toString()
                var count = Integer.parseInt(no)
                for(i in 0..count-1){

                    var items2  = ArrayList<Topic>()
                    items2 = arrayListOf<Topic>()
                    dbms.child(category!!).child(UIDC!!).child("subtopic").child("T$i").addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()){
                                for(topicShot in snapshot.children){


                                    val topic1 = topicShot.child("topic").getValue().toString()

                                    items2.add(Topic(topic1!!))


                                }
                                items21.add(nestedTopicX(it.child("content").child("T$i").child("topic").value.toString(), items2, false))
                                val adapter = topicX(items21)
                                binding_v.tpR.adapter = adapter
                                adapter.setOnItemClickListener(object : topicX.onItemClickListener{
                                    override fun onItemClick(position: Int) {
                                        val intent  = Intent(this@viewCourse, viewCourse_2::class.java)
                                        intent.putExtra("uidc",UIDC)
                                        intent.putExtra("category",category)
                                        intent.putExtra("position",position.toString())
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


        }
}


}