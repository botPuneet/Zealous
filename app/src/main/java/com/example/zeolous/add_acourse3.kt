package com.example.zeolous

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.example.zeolous.Models.addCourse1
import com.example.zeolous.databinding.ActivityAddAcourse3Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.net.URI
import java.util.*

class add_acourse3 : AppCompatActivity() {

    private lateinit var videoUri : Uri
    private lateinit var binding_a3 : ActivityAddAcourse3Binding
    private lateinit var dbms : DatabaseReference
    private lateinit var storage : StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_a3 = ActivityAddAcourse3Binding.inflate(layoutInflater)
        setContentView(binding_a3.root)

        val bundle : Bundle? = intent.extras
        val UIDC = bundle!!.getString("uidc")!!
        val category = bundle!!.getString("category")!!
        dbms = FirebaseDatabase.getInstance().getReference("courses")

        binding_a3.uploadbtn.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "video/*"
            startActivityForResult(intent, 1)
        }

        binding_a3.button7.setOnClickListener {
            val text = binding_a3.editTextTextMultiLine.text.toString()
            dbms.child(category).child(UIDC).child("introArticle").setValue(text)

            val calender = Calendar.getInstance()
            storage = FirebaseStorage.getInstance().getReference("Courses").child(FirebaseAuth.getInstance().currentUser!!.uid).child(UIDC).child(calender.timeInMillis.toString()+"")
            storage.putFile(videoUri).addOnSuccessListener{
                storage.downloadUrl.addOnSuccessListener {
                    dbms.child(category).child(UIDC).child("introVideo").setValue(it.toString())
                }
            }
            val intent  = Intent(this, Add_a_course4::class.java)
            intent.putExtra("uidc",UIDC)
            intent.putExtra("category",category)
            startActivity(intent)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {

            if (data.data != null){

             binding_a3.introvideoView.setVideoURI(data.data)
                binding_a3.introvideoView.start()
                val mediaController = MediaController(this)
                binding_a3.introvideoView.setMediaController(mediaController)
                mediaController.setAnchorView( binding_a3.introvideoView)
                videoUri = data.data!!

    }

}}
}