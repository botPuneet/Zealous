package com.example.zeolous.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zeolous.Models.chatRecycler
import com.example.zeolous.R

class chatAdapter(private val context : Context) : RecyclerView.Adapter<chatAdapter.MyViewHolder>() {
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
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.chat_recycler,
            parent,false
        )
        return MyViewHolder(itemView, mListener,)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.firstName.text = currentitem.username
        Glide.with(context).load(currentitem.Image).into(holder.ImageUri)
        holder.lastmessage.text = currentitem.lastMessage

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateUserList(userList : List<chatRecycler>){

        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()

    }

    class  MyViewHolder(itemView: View, listner: onItemClickListener) : RecyclerView.ViewHolder(itemView){

        val firstName : TextView = itemView.findViewById(R.id.username_textview_latest_message)
        val ImageUri : ImageView =itemView.findViewById(R.id.imageview_latest_message)
        val lastmessage : TextView = itemView.findViewById(R.id.latest_message_textview)

        init {
            itemView.setOnClickListener{
                listner.onItemClick(adapterPosition)
            }
        }

    }

}