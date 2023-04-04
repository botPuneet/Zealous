package com.example.zeolous

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zeolous.Adapter.messageAdapter
import com.example.zeolous.Models.chatsearch
import com.example.zeolous.Models.message
import com.example.zeolous.databinding.ActivityChattingBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.text.DateFormat
import java.util.*


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
    private lateinit var UID : String
    private lateinit var chatUser  : chatsearch


    private lateinit var messageObject:message

           private var sentroom:String?=null
           private var recieveroom:String?=null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_C = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding_C.root)

        database_search = FirebaseDatabase.getInstance().getReference("Users")
        mdef = FirebaseDatabase.getInstance().getReference()

         val count =1
        val bundle : Bundle? = intent.extras
         UID = bundle!!.getString("UID")!!//receivers UID


        messagelist = ArrayList()



        preferences = this.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val name = preferences.getString("First_name", "")
        val Profile = preferences.getString("profile", "")
         UID2 = preferences.getString("Uid", "")!!
//        database_search.child(UID).get().addOnSuccessListener {
//
//            val name = it.child("username").value.toString()
//            val image = it.child("Image").value.toString()
//            val type = it.child("type").value.toString()
//            val uid = it.child("uid").value.toString()
//            chatUser = chatsearch(image,name,type,uid)
//
//        }







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
                database_search.child(UID2!!).child("chatting").child(UID!!).child("username").setValue(Uname)
                database_search.child(UID2!!).child("chatting").child(UID!!).child("Image").setValue(Image)
                database_search.child(UID2!!).child("chatting").child(UID!!).child("uid").setValue(UID)
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                binding_C?.chattingImg?.setImageBitmap(bitmap)
            }
        }

        database_search.child(UID2!!).get().addOnSuccessListener {

            val name = it.child("name").value.toString()
            val type = it.child("type").value.toString()
            val Image = it.child("Image").value.toString()
            val Uname = it.child("username").value.toString()



                database_search.child(UID!!).child("chatting").child(UID2!!).child("username").setValue(Uname)
                database_search.child(UID!!).child("chatting").child(UID2!!).child("Image").setValue(Image)
                database_search.child(UID!!).child("chatting").child(UID2!!).child("uid").setValue(UID2)


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
                if(messageAdapter.itemCount>5){
                binding_C.recyclerviewChatLog.smoothScrollToPosition(messageAdapter.itemCount-1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        messageAdapter = messageAdapter(this,messagelist)
        val layoutManagerX = LinearLayoutManager(this)

        binding_C.recyclerviewChatLog.layoutManager = layoutManagerX
        binding_C.recyclerviewChatLog.adapter = messageAdapter

        messageAdapter.setOnItemClickListener(
            object : messageAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {

                  Toast.makeText(this@Chatting,messagelist[position].type,Toast.LENGTH_SHORT).show()
                }
            }
        )





        binding_C.edittextChatLog.setText("")

        binding_C.sendButtonChatLog.setOnClickListener{
                val message = binding_C.edittextChatLog.text.toString()

            database_search.child(UID2!!).child("chatting").child(UID!!).child("lastMessage").setValue(message)
                val calendar = Calendar.getInstance().time
            val timeformat = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar).toString()

            val messageObject = message(message,UID2,"msg","null",timeformat)

            mdef.child("chat").child(sentroom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mdef.child("chat").child(recieveroom!!).child("messages").push().setValue(messageObject)
            }

            binding_C.edittextChatLog.setText("")
        }


        binding_C.sendButtonImgLog.setOnClickListener{
            val popmenu = PopupMenu(this@Chatting,it)
            popmenu.setOnMenuItemClickListener {item->
                when(item.itemId){
                    R.id.image_send ->{
                        val intent = Intent()
                        intent.action = Intent.ACTION_GET_CONTENT
                        intent.type = "image/*"
                        startActivityForResult(intent, 1)
                        true
                    }
                    R.id.pdf_send ->{
                        val intent2 = Intent()

                        intent2.type = "application/pdf"
                        intent2.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(Intent.createChooser(intent2,"select pdf"),200)
                        true
                    }
                    else ->false
                }
            }
            popmenu.inflate(R.menu.attach)
            try{
                val fieldpop = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldpop.isAccessible = true
                val mPopup = fieldpop.get(popmenu)
                mPopup.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java).invoke(mPopup,true)

            }catch (e : java.lang.Exception){

            }finally {
                popmenu.show()
            }
        }
 binding_C.chattingImg.setOnClickListener{
     startActivity(Intent(applicationContext, ChattingProfile::class.java))
     finish()
 }




    }
    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null && requestCode==1) {

            if (data.data != null) {
                imageUri = data.data!!
                val calender = Calendar.getInstance()
                storage = FirebaseStorage.getInstance().getReference("chats").child(calender.timeInMillis.toString()+"")
                storage.putFile(imageUri).addOnSuccessListener{
                    storage.downloadUrl.addOnSuccessListener {
                        val calendar = Calendar.getInstance().time
                        val timeformat = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar).toString()
                        val messageObject = message("",UID2!!,"IMG",it.toString(),timeformat)

                        mdef.child("chat").child(sentroom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                            mdef.child("chat").child(recieveroom!!).child("messages").push().setValue(messageObject)
                            database_search.child(UID2!!).child("chatting").child(UID!!).child("lastMessage").setValue("Image")
                        }
                    }
                }


            }


        }

        // for pdf
        if (data != null  && requestCode==200) {

            if (data.data != null) {
                imageUri = data.data!!
                val calender = Calendar.getInstance()

                var result: String? = null
                if (imageUri.getScheme().equals("content")) {
                    val cursor: Cursor? = contentResolver.query(imageUri, null, null, null, null)
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
                    result = imageUri.getPath()
                    val cut = result!!.lastIndexOf('/')
                    if (cut != -1) {
                        result = result!!.substring(cut + 1)
                    }
                }
                storage = FirebaseStorage.getInstance().getReference("chats").child(calender.timeInMillis.toString()+"")
                storage.putFile(imageUri).addOnSuccessListener{
                    storage.downloadUrl.addOnSuccessListener {
                        val calendar = Calendar.getInstance().time
                        val timeformat = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar).toString()
                        val messageObject = message(result,UID2!!,"PDF",it.toString(),timeformat)

                        mdef.child("chat").child(sentroom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                            mdef.child("chat").child(recieveroom!!).child("messages").push().setValue(messageObject)
                            database_search.child(UID2!!).child("chatting").child(UID!!).child("lastMessage").setValue("Image")
                        }
                    }
                }


            }


        }
    }


}