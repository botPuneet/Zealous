package com.example.zeolous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zeolous.Adapter.nsutBranchAdapter
import com.example.zeolous.Models.nsutBranch
import com.example.zeolous.databinding.ActivityNsutPanelBinding

class nsutPanel : AppCompatActivity() {
    private lateinit var binding_N : ActivityNsutPanelBinding
    private lateinit var badapter : nsutBranchAdapter
    private lateinit var bList : ArrayList<nsutBranch>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_N = ActivityNsutPanelBinding.inflate(layoutInflater)
        setContentView(binding_N.root)

        binding_N.recyclerHorzXNsu.setHasFixedSize(true)
        binding_N.recyclerHorzXNsu.layoutManager = GridLayoutManager(this, 2)
        bList = ArrayList()
        addData()
        var items2 = listOf("CSAI", "COE", "CSDS", "EE", "ECE", "EIOT", "IT", "ITNS", "ICE", "MCE", "ME", "BT" )
        badapter = nsutBranchAdapter(bList)
        binding_N.recyclerHorzXNsu.adapter = badapter
        badapter.setOnItemClickListener(object : nsutBranchAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent  = Intent(this@nsutPanel, View_All_Courses::class.java)
                intent.putExtra("access", "3")
                intent.putExtra("branch", items2[position])
                startActivity(intent)
            }
        })

    }

    private fun addData() {

        bList.add(nsutBranch(R.drawable.ai,"CSAI"))

        bList.add(nsutBranch(R.drawable.coe,"COE"))

        bList.add(nsutBranch(R.drawable.csds,"CSDS"))

        bList.add(nsutBranch(R.drawable.tower,"EE"))

        bList.add(nsutBranch(R.drawable.ece,"ECE"))

        bList.add(nsutBranch(R.drawable.coe,"EIOT"))

        bList.add(nsutBranch(R.drawable.cs,"IT"))

        bList.add(nsutBranch(R.drawable.itns,"ITNS"))

        bList.add(nsutBranch(R.drawable.ice,"ICE"))

        bList.add(nsutBranch(R.drawable.csds,"MCE"))

        bList.add(nsutBranch(R.drawable.me,"ME"))

        bList.add(nsutBranch(R.drawable.bt,"BT"))
    }
}