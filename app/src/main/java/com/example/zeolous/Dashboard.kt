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
    //testing
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
        val Profile = preferences.getString("profile", "")

        val storageRef =  FirebaseStorage.getInstance().reference.child("Profile").child(name.toString())
        val localfile = File.createTempFile("temp",".jpg")
        binding2?.textView10?.setText("Hello\n$name!")
        storageRef.getFile(localfile).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding2?.image2?.setImageBitmap(bitmap)







        }
//            top_corse_array.add(sampleArray[0])
        val bottomSheet = Bottomsheet_1()
        binding2?.button3?.setOnClickListener{
            getFragmentManager()?.let { it1 -> bottomSheet.show(it1,"becoming") }


        }




                  dataIntializa()
         adapter_horz = Horizontal_RecyclerView(top_corse_array)
      val layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.HORIZONTAL,false )
        binding2?.recyclerHorzX?.layoutManager=layoutManager
        binding2?.recyclerHorzX?.setHasFixedSize(true)
        binding2?.recyclerHorzX?.adapter = adapter_horz





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