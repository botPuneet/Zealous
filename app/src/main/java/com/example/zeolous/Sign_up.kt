package com.example.zeolous

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
    override fun onCreate(savedInstanceState: Bundle?) {

        binding1 = ActivitySignUpBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()

        signUpgoogle = GoogleSignIn.getClient(this, gso)

        super.onCreate(savedInstanceState)
        setContentView(binding1.root)
        binding1.textView7.setOnClickListener {
            startActivity(Intent(applicationContext, Sign_in::class.java))
            finish()
        }
        binding1.button.setOnClickListener {

            if(binding1.Password.text.toString()==binding1.RePassword.text.toString()){
                firebaseAuth.createUserWithEmailAndPassword(binding1.EmailAddress.text.toString(), binding1.Password.text.toString())
                    .addOnCompleteListener(this) { task ->
                   if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val name = binding1.personName.text.toString()

                    val email = binding1.EmailAddress.text.toString()
                    val password = binding1.Password.text.toString()
                   // val user = firebaseAuth.currentUser
                    database = FirebaseDatabase.getInstance().getReference("Users")
                    val users = user(name,  email, password)
                    database.child(name).setValue(users)
                       sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)

                       val arr = name.split(" ".toRegex(), limit = 2).toTypedArray()

                       val firstWord = arr[0] //the

                       val theRest = arr[1]
                       val editor : SharedPreferences.Editor = sharedPreferences.edit()
                       editor.putString("First_name", firstWord)
                       editor.putString("Full_name",name)
                       editor.putString("Last_name",theRest)
                       editor.putString("type","Users")
                       editor.apply()

                    // data tranfers


                    var intent = Intent(this, Personalization::class.java)

                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        this, task.getException().toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }}

            } else {
                 Toast.makeText(this,"Mismatch passward",Toast.LENGTH_SHORT).show()

            }
        }


        binding1.withGoogle.setOnClickListener {
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
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                  val email = account.email.toString()
                val name = account.displayName.toString()
                database = FirebaseDatabase.getInstance().getReference("GoogleUsers")
                val guser = Guser(name,  email)
                database.child(name).setValue(guser)
                var first_name  :String = ""

                sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)

                val arr = name.split(" ".toRegex(), limit = 2).toTypedArray()

                val firstWord = arr[0]
                val theRest = arr[1]

                val editor : SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("First_name", firstWord)
                editor.putString("Full_name",name)
                editor.putString("Last_name",theRest)
                editor.putString("type","GoogleUsers")
                editor.apply()


                var intent3 = Intent(this, Personalization::class.java)

                startActivity(intent3)
            }else{
                Toast.makeText(this , it.exception.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }
}