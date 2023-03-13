package com.example.zeolous

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.Horizontal_RecyclerView
import com.example.zeolous.Adapter.recom_adapter
import com.example.zeolous.Models.Top_course
import com.example.zeolous.databinding.FragmentDashboardBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import kotlin.collections.ArrayList


class Dashboard : Fragment() {
    lateinit var preferences: SharedPreferences
    private  var binding2: FragmentDashboardBinding? = null
    private  val binding get() = binding2!!
    private lateinit var database_reference: DatabaseReference
    private  lateinit var database : FirebaseDatabase
    private lateinit var storage  : FirebaseStorage
    private lateinit var  imageUri : Uri
    private lateinit var dialog: AlertDialog.Builder

    private lateinit var adapter_horz :Horizontal_RecyclerView
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


             // profile pic image
        val storageRef =  FirebaseStorage.getInstance().reference.child("Profile").child(UID!!)
        val localfile = File.createTempFile("temp",".jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding2?.image2?.setImageBitmap(bitmap)
        }


        binding2?.textView10?.setText("Hello\n$Uname!")


        database_reference = FirebaseDatabase.getInstance().getReference("Users")


        val bottomSheet = Bottomsheet_1()
        binding2?.button3?.setOnClickListener{
            getFragmentManager()?.let { it1 -> bottomSheet.show(it1,"becoming") }


        }




                  dataIntializa()
         adapter_horz = Horizontal_RecyclerView(top_corse_array)
          val layoutManager1 = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL,false )
        binding2?.recyclerHorzX?.layoutManager=layoutManager1
        binding2?.recyclerHorzX?.setHasFixedSize(true)
        binding2?.recyclerHorzX?.adapter = adapter_horz


        val layoutManager2 = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL,false )
        adapter_recom1 = recom_adapter(top_corse_array)
        binding2?.topCoursePersona1?.layoutManager = layoutManager2
        binding2?.topCoursePersona1?.setHasFixedSize(true)
        binding2?.topCoursePersona1?.adapter = adapter_recom1

        database_reference.child(UID.toString()).get().addOnSuccessListener{
            val personlization1 = it.child("personalization 1").value
            val personlization2 = it.child("personalization 2").value
            val personlization3 = it.child("personalization 3").value
            val personlization4 = it.child("personalization 4").value
            val personlization5 = it.child("personalization 5").value

            binding2?.persona1?.setText("Top courses of $personlization1")
            binding2?.persona2?.setText("Top courses of $personlization2")
            binding2?.persona3?.setText("Top courses of $personlization3")
            binding2?.persona4?.setText("Top courses of $personlization4")
            binding2?.persona5?.setText("Top courses of $personlization5")

        }


        val layoutManager3 = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL,false )
        adapter_recom2 = recom_adapter(top_corse_array)
        binding2?.topCoursePersona2?.layoutManager = layoutManager3
        binding2?.topCoursePersona2?.setHasFixedSize(true)
        binding2?.topCoursePersona2?.adapter = adapter_recom2


        val layoutManager4 = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL,false )
        adapter_recom1 = recom_adapter(top_corse_array)
        binding2?.topCoursePersona3?.layoutManager = layoutManager4
        binding2?.topCoursePersona3?.setHasFixedSize(true)
        binding2?.topCoursePersona3?.adapter = adapter_recom1


        val layoutManager5 = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL,false )
        adapter_recom1 = recom_adapter(top_corse_array)
        binding2?.topCoursePersona4?.layoutManager = layoutManager5
        binding2?.topCoursePersona4?.setHasFixedSize(true)
        binding2?.topCoursePersona4?.adapter = adapter_recom1


        val layoutManager6 = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL,false )
        adapter_recom1 = recom_adapter(top_corse_array)
        binding2?.topCoursePersona5?.layoutManager = layoutManager6
        binding2?.topCoursePersona5?.setHasFixedSize(true)
        binding2?.topCoursePersona5?.adapter = adapter_recom1


    }//body close

private fun dataIntializa(){

    top_corse_array = arrayListOf<Top_course>()
    Img_arr= arrayOf(
        R.drawable.a,
        R.drawable.b,   R.drawable.b,
        R.drawable.d,
        R.drawable.e,
        R.drawable.f,

    )

    Tittle = arrayOf(
        getString(R.string.head_1),
        getString(R.string.head_2),
                getString(R.string.head_3),
                getString(R.string.head_4),
                getString(R.string.head_5),
                getString(R.string.head_6)
    )
    for(i in Img_arr.indices){
        val temp = Top_course(Img_arr[i],Tittle[i])
        top_corse_array.add(temp)
    }
}



}//classclose