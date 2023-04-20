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
import com.example.zeolous.Models.chatRecycler
import com.example.zeolous.R

class recom_adapter (private val context : Context) : RecyclerView.Adapter<recom_adapter.MyViewHolder>(){
    private val topCourse = ArrayList<Top_course>()

    private lateinit var mListener : onItemClickListener
    private val userList = ArrayList<chatRecycler>()

    interface onItemClickListener{
        fun onItemClick(position: Int){

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topcourse_personal_recycler,parent,false)
        return MyViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return topCourse.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = topCourse[position]
        holder.top_Title.text = currentItem.title
        holder.creator.text = currentItem.creator
        holder.descp.text = currentItem.description
        holder.rating.text = currentItem.rating
        holder.enroll.text = currentItem.enroll
        Glide.with(context).load(currentItem.courseImage).into(holder.Img)
    }

    fun updateUserList(userList : List<Top_course>){

        this.topCourse.clear()
        this.topCourse.addAll(userList)

        notifyDataSetChanged()

    }

    class MyViewHolder(itemView: View,  listner: recom_adapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val Img : ImageView =itemView.findViewById(R.id.top_IMG1)
        val top_Title : TextView = itemView.findViewById(R.id.top_Title1)
        val creator : TextView = itemView.findViewById(R.id.intructorName)
        val descp : TextView = itemView.findViewById(R.id.top_Descp)
        val rating : TextView = itemView.findViewById(R.id.ratingg)
        val enroll : TextView = itemView.findViewById(R.id.enrolll)

        init {
            itemView.setOnClickListener{
                listner.onItemClick(adapterPosition)
            }
        }
    }
}