package com.example.zeolous.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zeolous.Models.Top_course
import com.example.zeolous.R

class Horizontal_RecyclerView(private val context : Context) : RecyclerView.Adapter<Horizontal_RecyclerView.MyViewHolder>(){
    private val topCourse = ArrayList<Top_course>()

    private lateinit var mListener : onItemClickListener


    interface onItemClickListener{
        fun onItemClick(position: Int){

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.top_course_recyler,parent,false)
        return MyViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return topCourse.size
    }
    fun updateUserList(userList : List<Top_course>){

        this.topCourse.clear()
        this.topCourse.addAll(userList)

        notifyDataSetChanged()

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentItem = topCourse[position]
        holder.top_Title.text = currentItem.title
        holder.creator.text = currentItem.creator
        holder.rank.text = currentItem.rating
        holder.enroll.text = currentItem.enroll
        Glide.with(context).load(currentItem.courseImage).into(holder.Img)

    }
    class MyViewHolder(itemView: View,  listner: Horizontal_RecyclerView.onItemClickListener) : RecyclerView.ViewHolder(itemView) {
              val Img :ImageView =itemView.findViewById(R.id.top_IMG)
             val top_Title :TextView = itemView.findViewById(R.id.top_Title)
        val creator :TextView = itemView.findViewById(R.id.top_Descp)
        val rank :TextView = itemView.findViewById(R.id.rankk)
        val enroll :TextView = itemView.findViewById(R.id.enrolll)

        init {
            itemView.setOnClickListener{
                listner.onItemClick(adapterPosition)
            }
        }
    }
}