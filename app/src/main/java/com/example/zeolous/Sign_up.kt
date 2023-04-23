package com.example.zeolous

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.zeolous.Models.Guser
import com.example.zeolous.Models.user
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

class Sign_up : AppCompatActivity() {

    private lateinit var binding1: ActivitySignUpBinding
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var signUpgoogle: GoogleSignInClient
    private lateinit var database: DatabaseReference
    private lateinit var database2:DatabaseReference

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {

        binding1 = ActivitySignUpBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()

        signUpgoogle = GoogleSignIn.getClient(this, gso)

        super.onCreate(savedInstanceState)
        setContentView(binding1.root)

        //signIN button
        binding1.textView7.setOnClickListener {
            startActivity(Intent(applicationContext, Sign_in::class.java))
            finish()
        }
        database2 = FirebaseDatabase.getInstance().getReference("usernames")
        //Signup by email
        binding1.button.setOnClickListener {

                if (binding1.Password.text.toString() == binding1.RePassword.text.toString()) {

                    firebaseAuth.createUserWithEmailAndPassword(
                        binding1.EmailAddress.text.toString(),
                        binding1.Password.text.toString()
                    )
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val name = binding1.personName.text.toString()
                                val email = binding1.EmailAddress.text.toString()
                                val password = binding1.Password.text.toString()
                                var emailg2 = email.replace("@", "a")
                                var emailg22 = emailg2.replace(".", "z")
                                database2.child(name).child(emailg22).get().addOnSuccessListener {

                                    if (it.value.toString() == "1") {
                                        Toast.makeText(this, "already have an account", Toast.LENGTH_SHORT).show()
                                    } else {
                                val UId = FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
                                    .toString()
                                database = FirebaseDatabase.getInstance().getReference("Users")


                                val users = user(UId, name, email, password)



                                sharedPreferences =
                                    getSharedPreferences("userData", Context.MODE_PRIVATE)
                                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                                val arr = name.split(" ".toRegex(), limit = 2).toTypedArray()
                                val firstWord = arr[0]
                                editor.putString("First_name", firstWord)
                                editor.putString("Full_name", name)
                                editor.putString(
                                    "Uid",
                                    FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
                                )
                                editor.putString("type", "Users")
                                editor.putString("email", email)
                                editor.apply()

                                //Users
                                database.child(
                                    FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
                                ).setValue(users)

                                //usernames
                                database2.child(name).get().addOnSuccessListener {

                                    var emailg = email.replace("@", "a")
                                    var emailg2 = emailg.replace(".", "z")
                                    database2.child(name).child(emailg2)
                                        .setValue("1")


                                }

                                var intent = Intent(this, username::class.java)
                                startActivity(intent)

                            }}
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(
                                    this, task.getException().toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                } else {
                    Toast.makeText(this, "Mismatch passward", Toast.LENGTH_SHORT).show()

            }//set on click listener of email
        }

        binding1.withGoogle.setOnClickListener {
            signIngoogle()
        }


    }// close of create function

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
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            database2 = FirebaseDatabase.getInstance().getReference("usernames")
            if (it.isSuccessful) {

                val email1 = account.email.toString()

                val name = account.displayName.toString()
                var emailg2 = email1.replace("@", "a")
                var emailg22 = emailg2.replace(".", "z")
                database2.child(name).child(emailg22).get().addOnSuccessListener {

                    if (it.value.toString() == "1") {
                  Toast.makeText(this, "already have an account", Toast.LENGTH_SHORT).show()
                    } else {
                        val UId = FirebaseAuth.getInstance().getCurrentUser()!!.getUid().toString()
                        database = FirebaseDatabase.getInstance().getReference("Users")


                        val guser = Guser(UId, name, email1)

                        sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)
                        database.child(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())
                            .setValue(guser)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        val arr = name.split(" ".toRegex(), limit = 2).toTypedArray()
                        val firstWord = arr[0]

                        editor.putString(
                            "Uid",
                            FirebaseAuth.getInstance().getCurrentUser()!!.getUid()
                        )
                        editor.putString("First_name", firstWord)
                        editor.putString("Full_name", name)
                        editor.putString("type", "GoogleUsers")
                        editor.putString("email", email1)
                        editor.apply()

                        database2.child(name).get().addOnSuccessListener {

                            var emailg = email1.replace("@", "a")
                            var emailg2 = emailg.replace(".", "z")
                            database2.child(name).child(emailg2)
                                .setValue("1")


                        }


                        var intent3 = Intent(this, username::class.java)
                        startActivity(intent3)
                    }
                }
            }
                else{
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }

        }
    }
}