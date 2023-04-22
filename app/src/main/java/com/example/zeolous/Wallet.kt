package com.example.zeolous

import android.content.ClipData
import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zeolous.databinding.ActivityWalletBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Wallet : AppCompatActivity() {
    private lateinit var  binding_W : ActivityWalletBinding
    private lateinit var dbms : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_W = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding_W.root)
         dbms = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid.toString())
        dbms.get().addOnSuccessListener {
            var coins = it.child("coins").value.toString()
            var username = it.child("username").value.toString()
            binding_W.coinss.text = coins
            binding_W.refff.text = username
        }


       binding_W.clippp.setOnClickListener {
           val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
           val clip: ClipData = ClipData.newPlainText("EditText", binding_W.refff.text.toString())
           clipboard.setPrimaryClip(clip)
           Toast.makeText(this, "Copied refferal code", Toast.LENGTH_SHORT).show()
       }
    }
}