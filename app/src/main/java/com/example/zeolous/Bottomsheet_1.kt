package com.example.zeolous

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.zeolous.databinding.FragmentBottomsheet1Binding
import com.example.zeolous.databinding.FragmentDashboardBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment





class Bottomsheet_1 : BottomSheetDialogFragment() {
    private  var binding2: FragmentBottomsheet1Binding? = null
    private  val binding get() = binding2!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding2 = FragmentBottomsheet1Binding.inflate(inflater, container, false)

        return binding2!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding2?.becomeInstructor?.setOnClickListener {
//            var intent = Intent(this.requireContext(), BecomeInstructor::class.java)
//
//            startActivity(intent)
//        }
    }


}