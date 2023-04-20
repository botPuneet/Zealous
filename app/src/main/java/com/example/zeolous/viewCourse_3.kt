package com.example.zeolous

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.zeolous.Adapter.objectiveAdapter
import com.example.zeolous.databinding.ActivityViewCourse3Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class viewCourse_3 : AppCompatActivity() {
    private lateinit var binding_v3 : ActivityViewCourse3Binding
    private lateinit var dbms : DatabaseReference

    var category : String?= null
    var UIDC : String?= null

    private lateinit var adapter1 : objectiveAdapter
    var position1 : String?= null
    var position2 : String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_v3 = ActivityViewCourse3Binding.inflate(layoutInflater)
        setContentView(binding_v3.root)

        val bundle : Bundle? = intent.extras
        UIDC = bundle!!.getString("uidc")!!
        category = bundle!!.getString("category")!!
        position1 = bundle!!.getString("position1")!!
        position2 = bundle!!.getString("position2")!!
        dbms = FirebaseDatabase.getInstance().getReference("courses").child(category!!).child(UIDC!!)
        dbms.get().addOnSuccessListener {
            var title = it.child("title").value.toString()
            binding_v3.tittle.text = title
            var topic = it.child("subtopic").child("T$position1").child("S$position2").child("topic").value.toString()
            binding_v3.topic555.text = topic
            var Atopic = it.child("subtopicContent").child("T$position1").child("S$position2").child("article").value.toString()
            binding_v3.introA.text = Atopic
            var type = it.child("subtopicContent").child("T$position1").child("S$position2").child("type").value.toString()
            Toast.makeText(this@viewCourse_3, type,Toast.LENGTH_SHORT).show()
            if(type =="img"){
                binding_v3.imageView8.visibility = View.VISIBLE
                val content = it.child("subtopicContent").child("T$position1").child("S$position2").child("content").value.toString()
                Glide.with(this).load(content).into(binding_v3.imageView8)
            }

            if(type =="video"){
                binding_v3.lll.visibility = View.VISIBLE
                val content = it.child("subtopicContent").child("T$position1").child("S$position2").child("content").value.toString()
                var uri = Uri.parse(content)
                binding_v3.introvideoView.setVideoURI(uri)

                val mediaController = MediaController(this)
                binding_v3.introvideoView.setMediaController(mediaController)
                mediaController.setAnchorView( binding_v3.introvideoView)
            }

            if(type =="pdf"){
                val content = it.child("subtopicContent").child("T$position1").child("S$position2").child("content").value.toString()
                binding_v3.pdf.visibility = View.VISIBLE
               binding_v3.pdf.setOnClickListener {

                    val value = Uri.parse(content)
                val intent = Intent(Intent.ACTION_VIEW, value)
                  intent.type = "application/pdf"
                // start activity

                // start activity
                startActivity(Intent.createChooser(intent, "Open in"))}

            }

            if(type =="link"){


                val content = it.child("subtopicContent").child("T$position1").child("S$position2").child("content").value.toString()
                binding_v3.link.visibility = View.VISIBLE
                binding_v3.link.setOnClickListener {
                val value = Uri.parse(content)
                val intent = Intent(Intent.ACTION_VIEW, value)

                // start activity

                // start activity
                startActivity(intent)}

            }

        }
    }
}