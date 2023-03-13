package com.example.zeolous

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.zeolous.Models.Guser
import com.example.zeolous.databinding.ActivitySignInBinding
import com.example.zeolous.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Sign_in : AppCompatActivity() {
    private lateinit var binding2: ActivitySignInBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var signUpgoogle: GoogleSignInClient
    lateinit var preferences: SharedPreferences
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding2  = ActivitySignInBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()

        signUpgoogle = GoogleSignIn.getClient(this, gso)
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




                        database = FirebaseDatabase.getInstance().getReference("Users")
                        database.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid()).get().addOnSuccessListener {
                           val name = it.child("name").value

                            sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)

                            val arr = name.toString().split(" ".toRegex(), limit = 2).toTypedArray()
                            val firstWord = arr[0] //the

                            val theRest = arr[1]
                            val editor2 : SharedPreferences.Editor = sharedPreferences.edit()
                            editor2.putString("Uid",FirebaseAuth.getInstance().getCurrentUser()!!.getUid())
                            editor2.putString("First_name", firstWord)
                            editor2.putString("Full_name",name.toString())
                            editor2.putString("Last_name",theRest)
                            editor2.putString("type","Users")
                            editor2.putString("email",binding2.editTextTextEmailAddress.text.toString())
                            editor2.apply()
                            var intent3 = Intent(this, Home::class.java)
                            startActivity(intent3)

                        }


                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(baseContext, task.getException().toString(),
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }

        binding2.withGoogle.setOnClickListener{

            signIngoogle()
        }


    }

    private fun signIngoogle() {
        val signInIntent = signUpgoogle.signInIntent
        launcher.launch(signInIntent)


    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->

        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            handleresults(task)
        }

    }


    private fun handleresults(task: Task<GoogleSignInAccount>) {

        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {

                updateUI(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                database = FirebaseDatabase.getInstance().getReference("Users")
                database.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid()).get().addOnSuccessListener {
                    val name = it.child("name").value
                    sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)

                    val arr = name.toString().split(" ".toRegex(), limit = 2).toTypedArray()

                    val firstWord = arr[0] //the

                    val theRest = arr[1]
                    val editor2 : SharedPreferences.Editor = sharedPreferences.edit()
                    editor2.putString("Uid",FirebaseAuth.getInstance().getCurrentUser()!!.getUid())
                    editor2.putString("First_name", firstWord)
                    editor2.putString("Full_name",name.toString())
                    editor2.putString("Last_name",theRest)
                    editor2.putString("type","GoogleUsers")
                    editor2.putString("email",account.email.toString())
                    editor2.apply()
                var intent3 = Intent(this, Home::class.java)

                startActivity(intent3)

            }
            }else{
                Toast.makeText(this , it.exception.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }
}