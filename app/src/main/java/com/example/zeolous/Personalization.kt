package com.example.zeolous

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.zeolous.databinding.ActivityPersonalizationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Personalization : AppCompatActivity() {
    lateinit var preferences: SharedPreferences
    private lateinit var binding_p : ActivityPersonalizationBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    var interests  = listOf<String>("").toMutableList()
    var index=0
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_p = ActivityPersonalizationBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        setContentView(binding_p.root)


        var flag = arrayOf(
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true
        )

        binding_p.personal1.setOnClickListener {
            var personal_1 = "development"

            if (flag[0]) {
                if (index < 5) {
                    interests.add(index, personal_1)
                    index++
                    binding_p.personal1.backgroundTintList =
                        getColorStateList(R.color.holo_blue_bright)
                    flag[0] = false

                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding_p.personal1.backgroundTintList = getColorStateList(R.color.white)
                flag[0] = true
                interests.removeAt(index)
                index--
                binding_p.noofSelect.setText("Select any 5 ($index/5)")

            }

        }
        binding_p.personal2.setOnClickListener {
            var personal_2 = "Business"
            if (flag.get(1)) {
                if (index < 5) {
                    interests.add(index, personal_2)
                    index++
                    binding_p.personal2.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[1] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                     }
            } else {
                binding_p.personal2.backgroundTintList = getColorStateList(android.R.color.white)

                flag[1] = true
                interests.removeAt(index)
                index--
                binding_p.noofSelect.setText("Select any 5 ($index/5)")
            }

        }
        binding_p.personal3.setOnClickListener {
            var personal_3 = "Finance and Accounting"
            if (flag[2]) {
                if (index < 5) {
                    interests.add(index, personal_3)
                    index++
                    binding_p.personal3.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[2] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    binding_p.personal3.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[2] = true
                    index--
                binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }
            binding_p.personal4.setOnClickListener {
                var personal_4 = "IT and Software"
                if (flag[3]) {
                    if (index < 5) {
                    interests.add(index, personal_4)
                    index++
                    binding_p.personal4.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[3] = false
                        binding_p.noofSelect.setText("Select any 5 ($index/5)")
                    } else {
                        Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    binding_p.personal4.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[3] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }

            binding_p.personal6.setOnClickListener {
                var personal_6 = "Personal Development"
                if (flag[5]) {if (index < 5) {
                    interests.add(index, personal_6)
                    index++
                    binding_p.personal6.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[5] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
                } else {
                    binding_p.personal6.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[5] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }
            binding_p.personal7.setOnClickListener {
                var personal_7 = "Design"

                if (flag[6]) {if (index < 5) {
                    interests.add(index, personal_7)
                    index++
                    binding_p.personal7.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[6] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
                } else {
                    binding_p.personal7.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[6] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }

            binding_p.personal9.setOnClickListener {
                var personal_9 = "Lifestyle"
                if (flag[8]) {if (index < 5) {
                    interests.add(index, personal_9)
                    index++
                    binding_p.personal9.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[8] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
                } else {
                    binding_p.personal9.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[8] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }



            binding_p.personal11.setOnClickListener {
                var personal_11 = "Photography"
                if (flag[10]) {if (index < 5) {
                    interests.add(index, personal_11)
                    index++
                    binding_p.personal11.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[10] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
                } else {
                    binding_p.personal11.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[10] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }
            binding_p.personal12.setOnClickListener {
                var personal_12 = "Videography"
                if (flag[11]) {if (index < 5) {
                    interests.add(index, personal_12)
                    index++
                    binding_p.personal12.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[11] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
                } else {
                    binding_p.personal12.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[11] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }
            binding_p.personal13.setOnClickListener {
                var personal_13 = "Health and Fitness"
                if (flag[12]) {if (index < 5) {
                    interests.add(index, personal_13)
                    index++
                    binding_p.personal13.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[12] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
                } else {
                    binding_p.personal13.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[12] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }
            binding_p.personal14.setOnClickListener {
                var personal_14 = "Music"
                if (flag[13]) {if (index < 5) {
                    interests.add(index, personal_14)
                    index++
                    binding_p.personal14.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[13] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
                } else {
                    binding_p.personal14.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[13] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }
            binding_p.personal15.setOnClickListener {
                var personal_15 = "Teaching and Academics"
                if (flag[14]) {if (index < 5) {
                    interests.add(index, personal_15)
                    index++
                    binding_p.personal15.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[14] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
                } else {
                    binding_p.personal15.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[14] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }
            binding_p.personal16.setOnClickListener {
                var personal_16 = "Media"
                if (flag[15]) {if (index < 5) {
                    interests.add(index, personal_16)
                    index++
                    binding_p.personal16.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[15] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
                } else {
                    binding_p.personal16.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[15] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }
            binding_p.personal17.setOnClickListener {
                var personal_17 = "History"
                if (flag[16]) {if (index < 5) {
                    interests.add(index, personal_17)
                    index++
                    binding_p.personal17.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[16] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
                } else {
                    binding_p.personal17.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[16] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }
            binding_p.personal18.setOnClickListener {
                var personal_18 = "Acting"
                if (flag[17]) {if (index < 5) {
                    interests.add(index, personal_18)
                    index++
                    binding_p.personal18.backgroundTintList =
                        getColorStateList(android.R.color.holo_blue_bright)
                    flag[17] = false
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                } else {
                    Toast.makeText(this, "Selection Limit reached", Toast.LENGTH_SHORT).show()
                }
                } else {
                    binding_p.personal18.backgroundTintList =
                        getColorStateList(android.R.color.white)
                    flag[17] = true
                    index--
                    binding_p.noofSelect.setText("Select any 5 ($index/5)")
                }

            }

            binding_p.button2.setOnClickListener {

                preferences = getSharedPreferences("userData", Context.MODE_PRIVATE)
                val name = preferences.getString("Uid", "")
                var count = 0

                database = FirebaseDatabase.getInstance().getReference("Users")
                for (i: String in interests) {
                    database.child(name.toString()).child("personalization $count").setValue(i)
                    count++
                }
                var intent3 = Intent(this, Welcome::class.java)

                startActivity(intent3)
            }


    }}