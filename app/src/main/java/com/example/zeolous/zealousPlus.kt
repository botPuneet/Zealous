package com.example.zeolous

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.example.zeolous.Adapter.best_selling_Adapter
import com.example.zeolous.Adapter.recom_adapter
import com.example.zeolous.Models.Best_selling_course
import com.example.zeolous.Models.Top_course
import com.example.zeolous.databinding.FragmentDashboardBinding
import com.example.zeolous.databinding.FragmentZealousPlusBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.checkerframework.common.subtyping.qual.Bottom


class zealousPlus : Fragment() {

    private lateinit var binding :FragmentZealousPlusBinding
    private lateinit var dbms : DatabaseReference
    lateinit var preferences: SharedPreferences
    private  lateinit var best_corse_array : ArrayList<Best_selling_course>
    lateinit var Img_arr : Array<Int>
    lateinit var Tittle : Array<String>
    private lateinit var adapter1 : best_selling_Adapter
//    private lateinit var type : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentZealousPlusBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = this@zealousPlus.requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val UID = preferences.getString("Uid","")

        dbms = FirebaseDatabase.getInstance().getReference("Users")

        dbms.child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
           val type = it.child("type").value
            if(type =="Student") {
                 var intent = Intent(this@zealousPlus.requireContext(), BecomeInstructor::class.java)
                 startActivity(intent)
             }else{
                   val bottomSheet =  Bottomsheet_1()
                 val fragmentmanager = (activity as FragmentActivity).supportFragmentManager
                 fragmentmanager?.let{bottomSheet.show(it,"becoming")}
             }
        }

         binding.button3.setOnClickListener{
             val bottomSheet =  Bottomsheet_1()
             val fragmentmanager = (activity as FragmentActivity).supportFragmentManager
             fragmentmanager?.let{bottomSheet.show(it,"becoming")}
         }
//
        dataIntializa()

        binding.viewall.setOnClickListener{
            val intent  = Intent(this@zealousPlus.requireContext(), View_All_Courses::class.java)
            startActivity(intent)
        }

        val layoutManager2 = LinearLayoutManager(this.requireContext() )
        adapter1= best_selling_Adapter(best_corse_array)
        binding?.bestSellingRecycler?.layoutManager = layoutManager2
        binding?.bestSellingRecycler?.setHasFixedSize(false)
        binding?.bestSellingRecycler?.adapter = adapter1



    }
    private fun dataIntializa(){

        best_corse_array = arrayListOf<Best_selling_course>()
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
            val temp = Best_selling_course(Img_arr[i],Tittle[i])
            best_corse_array.add(temp)
        }
    }

}