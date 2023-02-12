package com.example.zeolous

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.zeolous.databinding.FragmentDashboardBinding
import com.example.zeolous.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    private var binding2: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth
    private val binding get() = binding2!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding2 = FragmentProfileBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding2!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val namess = arrayOf("Account Settings", "Payment methods", "Security", "language", "Log out")
        val names = arrayOf("Account Support", "FAQ", "terms of use", "Privacy Policy")

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this.requireContext(), com.example.zeolous.R.layout.text_colour, namess)
        val arrayAdapter2: ArrayAdapter<String> = ArrayAdapter(this.requireContext(), com.example.zeolous.R.layout.text_colour, names)


        binding2?.list1?.setOnItemClickListener { parent, view, position, id ->

            if(position==0) {

                val intent = Intent(this@Profile.requireContext(), Update_profile::class.java)
                startActivity(intent)

            }
            if(position==4) {
                auth.signOut()
                val intent = Intent(this@Profile.requireContext(), Sign_in::class.java)
                startActivity(intent)

            }



        }


            binding2?.list1?.adapter = arrayAdapter
            binding2?.list2?.adapter = arrayAdapter2


        }


    }
