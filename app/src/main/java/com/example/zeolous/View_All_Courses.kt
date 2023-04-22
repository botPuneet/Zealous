package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zeolous.Adapter.viewAllCourseAdapter
import com.example.zeolous.Models.Best_selling_course
import com.example.zeolous.Models.Topic
import com.example.zeolous.Models.viewAllCourses
import com.example.zeolous.databinding.ActivityViewAllCoursesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class View_All_Courses : AppCompatActivity() {
    private lateinit var binding_v : ActivityViewAllCoursesBinding
    private lateinit var  viewC : ArrayList<viewAllCourses>
    private lateinit var  viewC2 : ArrayList<viewAllCourses>
    private lateinit var adapter1 : viewAllCourseAdapter
    lateinit var Img_arr : Array<Int>
    lateinit var Tittle : Array<String>
    private lateinit var access : String
    private lateinit var dbms : DatabaseReference
    private lateinit var dbms2 : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_v = ActivityViewAllCoursesBinding.inflate(layoutInflater)
        setContentView(binding_v.root)
        val bundle : Bundle? = intent.extras
       access = bundle!!.getString("access")!!


        //creator
        if(access=="1") {
            dataIntializa()
        }

        //studentpanel
        if(access=="0"){
            dataIntializb()
        }
        //nsutpanel
        if(access=="3"){
           var Branch = bundle!!.getString("branch")!!

            dataIntializc(Branch)
        }


    }

    private fun dataIntializc(branch2 : String) {
        viewC = ArrayList()
        viewC2 = ArrayList()

        dbms2 = FirebaseDatabase.getInstance().getReference("courses")

        dbms2.child(branch2).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {

                    for (topicShot in snapshot.children) {

                        val Id= topicShot.child("uid").value.toString()
                        viewC2.add(viewAllCourses(branch2,Id))

                            val image = topicShot.child("courseImage").value.toString()
                            val title = topicShot.child("title").value.toString()

                            viewC.add(viewAllCourses(image,title))

                            binding_v.allCourses.setHasFixedSize(false)
                            binding_v.allCourses.layoutManager = GridLayoutManager(this@View_All_Courses,2)
                            adapter1 = viewAllCourseAdapter(this@View_All_Courses,viewC)
                            binding_v.allCourses.adapter = adapter1




                            adapter1.setOnItemClickListener(object  : viewAllCourseAdapter.onItemClickListener{
                                override fun onItemClick(position: Int) {
                                    val intent2  = Intent(this@View_All_Courses, viewCourse::class.java)
                                    intent2.putExtra("category",viewC2[position].image.toString())
                                    intent2.putExtra("Uid",viewC2[position].name.toString())
                                    intent2.putExtra("access", "0")

                                    startActivity(intent2)
                                }
                            })
                        }
                    }
                }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun dataIntializb() {
        viewC = ArrayList()
        viewC2 = ArrayList()
        dbms = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid.toString())
        dbms2 = FirebaseDatabase.getInstance().getReference("courses")

        dbms.child("coursesBN").get().addOnSuccessListener {
            var size = it.value.toString()
            if(size=="0"){
                binding_v.allCourses.visibility = View.GONE
                binding_v.nimg.visibility = View.VISIBLE
                binding_v.nText.visibility = View.VISIBLE

            }
        }

        dbms.child("coursesBought").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {

                    for (topicShot in snapshot.children) {
                        val category = topicShot.child("category").value.toString()
                        val Id= topicShot.child("uid").value.toString()
                        viewC2.add(viewAllCourses(category,Id))
                        dbms2.child(category).child(Id).get().addOnSuccessListener {
                            val image = it.child("courseImage").value.toString()
                            val title = it.child("title").value.toString()

                            viewC.add(viewAllCourses(image,title))

                            binding_v.allCourses.setHasFixedSize(false)
                            binding_v.allCourses.layoutManager = GridLayoutManager(this@View_All_Courses,2)
                            adapter1 = viewAllCourseAdapter(this@View_All_Courses,viewC)
                            binding_v.allCourses.adapter = adapter1

                            adapter1.setOnItemClickListener(object  : viewAllCourseAdapter.onItemClickListener{
                                override fun onItemClick(position: Int) {
                                    val intent  = Intent(this@View_All_Courses, viewCourse::class.java)
                                    intent.putExtra("category",viewC2[position].image.toString())
                                    intent.putExtra("Uid",viewC2[position].name.toString())
                                    intent.putExtra("access", "1.2")
                                    startActivity(intent)
                                }
                            })
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


    private fun dataIntializa(){

        viewC = ArrayList()
        viewC2 = ArrayList()
        dbms = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid.toString())
        dbms2 = FirebaseDatabase.getInstance().getReference("courses")

        dbms.child("courses").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {

                    for (topicShot in snapshot.children) {
                        val category = topicShot.child("Category").value.toString()
                        val Id= topicShot.child("ID").value.toString()
                        viewC2.add(viewAllCourses(category,Id))
                        dbms2.child(category).child(Id).get().addOnSuccessListener {
                            val image = it.child("courseImage").value.toString()
                            val title = it.child("title").value.toString()

                            viewC.add(viewAllCourses(image,title))

                            binding_v.allCourses.setHasFixedSize(false)
                            binding_v.allCourses.layoutManager = GridLayoutManager(this@View_All_Courses,2)
                            adapter1 = viewAllCourseAdapter(this@View_All_Courses,viewC)
                            binding_v.allCourses.adapter = adapter1

                            adapter1.setOnItemClickListener(object  : viewAllCourseAdapter.onItemClickListener{
                                override fun onItemClick(position: Int) {
                                    val intent  = Intent(this@View_All_Courses, viewCourse::class.java)
                                    intent.putExtra("category",viewC2[position].image.toString())
                                    intent.putExtra("Uid",viewC2[position].name.toString())
                                    intent.putExtra("access", "1")
                                    startActivity(intent)
                                }
                            })
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}