package com.example.zeolous.Repository

import androidx.lifecycle.MutableLiveData
import com.example.zeolous.Models.chatRecycler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chatRepository() {
    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private val auth : FirebaseAuth=FirebaseAuth.getInstance()

    @Volatile private var INSTANCE : chatRepository ?= null

    fun getInstance() : chatRepository{
        return INSTANCE ?: synchronized(this){

            val instance = chatRepository()
            INSTANCE = instance
            instance
        }


    }


    fun loadUsers(userList : MutableLiveData<List<chatRecycler>>){

        databaseReference.child(auth.getCurrentUser()!!.getUid()).child("chatting").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _userList : List<chatRecycler> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(chatRecycler::class.java)!!

                    }

                    userList.postValue(_userList)

                }catch (e : Exception){


                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

}}