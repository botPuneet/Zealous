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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_re = ActivityEnterRefferalBinding.inflate(layoutInflater)
        setContentView(binding_re.root)

        dbms = FirebaseDatabase.getInstance().getReference("Users")

        binding_re.button.setOnClickListener {
            var uid2 = binding_re.imageView.text.toString()
            dbms.child(uid2).get().addOnSuccessListener {
                val coind = it.child("coins").value.toString()

                val coins = Integer.parseInt(coind)
                dbms.child(uid2).child("coins").setValue((coins+20).toString())
                dbms.child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener {
                    val coind = it.child("coins").value.toString()

                    val coins = Integer.parseInt(coind)
                    dbms.child(FirebaseAuth.getInstance().currentUser!!.uid).child("coins").setValue((coins+20).toString())
                }
                var intent3 = Intent(this, Welcome::class.java)

                startActivity(intent3)
            }.addOnFailureListener{
                Toast.makeText(this, "Invalid referral code", Toast.LENGTH_SHORT).show()
            }


        }

        binding_re.skip.setOnClickListener{


            var intent3 = Intent(this, Welcome::class.java)

            startActivity(intent3)
        }
    }
}