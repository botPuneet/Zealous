package com.example.zeolous.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zeolous.Models.Best_selling_course
import com.example.zeolous.Models.Top_course
import com.example.zeolous.R

class best_selling_Adapter (private val bestcourse : ArrayList<Best_selling_course>) : RecyclerView.Adapter<best_selling_Adapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.best_selling_recycler,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bestcourse.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bestcourse[position]
        holder.top_Title.text = currentItem.title
        holder.Img.setImageResource(currentItem.img)
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Img : ImageView =itemView.findViewById(R.id.best_course_img)
        val top_Title : TextView = itemView.findViewById(R.id.best_course_title)
    }
}