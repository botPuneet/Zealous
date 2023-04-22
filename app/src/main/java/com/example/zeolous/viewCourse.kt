package com.example.zeolous

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.PopupMenu
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.objectiveAdapter
import com.example.zeolous.Adapter.topicX
import com.example.zeolous.Models.Top_course
import com.example.zeolous.Models.Topic
import com.example.zeolous.Models.nestedTopicX
import com.example.zeolous.databinding.ActivityViewCourseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class viewCourse : AppCompatActivity() {
    private lateinit var dbms : DatabaseReference
    private lateinit var dbms2 : DatabaseReference
    private lateinit var dbms3 : DatabaseReference
    private lateinit var dbms4 : DatabaseReference
    private lateinit var binding_v: ActivityViewCourseBinding
    var items  = listOf<String>("").toMutableList()
    var index=0
    var items21  = ArrayList<nestedTopicX>()
    var itemsTs  = ArrayList<Topic>()
    var index2=0
    var category : String?= null
    var UIDC : String?= null
    var accss : String?= null
    private lateinit var adapter1 : objectiveAdapter
    private lateinit var adapter2 : topicX
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_v = ActivityViewCourseBinding.inflate(layoutInflater)
        setContentView(binding_v.root)


        val bundle : Bundle? = intent.extras
        UIDC = bundle!!.getString("Uid")!!
         category = bundle!!.getString("category")!!
        accss = bundle!!.getString("access")!!
        dbms = FirebaseDatabase.getInstance().getReference("courses")
        dbms2 = FirebaseDatabase.getInstance().getReference("Users")
        dbms3 = FirebaseDatabase.getInstance().getReference("topCourses")
        dbms4 = FirebaseDatabase.getInstance().getReference("coursesStudents")

        val layoutManager2 = LinearLayoutManager(this )
        items = arrayListOf<String>()
        items21 = arrayListOf<nestedTopicX>()

        binding_v?.obR?.layoutManager = layoutManager2
        binding_v?.obR?.setHasFixedSize(false)

        binding_v?.tpR?.layoutManager =LinearLayoutManager(this )
        binding_v?.tpR?.setHasFixedSize(true)
        if(accss=="1" || accss=="1.2") {
            binding_v.button3.setOnClickListener {


                val popmenu = PopupMenu(this@viewCourse, it)
                popmenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.ratingdd -> {
                            val dialogBinding = layoutInflater.inflate(R.layout.rrating, null)
                            val myDialog = Dialog(this)
                            myDialog.setContentView(dialogBinding)
                            myDialog.setCancelable(true)
                            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            myDialog.show()

                            val btnn = dialogBinding.findViewById<Button>(R.id.confirm2r)
                            val rating2 = dialogBinding.findViewById<RatingBar>(R.id.ratingBarR)

                            btnn.setOnClickListener {
                                dbms.child(category!!).child(UIDC!!).get().addOnSuccessListener {
                                    var loc = it.child("ratingR").value.toString()
                                    dbms3.child(loc).removeValue()
                                    var rating = it.child("rating").value.toString()
                                    var ratingI = Integer.parseInt(rating)
                                    var Rrating = rating2.rating



                                    var rrrr = (ratingI + Rrating) / 2


                                    dbms.child(category!!).child(UIDC!!).child("rating")
                                        .setValue(rrrr.toInt().toString())
                                    Toast.makeText(this, "Rated : $Rrating", Toast.LENGTH_SHORT)
                                        .show()

                                   var value = rrrr.toString().replace(".","")
                                    dbms.child(category!!).child(UIDC!!).child("ratingR")
                                        .setValue(value)
                                    var gotc = it.getValue(Top_course::class.java)
                                    dbms3.child(value).setValue(gotc)

                                    myDialog.dismiss()



                                }

                            }
                            true
                        }
                        R.id.report -> {
                            dbms.child(category!!).child(UIDC!!).get().addOnSuccessListener {
                                var uid = it.child("cUID").value.toString()
                                var intent3 = Intent(this@viewCourse,Chatting::class.java)
                                intent3.putExtra("UID",uid)
                                startActivity(intent3)

                            }

                            true
                        }
                        else -> false
                    }
                }
                popmenu.inflate(R.menu.course_menu)
                try {
                    val fieldpop = PopupMenu::class.java.getDeclaredField("mPopup")
                    fieldpop.isAccessible = true
                    val mPopup = fieldpop.get(popmenu)
                    mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                        .invoke(mPopup, true)

                } catch (e: java.lang.Exception) {

                } finally {
                    popmenu.show()
                }
            }
        }

        if(accss=="0")
        {
            binding_v.buttonBuy.setOnClickListener{
                 val dialogBinding = layoutInflater.inflate(R.layout.buy_confirm, null)
                val myDialog = Dialog(this)
                myDialog.setContentView(dialogBinding)
                myDialog.setCancelable(true)
                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog.show()

                val btnn = dialogBinding.findViewById<Button>(R.id.confirm)

                btnn.setOnClickListener {
                    dbms4.child(category!!).child(UIDC!!).child("students").child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {

                            var vlu  = it.value

                        if(vlu==true){
                            Toast.makeText(this, "Already Bought this course", Toast.LENGTH_SHORT).show()

                        } else{
                            dbms2.child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                                val coind = it.child("coins").value.toString()
                                val coins = Integer.parseInt(coind)

                                val coursesd = it.child("coursesBN").value.toString()
                                var courses = Integer.parseInt(coursesd)
                                courses++


                                if(coins>=30){
                                    dbms.child(category!!).child(UIDC!!).get().addOnSuccessListener {
                                        val enroll = it.child("enroll").value.toString()
                                        var enrolls = Integer.parseInt(enroll)
                                        enrolls++
                                        dbms4.child(category!!).child(UIDC!!).child("students").child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(true)
                                        dbms.child(category!!).child(UIDC!!).child("enroll").setValue(enrolls.toString())
                                    }
                                    Toast.makeText(this, "Course Bought successfully", Toast.LENGTH_SHORT).show()
                                    dbms2.child(FirebaseAuth.getInstance().currentUser!!.uid).child("coins").setValue((coins-30).toString())
                                    dbms2.child(FirebaseAuth.getInstance().currentUser!!.uid).child("coursesBought").child("C$courses").child("uid")
                                        .setValue(UIDC)
                                    dbms2.child(FirebaseAuth.getInstance().currentUser!!.uid).child("coursesBought").child("C$courses").child("category")
                                        .setValue(category)
                                    dbms2.child(FirebaseAuth.getInstance().currentUser!!.uid).child("coursesBN").setValue(courses.toString())

                                }else{
                                    Toast.makeText(this, "Earn more coins to buy this course", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                           }

            }}
        }

        if(accss=="1.2"){
            binding_v.buttonBuy.visibility = View.GONE

        }

        dbms.child(category!!).child(UIDC!!).get().addOnSuccessListener {
            var tittle = it.child("title").value.toString()
            var iintro = it.child("introArticle").value.toString()
            var iintroV = it.child("introVideo").value.toString()
            var access = it.child("access").value.toString()
            var creator = it.child("creator").value.toString()
            var descp = it.child("description").value.toString()
            var enroll = it.child("enroll").value.toString()
            var rating = it.child("rating").value.toString()
            val no = it.child("topicCount").value.toString()

            var count = Integer.parseInt(no)
            binding_v.tittle.text = tittle
            binding_v.introA.text =iintro
            binding_v.creator.text =creator
            binding_v.descp.text =descp
            binding_v.students.text =enroll
            binding_v.rating.text =rating
            var uri = Uri.parse(iintroV)


            binding_v.introvideoView.setVideoURI(uri)
            binding_v.introvideoView.start()
            val mediaController = MediaController(this)
            binding_v.introvideoView.setMediaController(mediaController)
            mediaController.setAnchorView( binding_v.introVlayout)

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
                                  // objective adpater to avoid bug
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
                        @SuppressLint("SuspiciousIndentation")
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()){
                                for(topicShot in snapshot.children){


                                    val topic1 = topicShot.child("topic").getValue().toString()

                                    items2.add(Topic(topic1!!))


                                }
                                items21.add(nestedTopicX(it.child("content").child("T$i").child("topic").value.toString(), items2, false))
                                val adapter = topicX(items21)
                                binding_v.tpR.adapter = adapter

                                    adapter.setOnItemClickListener(object :
                                        topicX.onItemClickListener {
                                        override fun onItemClick(position: Int) {
                                            if(accss=="1" || accss=="1.2") {
                                                val intent =
                                                    Intent(
                                                        this@viewCourse,
                                                        viewCourse_2::class.java
                                                    )
                                                intent.putExtra("uidc", UIDC)
                                                intent.putExtra("category", category)
                                                intent.putExtra("position", position.toString())
                                                startActivity(intent)
                                            }
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