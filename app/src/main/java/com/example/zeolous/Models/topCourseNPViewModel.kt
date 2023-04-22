package com.example.zeolous.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeolous.Repository.recomRepo
import com.example.zeolous.Repository.topCourseRepo

class topCourseNPViewModel  : ViewModel() {
    private val repository : topCourseRepo
    private val _allUsers = MutableLiveData<List<Top_course>>()
    val allUsers : LiveData<List<Top_course>> = _allUsers


    init {

        repository = topCourseRepo().getInstance()
        repository.loadcourse(_allUsers)

    }

}