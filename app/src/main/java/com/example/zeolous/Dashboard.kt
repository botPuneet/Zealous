package com.example.zeolous

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zeolous.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Dashboard.newInstance] factory method to
 * create an instance of this fragment.
 */
class Dashboard : Fragment() {
    lateinit var preferences: SharedPreferences
    private  var binding2: FragmentDashboardBinding? = null
    private  val binding get() = binding2!!
    private lateinit var database_reference: DatabaseReference
    private  lateinit var database : FirebaseDatabase
    private lateinit var storage  : FirebaseStorage
    private lateinit var  imageUri : Uri
    private lateinit var dialog: AlertDialog.Builder

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
        val name = preferences.getString("usernameP", "")
        val Profile = preferences.getString("profile", "")
        binding2?.textView10?.setText("Hello\n$name!")




    }





}