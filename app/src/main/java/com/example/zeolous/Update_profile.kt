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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.*

class Update_profile : AppCompatActivity() {
    private lateinit var binding2: ActivityUpdateProfileBinding
    private lateinit var database: FirebaseDatabase
    lateinit var preferences: SharedPreferences
    private lateinit var storage: StorageReference
    private lateinit var storage2  : FirebaseStorage
    private lateinit var imageUri: Uri


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding2.root)

        binding2?.imageView3?.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)

        }

        preferences = this.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val name = preferences.getString("First_name", "")
        val Full_name = preferences.getString("Full_name", "")
         binding2.updateName.setText(Full_name)



        binding2.button5.setOnClickListener {
            storage = FirebaseStorage.getInstance().getReference("Profile").child(name.toString())
            storage.putFile(imageUri)
//                var intent3 = Intent(this, Home::class.java)
//
//                startActivity(intent3)
            Toast.makeText(this, "Profile pic Updated", Toast.LENGTH_SHORT).show()




        }
        val storageRef =
            FirebaseStorage.getInstance().reference.child("Profile").child(name.toString())
        val localfile = File.createTempFile("temp", ".jpg")

        storageRef.getFile(localfile).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding2?.imageView3?.setImageBitmap(bitmap)


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
