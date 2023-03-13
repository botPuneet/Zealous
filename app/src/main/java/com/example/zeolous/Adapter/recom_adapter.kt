package com.example.zeolous.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zeolous.Models.Top_course
import com.example.zeolous.R

class recom_adapter (private val topcourse : ArrayList<Top_course>) : RecyclerView.Adapter<recom_adapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topcourse_personal_recycler,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topcourse.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = topcourse[position]
        holder.top_Title.text = currentItem.title
        holder.Img.setImageResource(currentItem.img)
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Img : ImageView =itemView.findViewById(R.id.top_IMG1)
        val top_Title : TextView = itemView.findViewById(R.id.top_Title1)
    }
}