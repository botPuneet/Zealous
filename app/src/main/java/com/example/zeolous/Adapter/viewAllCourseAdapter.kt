package com.example.zeolous.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zeolous.Models.Best_selling_course
import com.example.zeolous.Models.Topic
import com.example.zeolous.Models.viewAllCourses
import com.example.zeolous.R

class viewAllCourseAdapter (private val context : Context ,private val bestcourse : ArrayList<viewAllCourses>) : RecyclerView.Adapter<viewAllCourseAdapter.MyViewHolder>(){

    private lateinit var mlistener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int){

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mlistener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_course_recycler,parent,false)
        return MyViewHolder(view,mlistener)
    }

    override fun getItemCount(): Int {
        return bestcourse.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bestcourse[position]
        holder.top_Title.text = currentItem.name
        Glide.with(context).load(currentItem.image).into(holder.Img)
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val Img : ImageView =itemView.findViewById(R.id.best_course_img)
        val top_Title : TextView = itemView.findViewById(R.id.best_course_title)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}