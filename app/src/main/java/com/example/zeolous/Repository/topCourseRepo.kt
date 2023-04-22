package com.example.zeolous.Repository

import androidx.lifecycle.MutableLiveData
import com.example.zeolous.Models.Top_course
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class topCourseRepo {
    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("topCourses")
    private val databaseReference2 : DatabaseReference = FirebaseDatabase.getInstance().getReference("courses")


    @Volatile private var INSTANCE : topCourseRepo ?= null

    fun getInstance() : topCourseRepo{
        return INSTANCE ?: synchronized(this){

            val instance = topCourseRepo()
            INSTANCE = instance
            instance
        }


    }


    fun loadcourse(userList : MutableLiveData<List<Top_course>>){
         var _userList :ArrayList<Top_course>
         _userList = arrayListOf<Top_course>()


      databaseReference.addValueEventListener(object : ValueEventListener{
          override fun onDataChange(snapshot: DataSnapshot) {
              if(snapshot.exists()){
                  _userList.clear()
                  for(topshot in snapshot.children){


                      var got =topshot.getValue(Top_course::class.java)
                      if (got != null) {
                          _userList.add(got)
                      }

                  }
                  _userList.reverse()


                  userList.postValue(_userList)

              }}

          override fun onCancelled(error: DatabaseError) {
              TODO("Not yet implemented")
          }

      })







    }}