package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.chatSeaarchAdapter
import com.example.zeolous.Models.chatsearch
import com.example.zeolous.databinding.ActivityChatSearchBinding
import com.example.zeolous.databinding.ActivityUpdateProfileBinding
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class chat_search : AppCompatActivity() {
    private lateinit var binding2: ActivityChatSearchBinding
    private lateinit var database_search: DatabaseReference
    private lateinit var userlist : ArrayList<chatsearch>
    private lateinit var adapter1: chatSeaarchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityChatSearchBinding.inflate(layoutInflater)
        setContentView(binding2.root)
        binding2.chatSearchRecycler.layoutManager = LinearLayoutManager(this)
        binding2.chatSearchRecycler.setHasFixedSize(true)
         userlist = arrayListOf<chatsearch>()
        datafeed()
        binding2.searchChat.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               filterList(newText)
                return true
            }


        })

    }

    private fun filterList(querry: String?) {
        if (querry!=null){
            val filteredlist = ArrayList<chatsearch>()
            for(i in userlist)
            {if(i.username!!.lowercase(Locale.ROOT).contains(querry)){
                filteredlist.add(i)
            }

            }
            if(filteredlist.isEmpty()) {
                Toast.makeText(this, "Sorry, Got no one with this name", Toast.LENGTH_SHORT).show()
            }
                else{
                   adapter1 = chatSeaarchAdapter(filteredlist, this@chat_search)
                binding2.chatSearchRecycler.adapter = adapter1

                adapter1.setOnItemClickListener(object  : chatSeaarchAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        var intent3 = Intent(this@chat_search,Chatting::class.java)
                        intent3.putExtra("UID",filteredlist[position].uid)
                        startActivity(intent3)
                    }

                })


                }

            }
        }



    private fun datafeed() {
        database_search = FirebaseDatabase.getInstance().getReference("Users")

        database_search.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (usersnapshot in snapshot.children){
                        val user = usersnapshot.getValue(chatsearch::class.java)
                        userlist.add(user!!)
                    }
//                    binding2.chatSearchRecycler.adapter = chatSeaarchAdapter(userlist, this@chat_search)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}