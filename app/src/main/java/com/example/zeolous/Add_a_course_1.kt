package com.example.zeolous

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.zeolous.Models.addCourse1
import com.example.zeolous.Models.message
import com.example.zeolous.databinding.ActivityAddAcourse1Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.DateFormat
import java.util.*


class Add_a_course_1 : AppCompatActivity() {

    private lateinit var binding_a1 : ActivityAddAcourse1Binding
    private lateinit var imageUri: Uri
    private lateinit var addd_c : addCourse1
    private lateinit var storage : StorageReference
    private lateinit var mdef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_a1 = ActivityAddAcourse1Binding.inflate(layoutInflater)
        setContentView(binding_a1.root)

        mdef = FirebaseDatabase.getInstance().getReference()
        var items = listOf("CSAI", "COE", "CSDS", "EE", "ECE", "EIOT", "IT", "ITNS", "ICE", "MCE", "ME", "BT", "others" )

        var items2 = listOf("English", "hindi","Tamil","Telugu","Gujrati","Marathi","Punjabi" )



        //For categories
        var adapter = ArrayAdapter(this, R.layout.categories_drop_course,items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding_a1.categoriesDrop.adapter = adapter

           var category1 : String?= "Others"
           var read = false
        binding_a1.categoriesDrop.onItemSelectedListener = object :   AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position==12){
                    binding_a1.ujkkjkjkjl.visibility = View.VISIBLE
                    binding_a1.newCategory.visibility = View.VISIBLE
                    read=true
                }else{
                    binding_a1.ujkkjkjkjl.visibility = View.GONE
                    binding_a1.newCategory.visibility = View.GONE
                     category1  = items[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }



        }



         //For language
        var language :String?= "English"
        var adapter2 = ArrayAdapter(this, R.layout.categories_drop_course,items2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding_a1.categoriesDrop2.adapter = adapter2


        binding_a1.categoriesDrop2.onItemSelectedListener = object :   AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                  language  = items2[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }



        }

        binding_a1?.imageView7?.setOnClickListener {

            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        val title = binding_a1.title.text.toString()
        val description  = binding_a1.description.text.toString()
        val access : String
        if(binding_a1.checkBox.isChecked){
            access="0"
        }else{
            access = binding_a1.duration.text.toString()
        }


        val calendar = Calendar.getInstance()
        val UIDC = FirebaseAuth.getInstance().currentUser?.uid+calendar.timeInMillis.toString()



        binding_a1.button7.setOnClickListener {

            val title = binding_a1.title.text.toString()
            val description  = binding_a1.description.text.toString()
            val access : String
            if(binding_a1.checkBox.isChecked){
                access="0"
            }else{
                access = binding_a1.duration.text.toString()
            }

            if(read==true){
                category1 = binding_a1.newCategory.text.toString()
            }
            val calender = Calendar.getInstance()
            storage = FirebaseStorage.getInstance().getReference("Courses").child(FirebaseAuth.getInstance().currentUser!!.uid).child(UIDC).child(calender.timeInMillis.toString()+"")
            storage.putFile(imageUri).addOnSuccessListener{
                storage.downloadUrl.addOnSuccessListener {
                    addd_c = addCourse1(UIDC, it.toString(),title,description, category1, language, access )

                    mdef.child("courses").child(category1.toString()).child(UIDC!!).setValue(addd_c)
                    mdef.child("courses").child(category1.toString()).child(UIDC!!).child("enroll").setValue("0")
                    mdef.child("courses").child(category1.toString()).child(UIDC!!).child("rating").setValue("0")
                    mdef.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                        val no = it.child("courseNumber").value.toString()
                        var count = Integer.parseInt(no)
                        count++
                        mdef.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                            val name = it.child("name").value.toString()
                            mdef.child("courses").child(category1.toString()).child(UIDC!!).child("creator").setValue(name)
                        }
                        mdef.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("courseNumber").setValue(count)
                        mdef.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("courses").child("C$count").child("Category").setValue(category1)
                        mdef.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("courses").child("C$count").child("ID").setValue(UIDC)
                    }


                }
            }
            val intent = Intent(this,Add_a_course_2::class.java)
            intent.putExtra("uidc",UIDC)
            intent.putExtra("category",category1)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {

            if (data.data != null)
                imageUri = data.data!!
            binding_a1?.imageView7?.setImageURI(imageUri)

        }
    }

}