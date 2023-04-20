package com.example.zeolous.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeolous.Repository.chatRepository
import com.example.zeolous.Repository.recomRepo

class topCourseViewModel : ViewModel() {
    private val repository : recomRepo
    private val _allUsers = MutableLiveData<List<Top_course>>()
    val allUsers : LiveData<List<Top_course>> = _allUsers


    init {

        repository = recomRepo().getInstance()
        repository.loadcourse(_allUsers)

    }

}