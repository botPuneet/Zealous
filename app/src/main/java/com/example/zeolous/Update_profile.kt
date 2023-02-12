package com.example.zeolous

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zeolous.databinding.ActivityUpdateProfileBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class Update_profile : AppCompatActivity() {
    private lateinit var binding2 : ActivityUpdateProfileBinding
    private lateinit var database : FirebaseDatabase
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var storage  : StorageReference
    private lateinit var  imageUri : Uri
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding2.root)

        binding2?.imageView3?.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,1)

            binding2.button5.setOnClickListener{
          storage = FirebaseStorage.getInstance().getReference("Profile")
                storage.putFile(imageUri)
                sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)

                val editor : SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("profile",imageUri.toString())

                editor.apply()


            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data !=null){

            if(data.data != null)
                imageUri = data.data!!
            binding2?.imageView3?.setImageURI(imageUri)

        }
    }
}