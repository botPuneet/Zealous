package com.example.zeolous

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.chatAdapter
import com.example.zeolous.Adapter.chatSeaarchAdapter
import com.example.zeolous.Models.chatViewModel
import com.example.zeolous.Models.chatsearch
import com.example.zeolous.databinding.FragmentChatboxBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

private lateinit var viewModel : chatViewModel
lateinit var preferences: SharedPreferences
private lateinit var database_search: DatabaseReference
private lateinit var userlist : ArrayList<chatsearch>
lateinit var adapter: chatAdapter
class Chatbox : Fragment() {
    private  var binding_C: FragmentChatboxBinding? = null
    private lateinit var UID2 : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_C = FragmentChatboxBinding.inflate(inflater, container, false)

        return binding_C!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding_C?.chatSearch?.setOnClickListener{
            val intent = Intent(this.requireContext(), chat_search::class.java)
            startActivity(intent)
        }
        userlist = arrayListOf<chatsearch>()
        binding_C?.chatboxRecycler?.layoutManager = LinearLayoutManager(context)
        binding_C?.chatboxRecycler?.setHasFixedSize(true)
        adapter = chatAdapter(this.requireContext())
        binding_C?.chatboxRecycler?.adapter = adapter

        preferences = this.requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val name = preferences.getString("First_name", "")
        val Profile = preferences.getString("profile", "")
        UID2 = preferences.getString("Uid","").toString()

        viewModel = ViewModelProvider(this).get(chatViewModel::class.java)

        viewModel.allUsers.observe(viewLifecycleOwner, Observer {

            adapter.updateUserList(it)

        })

        database_search = FirebaseDatabase.getInstance().getReference("Users")

        database_search.child(UID2).child("chatting").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                if(snapshot.exists()){
                    for (usersnapshot in snapshot.children){
                        val user = usersnapshot.getValue(chatsearch::class.java)
                        userlist.add(user!!)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        adapter.setOnItemClickListener(object  : chatAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

//                Toast.makeText(this@Chatbox.requireContext(), userlist[position].uid.toString(),Toast.LENGTH_SHORT).show()
                var intent3 = Intent(this@Chatbox.requireContext(),Chatting::class.java)
                intent3.putExtra("UID", userlist[position].uid)
                startActivity(intent3)
            }

        })
    }



}