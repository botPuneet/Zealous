package com.example.zeolous.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zeolous.Models.Topic
import com.example.zeolous.R


class TopicAdapter(val context: Context, private val bestcourse : ArrayList<Topic>) : RecyclerView.Adapter<TopicAdapter.MyViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int){

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.topic_recycler,parent,false)
        return MyViewHolder(view,mListener)
    }

    override fun getItemCount(): Int {
        return bestcourse.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bestcourse[position]
        holder.top_Title.text = currentItem.topic

    }
    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val top_Title : TextView = itemView.findViewById(R.id.topic555)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}