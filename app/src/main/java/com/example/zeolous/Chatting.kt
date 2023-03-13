package com.example.zeolous

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.Options
import com.example.zeolous.Adapter.messageAdapter
import com.example.zeolous.Models.chatsearch
import com.example.zeolous.Models.message
import com.example.zeolous.databinding.ActivityChattingBinding
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.Calendar


class Chatting : AppCompatActivity() {
    private lateinit var binding_C:ActivityChattingBinding
    private lateinit var database_search: DatabaseReference
    private lateinit var mdef:DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var storage: StorageReference
    lateinit var preferences: SharedPreferences
    private lateinit var messageAdapter: messageAdapter
    private lateinit var messagelist : ArrayList<message>
    private lateinit var imageUri: Uri
    private lateinit var UID2 : String


    private lateinit var messageObject:message

           private var sentroom:String?=null
           private var recieveroom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_C = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding_C.root)

        database_search = FirebaseDatabase.getInstance().getReference("Users")
        mdef = FirebaseDatabase.getInstance().getReference()

         val count =1
        val bundle : Bundle? = intent.extras
       val  UID = bundle!!.getString("UID")//receivers UID


        messagelist = ArrayList()



        preferences = this.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val name = preferences.getString("First_name", "")
        val Profile = preferences.getString("profile", "")
         UID2 = preferences.getString("Uid", "")!!






        database_search.child(UID!!).get().addOnSuccessListener {

            val name = it.child("name").value.toString()
            val type = it.child("type").value.toString()
            val Image = it.child("Image").value.toString()
            val Uname = it.child("username").value.toString()
            binding_C.chatttingName.setText(Uname)
            binding_C.chatttingType.setText(type)
            val storageRef =  FirebaseStorage.getInstance().reference.child("Profile").child(UID!!)
            val localfile = File.createTempFile("temp",".jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                binding_C?.chattingImg?.setImageBitmap(bitmap)
                database_search.child(UID2!!).child("chatting").child(UID!!).child("username").setValue(Uname)
                database_search.child(UID2!!).child("chatting").child(UID!!).child("Image").setValue(Image)
            }
        }


             sentroom = UID+UID2
          recieveroom = UID2+UID




        mdef.child("chat").child(sentroom!!).child("messages").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messagelist.clear()
                for(postsnap in snapshot.children){
                    val message1 = postsnap.getValue(message::class.java)
                    messagelist.add(message1!!)

                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        messageAdapter = messageAdapter(this,messagelist)
        binding_C.recyclerviewChatLog.layoutManager = LinearLayoutManager(this)
        binding_C.recyclerviewChatLog.adapter = messageAdapter



        binding_C.edittextChatLog.setText("")

        binding_C.sendButtonChatLog.setOnClickListener{
                val message = binding_C.edittextChatLog.text.toString()

            database_search.child(UID2!!).child("chatting").child(UID!!).child("lastMessage").setValue(message)

            val messageObject = message(message,UID2,"msg","null")

            mdef.child("chat").child(sentroom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mdef.child("chat").child(recieveroom!!).child("messages").push().setValue(messageObject)
            }
            binding_C.edittextChatLog.setText("")
        }


        binding_C.sendButtonImgLog.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)

        }





    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {

            if (data.data != null) {
                imageUri = data.data!!
                val calender = Calendar.getInstance()
                storage = FirebaseStorage.getInstance().getReference("chats").child(calender.timeInMillis.toString()+"")
                storage.putFile(imageUri).addOnSuccessListener{
                    storage.downloadUrl.addOnSuccessListener {
                        val messageObject = message("",UID2!!,"IMG",it.toString())

                        mdef.child("chat").child(sentroom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                            mdef.child("chat").child(recieveroom!!).child("messages").push().setValue(messageObject)
                        }
                    }
                }


            }


        }
    }


}