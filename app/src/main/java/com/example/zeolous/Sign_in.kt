package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.zeolous.databinding.ActivitySignInBinding
import com.example.zeolous.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class Sign_in : AppCompatActivity() {
    private lateinit var binding2: ActivitySignInBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding2  = ActivitySignInBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(binding2.root)
        binding2.textView7.setOnClickListener{
            startActivity(Intent(applicationContext,Sign_up::class.java))
            finish()
        }
        binding2.button.setOnClickListener{


            auth.signInWithEmailAndPassword(binding2.editTextTextEmailAddress.text.toString(),binding2.editTextTextPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        var intent3 = Intent(this, Home::class.java)
                        startActivity(intent3)



                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(baseContext, task.getException().toString(),
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }
}