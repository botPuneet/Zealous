package com.example.zeolous.Repository

import androidx.lifecycle.MutableLiveData
import com.example.zeolous.Models.Top_course
import com.example.zeolous.Models.chatRecycler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class recomRepo {
    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("courses")
    private val databaseReference2 : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private val auth : String = FirebaseAuth.getInstance().currentUser!!.uid
    private var Branch = ""
    @Volatile private var INSTANCE : recomRepo ?= null

    fun getInstance() : recomRepo{
        return INSTANCE ?: synchronized(this){

            val instance = recomRepo()
            INSTANCE = instance
            instance
        }


    }


    fun loadcourse(userList : MutableLiveData<List<Top_course>>){
        var _userList :ArrayList<Top_course>
        _userList = arrayListOf<Top_course>()
        databaseReference2.child(auth).get().addOnSuccessListener {
            Branch = it.child("branch").value.toString()

            databaseReference.child(Branch).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _userList.clear()
                    if(snapshot.exists()){

                        for(topshot in snapshot.children){

                          if(topshot.child("setup").value.toString()=="1"){
                          var got =topshot.getValue(Top_course::class.java)
                        if (got != null) {
                            _userList.add(got)
                        }
                    }
                     }
                      }

                    userList.postValue(_userList)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
        }

    }}