package com.example.zeolous

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.MediaController
import com.example.zeolous.databinding.ActivityAddCourse9Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class add_course9 : AppCompatActivity() {
    var tposition : String?= null
    var sposition : String?= null
    var UIDC : String?= null
    var category : String?= null
    private lateinit var uUri : Uri
    private lateinit var dbms : DatabaseReference
    private lateinit var binding_a9 : ActivityAddCourse9Binding
    private lateinit var storage : StorageReference
    var Nflag=false
    var Lflag =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_a9 = ActivityAddCourse9Binding.inflate(layoutInflater)
        setContentView(binding_a9.root)
        val bundle : Bundle? = intent.extras
        UIDC = bundle!!.getString("uidc")!!
        tposition = bundle!!.getString("tPosition")
        category = bundle!!.getString("category")!!
       sposition = bundle!!.getString("sPosition")!!
        dbms = FirebaseDatabase.getInstance().getReference("courses").child(category!!).child(UIDC!!).child("subtopicContent")
               .child("T$tposition").child("S$sposition")

        binding_a9.imgup.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 2)
            dbms.child("type").setValue("img")

        }

        binding_a9.videoup.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "video/*"
            startActivityForResult(intent, 1)
            dbms.child("type").setValue("video")

        }

        binding_a9.pdfup.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "application/pdf"
            startActivityForResult(intent, 3)
            dbms.child("type").setValue("pdf")
        }

        binding_a9.linkup.setOnClickListener {
            dbms.child("type").setValue("link")
            binding_a9.textView12dfgds.visibility = View.VISIBLE
            binding_a9.editTextTextMultiLineesd.visibility = View.VISIBLE
            Lflag = true
        }
        binding_a9.noneup.setOnClickListener {
            var st = "None"
            binding_a9.pdfView.text = st
            Nflag = true
        }

        binding_a9.button9.setOnClickListener {
            if(Nflag==false && Lflag ==false){
            val calender = Calendar.getInstance()
            storage = FirebaseStorage.getInstance().getReference("Courses").child(FirebaseAuth.getInstance().currentUser!!.uid).child(UIDC!!)
                .child("subtopic")
                .child("T$tposition").child("S$sposition")
                .child(calender.timeInMillis.toString()+"")
            storage.putFile(uUri).addOnSuccessListener{
                storage.downloadUrl.addOnSuccessListener {

                    dbms.child("content").setValue(it.toString())
                }
            }}

            if(Lflag == true){
                dbms.child("content").setValue(binding_a9.editTextTextMultiLineesd.text.toString())
            }

            dbms.child("article").setValue(binding_a9.editTextTextMultiLine.text.toString())
            finish()
        }

    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //video
    if(requestCode==1){
        if (data != null) {

            if (data.data != null){
                binding_a9.videoView.visibility = View.VISIBLE
                binding_a9.videoView.setVideoURI(data.data)
                binding_a9.videoView.start()
                val mediaController = MediaController(this)
                binding_a9.videoView.setMediaController(mediaController)
                mediaController.setAnchorView( binding_a9.videoView)
                uUri = data.data!!

            }

        }}

        //image
        if(requestCode==2){
            if (data != null) {

                if (data.data != null){
                    binding_a9.imgView.visibility = View.VISIBLE
                    binding_a9.imgView.setImageURI(data.data)

                    uUri = data.data!!

                }

            }}

        //pdf
        if(requestCode==3){
            if (data != null) {

                if (data.data != null){
                     binding_a9.pdfView.visibility = View.VISIBLE
                    var result: String? = null
                    uUri = data.data!!
                    if (uUri.getScheme().equals("content")) {
                        val cursor: Cursor? = contentResolver.query(uUri, null, null, null, null)
                        try {
                            if (cursor != null && cursor.moveToFirst()) {
                                result =
                                    cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            }
                        } finally {
                            cursor!!.close()
                        }
                    }
                    if (result == null) {
                        result = uUri.getPath()
                        val cut = result!!.lastIndexOf('/')
                        if (cut != -1) {
                            result = result!!.substring(cut + 1)
                        }
                    }
                    binding_a9.pdfView.text = result.toString()

                }

            }}



    }

}