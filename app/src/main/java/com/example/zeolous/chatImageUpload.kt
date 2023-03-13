package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zeolous.Models.message
import com.example.zeolous.databinding.ActivityChatImageUploadBinding
import com.example.zeolous.databinding.ActivityChattingBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class chatImageUpload : AppCompatActivity() {

    private lateinit var storage: StorageReference
    private lateinit var binding_C: ActivityChatImageUploadBinding
    private lateinit var mdef: DatabaseReference
    private lateinit var messageObject:message

    override fun onCreate(savedInstanceState: Bundle?) {
        binding_C = ActivityChatImageUploadBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding_C.root)
        mdef = FirebaseDatabase.getInstance().getReference()
        val bundle : Bundle? = intent.extras
        val URI = bundle!!.getString("URI")
        val UID = bundle!!.getString("UID")
        val sentroom = bundle!!.getString("sentroom")
        val recieveroom = bundle!!.getString("recieveroom")
        storage = FirebaseStorage.getInstance().getReference("chat").child(sentroom!!)

        binding_C.button6.setOnClickListener {

            storage.downloadUrl.addOnSuccessListener {
                messageObject = message("", UID, "IMG", it.toString())
            }
            mdef.child("chat").child(sentroom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mdef.child("chat").child(recieveroom!!).child("messages").push().setValue(messageObject)
            }
            Toast.makeText(this , "fuck you", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, Update_profile::class.java))
            finish()
        }
    }
}