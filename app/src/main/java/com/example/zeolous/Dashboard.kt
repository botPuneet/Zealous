package com.example.zeolous

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.Horizontal_RecyclerView
import com.example.zeolous.Adapter.recom_adapter
import com.example.zeolous.Models.*
import com.example.zeolous.databinding.FragmentDashboardBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class Dashboard : Fragment() {
    lateinit var preferences: SharedPreferences
    private var topCourse = ArrayList<Top_course>()
    private lateinit var viewModel : topCourseViewModel

    private  var binding2: FragmentDashboardBinding? = null
    private  val binding get() = binding2!!
    private lateinit var database_reference: DatabaseReference
    private  lateinit var database : DatabaseReference
    private lateinit var dbms : DatabaseReference
    private lateinit var storage  : FirebaseStorage
    private lateinit var  imageUri : Uri
    private lateinit var dialog: AlertDialog.Builder
         var personlization1= ""
     var coins = ""
      private lateinit var TC : ArrayList<Top_course>

   private lateinit var adapter_recom1 : recom_adapter
    private lateinit var adapter_recom2 : recom_adapter
    private  lateinit var top_corse_array : ArrayList<Top_course>
    lateinit var Img_arr : Array<Int>
    lateinit var Tittle : Array<String>
    private var count = 0




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding2 = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding2!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = this.requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val name = preferences.getString("First_name", "")
        val Uname = preferences.getString("username", "")
        val Profile = preferences.getString("profile", "")
        val UID = preferences.getString("Uid","")
        val nflag = preferences.getString("vFlag","")


             // profile pic image
        val storageRef =  FirebaseStorage.getInstance().reference.child("Profile").child(UID!!)
        val localfile = File.createTempFile("temp",".jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding2?.image2?.setImageBitmap(bitmap)
        }


        binding2?.textView10?.setText("Hello\n$Uname!")


        database_reference = FirebaseDatabase.getInstance().getReference("Users")
        database = FirebaseDatabase.getInstance().getReference("courses")

       binding2!!.button5.setOnClickListener{
      var intent  = Intent(this.requireContext(), Wallet::class.java)
      startActivity(intent)
  }
















        val layoutManager2 = LinearLayoutManager(this@Dashboard.requireContext(), LinearLayoutManager.HORIZONTAL,false )
        adapter_recom1 = recom_adapter(this@Dashboard.requireContext() )
        binding2?.topCoursePersona1?.layoutManager = layoutManager2
        binding2?.topCoursePersona1?.setHasFixedSize(true)
        binding2?.topCoursePersona1?.adapter = adapter_recom1

        binding2!!.studentPanel.setOnClickListener{
            val intent  = Intent(this@Dashboard.requireContext(), View_All_Courses::class.java)
            intent.putExtra("access", "0")
            startActivity(intent)
        }

        binding2!!.personal3.setOnClickListener {
            val intent  = Intent(this@Dashboard.requireContext(), nsutPanel::class.java)

            startActivity(intent)
        }

        adapter_recom1.setOnItemClickListener(object  : recom_adapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                super.onItemClick(position)
                val intent  = Intent(this@Dashboard.requireContext(), viewCourse::class.java)
                intent.putExtra("category",personlization1)
                intent.putExtra("Uid",topCourse[position].uid.toString())
                intent.putExtra("access", "0")
                startActivity(intent)
            }
        })


        viewModel = ViewModelProvider(this).get(topCourseViewModel::class.java)

        viewModel.allUsers.observe(viewLifecycleOwner, Observer {

            adapter_recom1.updateUserList(it)

        })
        topCourse = arrayListOf<Top_course>()

        database_reference.child(UID.toString()).get().addOnSuccessListener{
            personlization1 = it.child("branch").value.toString()
            coins = it.child("coins").value.toString()
           if(nflag=="0"){

                val dialogBinding = layoutInflater.inflate(R.layout.congo1, null)
                val myDialog = Dialog(this@Dashboard.requireContext())
                myDialog.setContentView(dialogBinding)
                myDialog.setCancelable(true)
                myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                myDialog.show()

                val btnn = dialogBinding.findViewById<TextView>(R.id.coinsW)
               val btnn2 = dialogBinding.findViewById<TextView>(R.id.confirm)
               btnn2.setOnClickListener{
                   myDialog.dismiss()
               }
                btnn.text = coins
               val editor : SharedPreferences.Editor = preferences.edit()
               editor.putString("vFlag","1")
               editor.apply()

            }
            database.child(personlization1).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   topCourse.clear()
                    if(snapshot.exists()){
                        for (usersnapshot in snapshot.children){
                            val user = usersnapshot.getValue(Top_course::class.java)
                            topCourse.add(user!!)
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



            binding2?.persona1?.setText("Top courses of $personlization1")
//            binding2?.persona2?.setText("Top courses of $personlization2")
//            binding2?.persona3?.setText("Top courses of $personlization3")
//            binding2?.persona4?.setText("Top courses of $personlization4")
//            binding2?.persona5?.setText("Top courses of $personlization5")

        }


//        val layoutManager3 = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL,false )
//        adapter_recom2 = recom_adapter(top_corse_array)
//        binding2?.topCoursePersona2?.layoutManager = layoutManager3
//        binding2?.topCoursePersona2?.setHasFixedSize(true)
//        binding2?.topCoursePersona2?.adapter = adapter_recom2
//
//
//        val layoutManager4 = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL,false )
//        adapter_recom1 = recom_adapter(top_corse_array)
//        binding2?.topCoursePersona3?.layoutManager = layoutManager4
//        binding2?.topCoursePersona3?.setHasFixedSize(true)
//        binding2?.topCoursePersona3?.adapter = adapter_recom1
//
//
//        val layoutManager5 = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL,false )
//        adapter_recom1 = recom_adapter(top_corse_array)
//        binding2?.topCoursePersona4?.layoutManager = layoutManager5
//        binding2?.topCoursePersona4?.setHasFixedSize(true)
//        binding2?.topCoursePersona4?.adapter = adapter_recom1

//
//        val layoutManager6 = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL,false )
//        adapter_recom1 = recom_adapter(top_corse_array)
//        binding2?.topCoursePersona5?.layoutManager = layoutManager6
//        binding2?.topCoursePersona5?.setHasFixedSize(true)
//        binding2?.topCoursePersona5?.adapter = adapter_recom1


    } //body close

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        var adapter_horz : Horizontal_RecyclerView
        adapter_horz = Horizontal_RecyclerView(this@Dashboard.requireContext())
        val layoutManager1 =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding2?.recyclerHorzX?.layoutManager = layoutManager1
        binding2?.recyclerHorzX?.setHasFixedSize(true)
        binding2?.recyclerHorzX?.adapter = adapter_horz
        var viewModel2 : topCourseNPViewModel
        viewModel2 = ViewModelProvider(this).get(topCourseNPViewModel::class.java)

        viewModel2.allUsers.observe(viewLifecycleOwner, Observer {

            adapter_horz.updateUserList(it)

        })

        TC = arrayListOf()

        dbms = FirebaseDatabase.getInstance().getReference("topCourses")

        dbms.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    TC.clear()
                    for (topshot in snapshot.children){
                       var shot = topshot.getValue(Top_course::class.java)
                        TC.add(shot!!)
                    }

                }
                TC.reverse()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        adapter_horz.setOnItemClickListener(object : Horizontal_RecyclerView.onItemClickListener{
            override fun onItemClick(position: Int) {
                super.onItemClick(position)
                val intent  = Intent(this@Dashboard.requireContext(), viewCourse::class.java)
                intent.putExtra("category",TC[position].category)
                intent.putExtra("Uid",TC[position].uid)
                intent.putExtra("access", "0")

                startActivity(intent)
            }
        })
    }








}//classclose