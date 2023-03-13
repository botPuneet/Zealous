package com.example.zeolous.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zeolous.Models.chatsearch
import com.example.zeolous.R


class chatSeaarchAdapter(private var userList : ArrayList<chatsearch>, private val context : Context) : RecyclerView.Adapter<chatSeaarchAdapter.MyViewHolder>() {
     private lateinit var mListener: onItemClickListener

         interface onItemClickListener {
        fun onItemClick(position: Int)
         }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chat_search_recycler,parent,false)
        return MyViewHolder(itemView,mListener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.name.text = currentItem.username
        holder.type.text =currentItem.type
        Glide.with(context).load(currentItem.Image).into(holder.ImageUri)



    }

    class MyViewHolder(itemView : View,listner : onItemClickListener) : RecyclerView.ViewHolder(itemView){
              val name : TextView =itemView.findViewById(R.id.name_chat_search)
               val type : TextView =itemView.findViewById(R.id.type_chat_search)
           val ImageUri : ImageView =itemView.findViewById(R.id.Uri_chat_search)
 init {
     itemView.setOnClickListener{
         listner.onItemClick(adapterPosition)
     }
 }
    }
    fun setFilteredList(userList: ArrayList<chatsearch>){
        this.userList = userList
        notifyDataSetChanged()
    }

}