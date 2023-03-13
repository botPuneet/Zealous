package com.example.zeolous.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeolous.Repository.chatRepository

class chatViewModel() : ViewModel() {

    private val repository : chatRepository
    private val _allUsers = MutableLiveData<List<chatRecycler>>()
    val allUsers : LiveData<List<chatRecycler>> = _allUsers


    init {

        repository = chatRepository().getInstance()
        repository.loadUsers(_allUsers)

    }

}