package com.example.zeolous

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zeolous.databinding.ActivityUpdateProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.*

class Update_profile : AppCompatActivity() {
    private lateinit var binding2: ActivityUpdateProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var database3: DatabaseReference
    lateinit var preferences: SharedPreferences
    private lateinit var storage: StorageReference

    private lateinit var imageUri: Uri


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding2 = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding2.root)
         var flag_img=false
           binding2?.imageView3?.setOnClickListener {
            flag_img=true
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        database = FirebaseDatabase.getInstance().getReference("Users")
        database2 = FirebaseDatabase.getInstance().getReference("usernames")
        database3 = FirebaseDatabase.getInstance().getReference("username")

        preferences = this.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = preferences.edit()
        val name = preferences.getString("First_name", "")
        var Full_name = preferences.getString("Full_name", "")
        var Uname = preferences.getString("username", "")
        val UID = preferences.getString("Uid","")
        val uidno = preferences.getString("UID_NO","")
         binding2.updateName.setText(Full_name)
        binding2.updateUname.setText(Uname)

        val storageRef =  FirebaseStorage.getInstance().reference.child("Profile").child(UID!!)
        val localfile = File.createTempFile("temp",".jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding2?.imageView3?.setImageBitmap(bitmap)
        }

     var flagUpdata =false


        binding2.button5.setOnClickListener {
            if(flag_img==true) {
            storage = FirebaseStorage.getInstance().getReference("Profile").child(UID!!)
            storage.putFile(imageUri)

                storage.downloadUrl.addOnSuccessListener {
                    database.child(UID!!).child("Image").setValue(it.toString())
                }
                flag_img=false
            }
             if(Full_name!=binding2.updateName.toString()){
                database2.child(Full_name!!).child(uidno!!).removeValue()
               Full_name = binding2.updateName.text.toString()
                 binding2.updateName.setText(Full_name)
                 val arr = Full_name!!.split(" ".toRegex(), limit = 2)?.toTypedArray()
                 val firstWord = arr?.get(0)
                 editor.putString("First_name", firstWord)
                 editor.putString("Full_name",Full_name)
                 editor.apply()
                  database.child(UID!!).child("name").setValue(Full_name)
                 database2.child(Full_name!!).get().addOnSuccessListener {
                     val count =1
                     if(it.child("UID$count").exists()){
                         count+1
                     }else{
                         database2.child(Full_name!!).child("UID$count").setValue(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())
                         editor.putString("UID_No", "UID$count")
                     }
                 }
             }

            if(Uname!=binding2.updateUname.text.toString()){
                   val uname = binding2.updateUname.text.toString()
                database3.child(uname!!).get().addOnSuccessListener {
                    if(it.value=="true"){
                        Toast.makeText(this,"Sorry Already taken",Toast.LENGTH_SHORT).show()

                    }
                    else{  database3.child(Uname!!).child(uidno!!).removeValue()
                        database3.child(uname!!).setValue("true")
                        preferences = this.getSharedPreferences("userData", Context.MODE_PRIVATE)
                        val editor : SharedPreferences.Editor = preferences.edit()
                        editor.putString("username",uname)
                        editor.apply()
                        database.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid()).child("username").setValue(uname)
                        flagUpdata = true
                    }}


            }else{
                flagUpdata = true
            }
            if(flagUpdata) {
                Toast.makeText(this, "Information Updated", Toast.LENGTH_SHORT).show()
            }



        }




    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (data != null) {

                if (data.data != null)
                    imageUri = data.data!!
                binding2?.imageView3?.setImageURI(imageUri)

            }
        }
    }
