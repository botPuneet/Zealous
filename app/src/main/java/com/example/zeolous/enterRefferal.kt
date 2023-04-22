package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zeolous.databinding.ActivityEnterRefferalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class enterRefferal : AppCompatActivity() {
    private lateinit var binding_re : ActivityEnterRefferalBinding
    private lateinit var dbms : DatabaseReference
    private lateinit var dbms2 : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_re = ActivityEnterRefferalBinding.inflate(layoutInflater)
        setContentView(binding_re.root)

        dbms = FirebaseDatabase.getInstance().getReference("Users")
        dbms2 = FirebaseDatabase.getInstance().getReference("username")


        binding_re.button.setOnClickListener {
            var uid1 = binding_re.imageView.text.toString()

            dbms2.child(uid1).get().addOnSuccessListener {
                var uid2 = it.value.toString()
                var checkFlag =true
                if(uid2==null){
                    checkFlag=false
                }
                 if(checkFlag==true){


                    dbms.child(uid2).get().addOnSuccessListener {
                        val coind = it.child("coins").value.toString()

                        val coins = Integer.parseInt(coind)
                        dbms.child(uid2).child("coins").setValue((coins + 50).toString())
                        dbms.child(FirebaseAuth.getInstance().currentUser!!.uid).get()
                            .addOnSuccessListener {
                                val coind = it.child("coins").value.toString()

                                val coins = Integer.parseInt(coind)
                                dbms.child(FirebaseAuth.getInstance().currentUser!!.uid)
                                    .child("coins")
                                    .setValue((coins + 50).toString())
                            }
                        var intent3 = Intent(this, Welcome::class.java)

                        startActivity(intent3)
                    }.addOnFailureListener {
                        Toast.makeText(this, "Invalid referral code", Toast.LENGTH_SHORT).show()
                    }
                 }
            }
        }

        binding_re.skip.setOnClickListener{


            var intent3 = Intent(this, Welcome::class.java)

            startActivity(intent3)
        }
    }
}